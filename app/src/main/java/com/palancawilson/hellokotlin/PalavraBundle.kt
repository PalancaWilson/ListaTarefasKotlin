package com.palancawilson.hellokotlin

/**
 * Data class que encapsula a lista de palavras de uma mensagem
 * e calcula algumas estatísticas sobre ela.
 *
 * Vantagens de uma `data class`:
 *  - gera automaticamente `equals`, `hashCode`, `toString` e `copy`;
 *  - é ideal para representar dados imutáveis.
 *
 * @param palavras lista de palavras (já dividida/filtrada) da mensagem.
 */
data class PalavraBundle(val palavras: List<String>) {

    /**
     * Contagem total de palavras.
     * Propriedade computada (não é passada no construtor).
     */
    val contar: Int = palavras.size

    /**
     * Comprimento médio das palavras.
     * Devolve 0.0 quando a lista está vazia (evita NaN do `average()`).
     */
    val comprimentoMedio: Double =
        if (palavras.isEmpty()) 0.0 else palavras.map { it.length }.average()

    /**
     * Palavra mais longa.
     * Usa `maxByOrNull` (seguro para listas vazias) e `?:` para valor por defeito.
     */
    val palavraMaisLonga: String =
        palavras.maxByOrNull { it.length } ?: "Não disponível."
}
