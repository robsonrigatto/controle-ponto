package br.com.robsonrigatto.controleponto.dto;

import java.io.Serializable;
import java.util.List;

public class PontoPorAlunoResponseDTO implements Serializable {

    private List<PontoResponseDTO> pontos;
    private Double totalHoras;

    public List<PontoResponseDTO> getPontos() {
        return pontos;
    }

    public void setPontos(List<PontoResponseDTO> pontos) {
        this.pontos = pontos;
    }

    public Double getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(Double totalHoras) {
        this.totalHoras = totalHoras;
    }
}
