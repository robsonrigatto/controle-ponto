package br.com.robsonrigatto.controleponto.dto;

import br.com.robsonrigatto.controleponto.enumeration.TipoBatida;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PontoResponseDTO implements Serializable {

    private Integer id;
    private AlunoResponseDTO aluno;
    private TipoBatida tipoBatida;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataHoraBatida;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AlunoResponseDTO getAluno() {
        return aluno;
    }

    public void setAluno(AlunoResponseDTO aluno) {
        this.aluno = aluno;
    }

    public TipoBatida getTipoBatida() {
        return tipoBatida;
    }

    public void setTipoBatida(TipoBatida tipoBatida) {
        this.tipoBatida = tipoBatida;
    }

    public LocalDateTime getDataHoraBatida() {
        return dataHoraBatida;
    }

    public void setDataHoraBatida(LocalDateTime dataHoraBatida) {
        this.dataHoraBatida = dataHoraBatida;
    }
}
