package br.com.robsonrigatto.controleponto.mapper;

import br.com.robsonrigatto.controleponto.dto.AlunoResponseDTO;
import br.com.robsonrigatto.controleponto.dto.AlunoRequestDTO;
import br.com.robsonrigatto.controleponto.entity.Aluno;
import org.springframework.stereotype.Component;

@Component
public class AlunoMapper {

    public AlunoResponseDTO entityToDTO(Aluno entity) {
        AlunoResponseDTO dto = new AlunoResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setCpf(entity.getCpf());
        dto.setEmail(entity.getEmail());
        dto.setDataCadastro(entity.getDataCadastro());

        return dto;
    }

    public Aluno dtoToNewEntity(AlunoRequestDTO dto) {
        Aluno entity = new Aluno();
        entity.setNome(dto.getNome());
        entity.setCpf(dto.getCpf());
        entity.setEmail(dto.getEmail());
        return entity;
    }
}
