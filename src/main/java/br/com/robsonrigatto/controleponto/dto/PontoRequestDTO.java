package br.com.robsonrigatto.controleponto.dto;

import br.com.robsonrigatto.controleponto.enumeration.TipoBatida;

import java.io.Serializable;

public class PontoRequestDTO implements Serializable {

    private Integer idAluno;
    private TipoBatida tipoBatida;

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public TipoBatida getTipoBatida() {
        return tipoBatida;
    }

    public void setTipoBatida(TipoBatida tipoBatida) {
        this.tipoBatida = tipoBatida;
    }
}
