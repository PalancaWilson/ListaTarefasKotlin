package com.palancawilson.hellokotlin

import org.junit.Assert.assertEquals
import org.junit.Test

// Testes da data class PalavraBundle (2.2).
class PalavraBundleTest {

    // Palavras ASCII para evitar ambiguidades no cálculo do comprimento.
    // Comprimentos: "Kotlin" = 6, "a" = 1, "Android" = 7.
    private val bundle = PalavraBundle(listOf("Kotlin", "a", "Android"))

    @Test
    fun contar_retornaTotalDePalavras() {
        assertEquals(3, bundle.contar)
    }

    @Test
    fun comprimentoMedio_calculaMediaCorreta() {
        // (6 + 1 + 7) / 3 = 4.666...
        assertEquals(14.0 / 3.0, bundle.comprimentoMedio, 0.0001)
    }

    @Test
    fun palavraMaisLonga_retornaAPalavraComMaisCaracteres() {
        assertEquals("Android", bundle.palavraMaisLonga)
    }

    @Test
    fun bundleVazio_retornaValoresPorDefeito() {
        val vazio = PalavraBundle(emptyList())

        assertEquals(0, vazio.contar)
        assertEquals(0.0, vazio.comprimentoMedio, 0.0001)
        assertEquals("Não disponível.", vazio.palavraMaisLonga)
    }
}
