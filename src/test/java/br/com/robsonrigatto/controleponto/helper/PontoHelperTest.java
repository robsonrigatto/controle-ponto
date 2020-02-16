package br.com.robsonrigatto.controleponto.helper;

import br.com.robsonrigatto.controleponto.entity.Ponto;
import br.com.robsonrigatto.controleponto.enumeration.TipoBatida;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PontoHelperTest {

	@Autowired
	private PontoHelper helper;

	@Test
	public void proximoPontoTest_nulo() {
		assertEquals(TipoBatida.ENTRADA, helper.getProximoPonto(null));
	}

	@Test
	public void proximoPontoTest_entrada() {
		assertEquals(TipoBatida.SAIDA, helper.getProximoPonto(TipoBatida.ENTRADA));
	}

	@Test
	public void proximoPontoTest_saida() {
		assertEquals(TipoBatida.ENTRADA, helper.getProximoPonto(TipoBatida.SAIDA));
	}

	@Test
	public void calculaTotalHorasTest_listaVazia() {
		assertEquals(0.0, helper.calculaTotalHoras(Lists.newArrayList()));
	}

	@Test
	public void calculaTotalHorasTest_quantidadeImpar() {
		Ponto p1 = new Ponto();
		p1.setTipoBatida(TipoBatida.ENTRADA);
		p1.setDataHoraBatida(LocalDateTime.of(2020, 02, 16, 7,00));
		Ponto p2 = new Ponto();
		p2.setTipoBatida(TipoBatida.SAIDA);
		p2.setDataHoraBatida(LocalDateTime.of(2020, 02, 16, 8,00));
		Ponto p3 = new Ponto();
		p3.setTipoBatida(TipoBatida.ENTRADA);
		p3.setDataHoraBatida(LocalDateTime.of(2020, 02, 16, 9,00));
		List<Ponto> pontos = Lists.newArrayList(p1, p2, p3);
		assertEquals(1.0, helper.calculaTotalHoras(pontos));
	}

	@Test
	public void calculaTotalHorasTest_quantidadePar() {
		Ponto p1 = new Ponto();
		p1.setTipoBatida(TipoBatida.ENTRADA);
		p1.setDataHoraBatida(LocalDateTime.of(2020, 02, 16, 7,00));
		Ponto p2 = new Ponto();
		p2.setTipoBatida(TipoBatida.SAIDA);
		p2.setDataHoraBatida(LocalDateTime.of(2020, 02, 16, 8,00));
		Ponto p3 = new Ponto();
		p3.setTipoBatida(TipoBatida.ENTRADA);
		p3.setDataHoraBatida(LocalDateTime.of(2020, 02, 16, 9,00));
		Ponto p4 = new Ponto();
		p4.setTipoBatida(TipoBatida.SAIDA);
		p4.setDataHoraBatida(LocalDateTime.of(2020, 02, 16, 9,30));
		List<Ponto> pontos = Lists.newArrayList(p1, p2, p3, p4);
		assertEquals(1.5, helper.calculaTotalHoras(pontos));
	}

}
