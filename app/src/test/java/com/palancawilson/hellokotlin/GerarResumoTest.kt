package com.palancawilson.hellokotlin

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Testes unitários (JVM) da função de nível superior `gerarResumo` (Exercício 2.3),
 * incluindo o filtro por lambda das palavras longas.
 */
class GerarResumoTest {

    @Test
    fun resumo_incluiTodasAsEstatisticasEPalavrasLongas() {
        val bundle = PalavraBundle(listOf("Kotlin", "a", "Android"))

        val esperado = """
            Palavras: 3
            Comprimento médio: 4.67
            Palavra mais longa: Android
            Palavras longas (> 4): Kotlin, Android
        """.trimIndent()

        assertEquals(esperado, gerarResumo(bundle))
    }

    @Test
    fun resumo_bundleVazio_indicaNenhumaPalavraLonga() {
        val bundle = PalavraBundle(emptyList())

        val esperado = """
            Palavras: 0
            Comprimento médio: 0.0
            Palavra mais longa: Não disponível.
            Palavras longas (> 4): nenhuma
        """.trimIndent()

        assertEquals(esperado, gerarResumo(bundle))
    }

    @Test
    fun filtroLambda_excluiPalavrasComLimiteOuMenos() {
        // "sol" (3) e "mar" (3) não são longas; "planeta" (7) é.
        val bundle = PalavraBundle(listOf("sol", "mar", "planeta"))

        val resumo = gerarResumo(bundle)

        assertTrue(resumo.contains("Palavras longas (> 4): planeta"))
        assertFalse(resumo.contains("sol"))
        assertFalse(resumo.contains("mar"))
    }
}
