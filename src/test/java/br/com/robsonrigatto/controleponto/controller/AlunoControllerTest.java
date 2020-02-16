package br.com.robsonrigatto.controleponto.controller;

import br.com.robsonrigatto.controleponto.dto.AlunoRequestDTO;
import br.com.robsonrigatto.controleponto.dto.AlunoResponseDTO;
import br.com.robsonrigatto.controleponto.entity.Aluno;
import br.com.robsonrigatto.controleponto.repository.AlunoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlunoControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AlunoRepository alunoRepository;

    private Aluno joao;

    @BeforeEach
    public void setUp() {
        joao = new Aluno();
        joao.setCpf("00000000000"); joao.setNome("João da Silva"); joao.setEmail("joao.silva@gmail.com");
        joao = alunoRepository.save(joao);
    }

    @AfterEach
    public void tearDown() {
        alunoRepository.delete(joao);
        joao = null;
    }

    @Test
    public void findAllTest() {
        ResponseEntity<AlunoResponseDTO[]> response = restTemplate.getForEntity("http://localhost:" + port + "/controle-ponto/alunos", AlunoResponseDTO[].class);
        assertEquals(200, response.getStatusCodeValue());

        AlunoResponseDTO[] list = response.getBody();
        assertNotNull(list);
        assertTrue(list.length > 0);
    }

    @Test
    public void findByIdTest_alunoExiste() {
        ResponseEntity<AlunoResponseDTO> response = restTemplate.getForEntity("http://localhost:" + port + "/controle-ponto/alunos/{id}", AlunoResponseDTO.class, joao.getId());
        assertEquals(200, response.getStatusCodeValue());

        AlunoResponseDTO dto = response.getBody();
        assertEquals(joao.getId(), dto.getId());
    }

    @Test
    public void findByIdTest_alunoNaoExiste() {
        ResponseEntity<AlunoResponseDTO> response = restTemplate.getForEntity("http://localhost:" + port + "/controle-ponto/alunos/{id}", AlunoResponseDTO.class, joao.getId() + 1);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void createTest_emailNulo() {
        AlunoRequestDTO entradaDTO = new AlunoRequestDTO();
        entradaDTO.setNome("Pedro Ferreira"); entradaDTO.setCpf("12345678900");
        ResponseEntity<AlunoResponseDTO> response = restTemplate.postForEntity("http://localhost:" + port + "/controle-ponto/alunos", entradaDTO, AlunoResponseDTO.class);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void createTest_dadosValidos() {
        AlunoRequestDTO entradaDTO = new AlunoRequestDTO();
        entradaDTO.setNome("Pedro Ferreira"); entradaDTO.setCpf("12345678900"); entradaDTO.setEmail("pedro.ferreira@gmail.com");
        ResponseEntity<AlunoResponseDTO> response = restTemplate.postForEntity("http://localhost:" + port + "/controle-ponto/alunos", entradaDTO, AlunoResponseDTO.class);
        assertEquals(201, response.getStatusCodeValue());

        AlunoResponseDTO dto = response.getBody();
        assertNotNull(dto);
        assertEquals(entradaDTO.getCpf(), dto.getCpf());
    }

    @Test
    public void updateTest_dadosValidos() {
        AlunoRequestDTO entradaDTO = new AlunoRequestDTO();
        entradaDTO.setCpf("12345678900");
        HttpEntity<AlunoRequestDTO> request = new HttpEntity<>(entradaDTO);
        ResponseEntity<AlunoResponseDTO> response = restTemplate.exchange("http://localhost:" + port + "/controle-ponto/alunos/{id}", HttpMethod.PUT, request, AlunoResponseDTO.class, joao.getId());

        assertEquals(200, response.getStatusCodeValue());
        AlunoResponseDTO dto = response.getBody();
        assertEquals(entradaDTO.getCpf(), dto.getCpf());
        assertEquals(joao.getId(), dto.getId());
    }

    @Test
    public void updateTest_alunoNaoExite() {
        AlunoRequestDTO entradaDTO = new AlunoRequestDTO();
        entradaDTO.setCpf("12345678900");
        HttpEntity<AlunoRequestDTO> request = new HttpEntity<>(entradaDTO);
        ResponseEntity<AlunoResponseDTO> response = restTemplate.exchange("http://localhost:" + port + "/controle-ponto/alunos/{id}", HttpMethod.PUT, request, AlunoResponseDTO.class, joao.getId()+1);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void deleteTest_alunoNaoExiste() {
        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:" + port + "/controle-ponto/alunos/{id}", HttpMethod.DELETE, null, Void.class, joao.getId()+1);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void deleteTest() {
        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:" + port + "/controle-ponto/alunos/{id}", HttpMethod.DELETE, null, Void.class, joao.getId());
        assertEquals(204, response.getStatusCodeValue());

        Optional<Aluno> alunoOptional = this.alunoRepository.findById(joao.getId());
        assertFalse(alunoOptional.isPresent());
    }
}
