package br.com.robsonrigatto.controleponto.controller;

import br.com.robsonrigatto.controleponto.dto.PontoPorAlunoResponseDTO;
import br.com.robsonrigatto.controleponto.dto.PontoResponseDTO;
import br.com.robsonrigatto.controleponto.entity.Aluno;
import br.com.robsonrigatto.controleponto.entity.Ponto;
import br.com.robsonrigatto.controleponto.enumeration.TipoBatida;
import br.com.robsonrigatto.controleponto.helper.PontoHelper;
import br.com.robsonrigatto.controleponto.mapper.PontoMapper;
import br.com.robsonrigatto.controleponto.repository.AlunoRepository;
import br.com.robsonrigatto.controleponto.repository.PontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PontoController {

    @Autowired
    private PontoRepository pontoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PontoHelper pontoHelper;

    @Autowired
    private PontoMapper pontoMapper;

    @PostMapping("/alunos/{id}/pontos")
    public ResponseEntity<PontoResponseDTO> create(@PathVariable("id") Integer idAluno) {
        Optional<Aluno> alunoOptional = alunoRepository.findById(idAluno);
        if(!alunoOptional.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "é obrigatório informar um aluno válido");
        }

        Ponto ponto = new Ponto();
        Aluno aluno = alunoOptional.get();
        ponto.setAluno(aluno);
        ponto.setDataHoraBatida(LocalDateTime.now());

        Optional<Ponto> pontoOptional = this.pontoRepository.findLastPontoByIdAluno(idAluno);
        TipoBatida tipoUltimaBatida = pontoOptional.map(Ponto::getTipoBatida).orElse(null);
        TipoBatida tipoBatida = this.pontoHelper.getProximoPonto(tipoUltimaBatida);
        ponto.setTipoBatida(tipoBatida);

        ponto = this.pontoRepository.save(ponto);
        return new ResponseEntity<>(pontoMapper.entityToDTO(ponto, true), HttpStatus.CREATED);
    }

    @GetMapping(path = "/alunos/{id}/pontos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PontoPorAlunoResponseDTO> findAllByIdAluno(@PathVariable("id") Integer idAluno) {
        Optional<Aluno> alunoOptional = this.alunoRepository.findById(idAluno);
        if(!alunoOptional.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "é obrigatório informar um aluno válido");
        }

        List<Ponto> pontos = this.pontoRepository.findAllByIdAluno(idAluno);

        PontoPorAlunoResponseDTO dto = new PontoPorAlunoResponseDTO();
        dto.setTotalHoras(this.pontoHelper.calculaTotalHoras(pontos));
        dto.setPontos(pontos.stream().map(ponto -> this.pontoMapper.entityToDTO(ponto, false)).collect(Collectors.toList()));

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
