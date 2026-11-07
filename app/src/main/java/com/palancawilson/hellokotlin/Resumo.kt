package com.palancawilson.hellokotlin

import kotlin.math.roundToInt

// 2.3: limite a partir do qual a palavra é considerada "longa".
const val LIMITE_PALAVRA_LONGA = 4

// 2.3: função de nível superior que gera o resumo.
fun gerarResumo(bundle: PalavraBundle): String {
    // Lambda no filter: mantém as palavras longas.
    val palavrasLongas = bundle.palavras.filter { it.length > LIMITE_PALAVRA_LONGA }

    // Arredonda a média a duas casas decimais.
    val media = (bundle.comprimentoMedio * 100).roundToInt() / 100.0

    // Texto das palavras longas (ou "nenhuma").
    val longasTexto =
        if (palavrasLongas.isEmpty()) "nenhuma" else palavrasLongas.joinToString()

    return """
        Palavras: ${bundle.contar}
        Comprimento médio: $media
        Palavra mais longa: ${bundle.palavraMaisLonga}
        Palavras longas (> $LIMITE_PALAVRA_LONGA): $longasTexto
    """.trimIndent()
}
