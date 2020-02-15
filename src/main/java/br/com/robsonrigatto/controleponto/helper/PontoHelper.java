package br.com.robsonrigatto.controleponto.helper;

import br.com.robsonrigatto.controleponto.entity.Ponto;
import br.com.robsonrigatto.controleponto.enumeration.TipoBatida;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class PontoHelper {

    public Double calculaTotalHoras(List<Ponto> pontos) {
        Long total = 0l;
        LocalDateTime dataHoraUltimaBatida = null;

        for(Ponto ponto : pontos) {
            TipoBatida tipoBatida = ponto.getTipoBatida();
            LocalDateTime dataHoraBatida = ponto.getDataHoraBatida();

            if(tipoBatida.equals(TipoBatida.ENTRADA)) {
                dataHoraUltimaBatida = dataHoraBatida;

            } else {
                total += Duration.between(dataHoraUltimaBatida, dataHoraBatida).toMillis();
            }
        }

        return ((double) total) / (1000 * 60 * 60);
    }

    public TipoBatida getProximoPonto(TipoBatida tipoBatida) {
        if(tipoBatida == null || tipoBatida.equals(TipoBatida.SAIDA)) {
            return TipoBatida.ENTRADA;
        }

        return TipoBatida.SAIDA;
    }
}
