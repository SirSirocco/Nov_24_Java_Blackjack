package model;

import org.junit.*;
import static org.junit.Assert.*;

public class ParticipanteTeste {
	// APOSTA MINIMA = 50
	/**
	 * Caso 1: valor igual a referencia. Esperado: true.
	 */
	@Test
	public void testValidaApostaIgual() {
		int val = 50;
		assertTrue(Participante.validaAposta(val));
	}

	/**
	 * Caso 2: valor maior que referencia. Esperado: true.
	 */
	@Test
	public void testValidaApostaMaior() {
		int val = 60;
		assertTrue(Participante.validaAposta(val));
	}

	/**
	 * Caso 3: valor menor que referencia. Esperado: false.
	 */
	@Test
	public void testValidaApostaMenor() {
		int val = 1;
		assertFalse(Participante.validaAposta(val));
	}

	/**
	 * Caso 1: Participante possui blackjack. Esperado: 1.
	 */
	@Test
	public void testVerificaBlackjackTrue() {
		Participante dealer = Dealer.getDealer();
		dealer.mao.get(0).limpaMao();
		dealer.mao.get(0).insere(new Carta("Paus", "A"));
		dealer.mao.get(0).insere(new Carta("Paus", "10"));

		assertEquals(1, dealer.verificaBlackjack(0));
	}

	/**
	 * Caso 2: Participante nao possui blackjack nem 21 pontos. Esperado: 0.
	 */
	@Test
	public void testVerificaBlackjackFalse21Pontos() {
		Participante dealer = Dealer.getDealer();
		dealer.mao.get(0).limpaMao();
		dealer.mao.get(0).insere(new Carta("Paus", "A"));
		dealer.mao.get(0).insere(new Carta("Paus", "5"));
		dealer.mao.get(0).insere(new Carta("Copas", "5"));

		assertEquals(0, dealer.verificaBlackjack(0));
	}

	/**
	 * Caso 3: Jogador possui 21 pontos, mas fez split de ases. Esperado: 0 (nao tem
	 * Blackjack).
	 */
	@Test
	public void testVerificaBlackjackFalseSplitAses() {
		Jogador jogador = new Jogador();
		jogador.mao.get(0).insere(new Carta("Paus", "A"));
		jogador.mao.get(0).insere(new Carta("Paus", "K"));
		jogador.setSplitAses(true); // Simula divisao de ases

		assertEquals(0, jogador.verificaBlackjack(0));
	}

	/**
	 * Caso 1: Jogador possui dois ases de naipes diferentes. Esperado: 1.
	 */
	@Test
	public void testVerificaCartasMesmoValorAs() {
		Carta carta1 = new Carta("Paus", "A");
		Carta carta2 = new Carta("Copas", "A");
		assertEquals(1, Participante.verificaCartasMesmoValor(carta1, carta2));
	}

	/**
	 * Caso 2: Jogador possui um rei e um valete. Esperado: 10.
	 */
	@Test
	public void testVerificaCartasMesmoValorJR() {
		Carta carta1 = new Carta("Paus", "J");
		Carta carta2 = new Carta("Copas", "K");
		assertEquals(10, Participante.verificaCartasMesmoValor(carta1, carta2));
	}

	/**
	 * Caso 3: Jogador possui duas cartas numericas com mesmo valor. Esperado: Valor
	 * das cartas.
	 */
	@Test
	public void testVerificaCartasMesmoValorNum() {
		Carta carta1 = new Carta("Paus", "7");
		Carta carta2 = new Carta("Copas", "7");
		assertEquals(7, Participante.verificaCartasMesmoValor(carta1, carta2));
	}

	/**
	 * Caso 4: Jogador possui duas cartas com valores diferentes. Esperado: -1.
	 */
	@Test
	public void testVerificaCartasMesmoValorDiff() {
		Carta carta1 = new Carta("Paus", "7");
		Carta carta2 = new Carta("Copas", "A");
		assertEquals(-1, Participante.verificaCartasMesmoValor(carta1, carta2));
	}

