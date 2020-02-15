package br.com.robsonrigatto.controleponto.mapper;

import br.com.robsonrigatto.controleponto.dto.PontoResponseDTO;
import br.com.robsonrigatto.controleponto.entity.Ponto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PontoMapper {

    @Autowired
    private AlunoMapper alunoMapper;

    public PontoResponseDTO entityToDTO(Ponto ponto, Boolean includeAluno) {
        PontoResponseDTO dto = new PontoResponseDTO();
        dto.setId(ponto.getId());
        if(includeAluno) {
            dto.setAluno(alunoMapper.entityToDTO(ponto.getAluno()));
        }
        dto.setTipoBatida(ponto.getTipoBatida());
        dto.setDataHoraBatida(ponto.getDataHoraBatida());

        return dto;
    }
}
