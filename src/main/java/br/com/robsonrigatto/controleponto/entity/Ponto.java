package br.com.robsonrigatto.controleponto.entity;

import br.com.robsonrigatto.controleponto.enumeration.TipoBatida;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Ponto {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_aluno", foreignKey = @ForeignKey(name = "FK_PONTO_ALUNO"))
    private Aluno aluno;

    @Column(name = "data_hora_batida")
    private LocalDateTime dataHoraBatida;

    @Enumerated(EnumType.STRING)
    private TipoBatida tipoBatida;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public LocalDateTime getDataHoraBatida() {
        return dataHoraBatida;
    }

    public void setDataHoraBatida(LocalDateTime dataHoraBatida) {
        this.dataHoraBatida = dataHoraBatida;
    }

    public TipoBatida getTipoBatida() {
        return tipoBatida;
    }

    public void setTipoBatida(TipoBatida tipoBatida) {
        this.tipoBatida = tipoBatida;
    }
}