	/**
	 * Caso 1: Jogador soh possui valores numericos. Esperado: Soma dos valores.
	 */
	@Test
	public void testGetPontosNum() {
		Participante participante = new Jogador();

		participante.mao.get(0).insere(new Carta("Copas", "8"));
		participante.mao.get(0).insere(new Carta("Espadas", "7"));
		int pontos = participante.getPontos(0);
		assertEquals(15, pontos);
	}

	/**
	 * Caso 2: Jogador soh possui As e 10. Esperado: 21 pontos.
	 */
	@Test
	public void testGetPontosBJ() {
		Participante participante = new Jogador();

		participante.mao.get(0).insere(new Carta("Copas", "A"));
		participante.mao.get(0).insere(new Carta("Espadas", "10"));
		int pontos = participante.getPontos(0);
		assertEquals(21, pontos);
	}

	/**
	 * Caso 2: Jogador possui 2 Ases e 7. Esperado: 19 pontos.
	 */
	@Test
	public void testGetPontos2As() {
		Participante participante = new Jogador();
		int pontos;

		participante.mao.get(0).insere(new Carta("Copas", "A"));
		participante.mao.get(0).insere(new Carta("Espadas", "A"));
		participante.mao.get(0).insere(new Carta("Espadas", "7"));
		pontos = participante.getPontos(0);
		assertEquals(19, pontos);
	}

	/**
	 * Caso 1: Jogador quebra. Esperado: true.
	 */
	@Test
	public void testVerificaQuebraTrue() {
		Participante participante = new Jogador();

		participante.mao.get(0).insere(new Carta("Paus", "K"));
		participante.mao.get(0).insere(new Carta("Ouros", "Q"));
		participante.mao.get(0).insere(new Carta("Copas", "2"));
		assertTrue(participante.verificaQuebra(0));
	}

	/**
	 * Caso 2: Jogador nao quebra. Esperado: false.
	 */
	@Test
	public void testVerificaQuebraFalse() {
		Participante participante = new Jogador();

		participante.mao.get(0).insere(new Carta("Paus", "A"));
		participante.mao.get(0).insere(new Carta("Ouros", "A"));
		participante.mao.get(0).insere(new Carta("Copas", "2"));
		assertFalse(participante.verificaQuebra(0));
	}

	/**
	 * Caso 1: Participante ativa mao 1, antes inativa. Esperado: false (mao nao
	 * inativa).
	 */
	@Test
	public void testAtivaMao() {
		Participante participante = new Jogador();

		assertFalse(participante.verificaMaoAtiva(1));
		participante.ativaMao(1);
		assertTrue(participante.verificaMaoAtiva(1));
	}

	/**
	 * Caso 1: Participante ativa mao 1 e depois a desativa com stand. Esperado:
	 * true (mao inativada por stand).
	 */
	@Test
	public void testVerificaMaoInativa() {
		Participante participante = new Jogador();

		participante.ativaMao(1);
		participante.stand(1);
		assertTrue(participante.verificaMaoFinalizada(1));
	}

	/**
	 * Caso 1: Participante compra duas cartas. Esperado: numero de cartas igual a
	 * 2.
	 */
	@Test
	public void testHit() {
		Participante participante = new Jogador();

		assertEquals(0, participante.mao.get(0).getNumCartas());
		participante.hit(0);
		participante.hit(0);
		assertEquals(2, participante.mao.get(0).getNumCartas());
	}

	/**
	 * Caso 1: Participante inativa mao 0, unica mao antes ativa. Esperado:
	 * numMaosFinalizadas == 0 e verificaMaoFinalizada(0) retorna true.
	 */
	@Test
	public void testStand() {
		Participante participante = new Jogador();

		participante.stand(0);
		assertEquals(1, participante.numMaosFinalizadas);
		assertTrue(participante.verificaMaoFinalizada(0));
	}
}
