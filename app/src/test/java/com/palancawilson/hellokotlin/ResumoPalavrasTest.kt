package com.palancawilson.hellokotlin

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Testes unitários (JVM) da lógica do Exercício 2.1:
 * divisão da mensagem em palavras e geração do resumo com `when`/`if`.
 */
class ResumoPalavrasTest {

    @Test
    fun mensagemVazia_retornaSemPalavras() {
        assertEquals("Nenhuma palavra encontrada!", gerarResumoContagem(""))
        assertEquals("Nenhuma palavra encontrada!", gerarResumoContagem("   "))
    }

    @Test
    fun umaPalavra_retornaApenasUmaPalavra() {
        assertEquals("Apenas uma palavra!", gerarResumoContagem("Olá"))
    }

    @Test
    fun variasPalavras_contaCorretamente() {
        assertEquals("Foram digitadas 3 palavras.", gerarResumoContagem("Olá mundo teste"))
    }

    @Test
    fun mensagemComMuitasPalavras_acrescentaAviso() {
        assertEquals(
            "Foram digitadas 6 palavras. (mensagem longa)",
            gerarResumoContagem("a b c d e f")
        )
    }

    @Test
    fun dividirPalavras_ignoraEspacosVazios() {
        assertEquals(listOf("Olá", "mundo"), dividirPalavras("Olá   mundo"))
    }
}
