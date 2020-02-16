package br.com.robsonrigatto.controleponto.controller;

import br.com.robsonrigatto.controleponto.dto.PontoPorAlunoResponseDTO;
import br.com.robsonrigatto.controleponto.dto.PontoResponseDTO;
import br.com.robsonrigatto.controleponto.entity.Aluno;
import br.com.robsonrigatto.controleponto.entity.Ponto;
import br.com.robsonrigatto.controleponto.enumeration.TipoBatida;
import br.com.robsonrigatto.controleponto.repository.AlunoRepository;
import br.com.robsonrigatto.controleponto.repository.PontoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PontoControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PontoRepository pontoRepository;

    private Aluno joao;

    @BeforeEach
    public void setUp() {
        if(joao != null) return;

        joao = new Aluno();
        joao.setCpf("00000000000"); joao.setNome("João da Silva"); joao.setEmail("joao.silva@gmail.com");
        joao = alunoRepository.save(joao);
    }

    @Test
    public void createTest_entrada() {
        ResponseEntity<PontoResponseDTO> response = restTemplate.postForEntity("http://localhost:" + port + "/controle-ponto/alunos/{id}/pontos", null, PontoResponseDTO.class, joao.getId());
        assertEquals(201, response.getStatusCode().value());

        PontoResponseDTO ponto = response.getBody();
        assertNotNull(ponto.getId());
        assertEquals(joao.getId(), ponto.getAluno().getId());
        assertEquals(TipoBatida.ENTRADA, ponto.getTipoBatida());
    }

    @Test
    public void createTest_saida() {
        Ponto p1 = new Ponto();
        p1.setAluno(joao); p1.setTipoBatida(TipoBatida.ENTRADA); p1.setDataHoraBatida(LocalDateTime.of(2020, 02, 16, 12, 16));
        this.pontoRepository.save(p1);

        ResponseEntity<PontoResponseDTO> response = restTemplate.postForEntity("http://localhost:" + port + "/controle-ponto/alunos/{id}/pontos", null, PontoResponseDTO.class, joao.getId());
        assertEquals(201, response.getStatusCode().value());

        PontoResponseDTO ponto = response.getBody();
        assertNotNull(ponto.getId());
        assertEquals(joao.getId(), ponto.getAluno().getId());
        assertEquals(TipoBatida.SAIDA, ponto.getTipoBatida());
    }

    @Test
    public void createTest_alunoInvalido() {
        ResponseEntity<PontoResponseDTO> response = restTemplate.postForEntity("http://localhost:" + port + "/controle-ponto/alunos/{id}/pontos", null, PontoResponseDTO.class, joao.getId() + 1);
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    public void findAllByIdAluno_alunoInvalido() {
        ResponseEntity<PontoPorAlunoResponseDTO> response = restTemplate.getForEntity("http://localhost:" + port + "/controle-ponto/alunos/{id}/pontos", PontoPorAlunoResponseDTO.class, joao.getId() + 1);
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    public void findAllByIdAluno_alunoComPontos() {
        Ponto p1 = new Ponto();
        p1.setAluno(joao); p1.setTipoBatida(TipoBatida.ENTRADA); p1.setDataHoraBatida(LocalDateTime.of(2020, 02, 16, 12, 16));
        this.pontoRepository.save(p1);

        Ponto p2 = new Ponto();
        p2.setAluno(joao); p2.setTipoBatida(TipoBatida.SAIDA); p2.setDataHoraBatida(LocalDateTime.of(2020, 02, 16, 18, 31));
        this.pontoRepository.save(p2);

        ResponseEntity<PontoPorAlunoResponseDTO> response = restTemplate.getForEntity("http://localhost:" + port + "/controle-ponto/alunos/{id}/pontos", PontoPorAlunoResponseDTO.class, joao.getId());
        assertEquals(200, response.getStatusCode().value());

        PontoPorAlunoResponseDTO dto = response.getBody();
        assertEquals(6.25, dto.getTotalHoras());
        assertEquals(2, dto.getPontos().size());
    }
}
