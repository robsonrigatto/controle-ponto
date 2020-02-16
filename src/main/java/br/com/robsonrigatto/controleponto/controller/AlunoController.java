package br.com.robsonrigatto.controleponto.controller;

import br.com.robsonrigatto.controleponto.dto.AlunoRequestDTO;
import br.com.robsonrigatto.controleponto.dto.AlunoResponseDTO;
import br.com.robsonrigatto.controleponto.entity.Aluno;
import br.com.robsonrigatto.controleponto.mapper.AlunoMapper;
import br.com.robsonrigatto.controleponto.repository.AlunoRepository;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoMapper alunoMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlunoResponseDTO> findAll() {
        List<Aluno> alunos = alunoRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        return alunos.stream().map(entity -> alunoMapper.entityToDTO(entity)).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlunoResponseDTO> findById(@PathVariable("id") Integer id) {
        Optional<Aluno> optional = alunoRepository.findById(id);
        return optional.map(aluno -> new ResponseEntity<>(alunoMapper.entityToDTO(aluno), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlunoResponseDTO> create(@RequestBody AlunoRequestDTO dto) {
        List<String> requiredFields = Lists.newArrayList();
        if(StringUtils.isBlank(dto.getNome())) requiredFields.add("nome");
        if(StringUtils.isBlank(dto.getCpf()))  requiredFields.add("cpf");
        if(StringUtils.isBlank(dto.getEmail())) requiredFields.add("email");

        if(!requiredFields.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format("Os campos %s são obrigatórios", requiredFields));
        }

        Aluno entity = this.alunoMapper.dtoToNewEntity(dto);
        entity.setDataCadastro(LocalDate.now());
        entity = this.alunoRepository.save(entity);

        return new ResponseEntity<>(this.alunoMapper.entityToDTO(entity), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlunoResponseDTO> update(@PathVariable("id") Integer id, @RequestBody AlunoRequestDTO dto) {
        Optional<Aluno> optional = this.alunoRepository.findById(id);
        if(!optional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Aluno entity = optional.get();
        if(StringUtils.isNotBlank(dto.getNome())) {
            entity.setNome(dto.getNome());
        }
        if(StringUtils.isNotBlank(dto.getCpf())) {
            entity.setCpf(dto.getCpf());
        }
        if(StringUtils.isNotBlank(dto.getEmail())) {
            entity.setEmail(dto.getEmail());
        }

        entity = this.alunoRepository.save(entity);
        return new ResponseEntity<>(alunoMapper.entityToDTO(entity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        Optional<Aluno> optional = this.alunoRepository.findById(id);
        if(!optional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.alunoRepository.delete(optional.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
