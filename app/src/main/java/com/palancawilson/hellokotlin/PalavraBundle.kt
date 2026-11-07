package com.palancawilson.hellokotlin

// 2.2: guarda as palavras e calcula estatísticas.
data class PalavraBundle(val palavras: List<String>) {

    // Número total de palavras.
    val contar: Int = palavras.size

    // Média do comprimento (0.0 se estiver vazio).
    val comprimentoMedio: Double =
        if (palavras.isEmpty()) 0.0 else palavras.map { it.length }.average()

    // Palavra mais longa (ou valor por defeito).
    val palavraMaisLonga: String =
        palavras.maxByOrNull { it.length } ?: "Não disponível."
}
