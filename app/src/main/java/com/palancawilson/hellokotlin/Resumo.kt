package com.palancawilson.hellokotlin

import kotlin.math.roundToInt

/**
 * Limite de comprimento a partir do qual uma palavra é considerada "longa".
 * Uma palavra é longa quando `length > LIMITE_PALAVRA_LONGA`.
 */
const val LIMITE_PALAVRA_LONGA = 4

/**
 * Gera uma string resumida com as estatísticas do [PalavraBundle].
 *
 * Função de nível superior (fora de qualquer classe), tal como pedido no 2.3.
 * Utiliza uma expressão lambda `{ it.length > LIMITE_PALAVRA_LONGA }` passada
 * ao `filter` para seleccionar apenas as palavras longas.
 *
 * @param bundle conjunto de palavras já processado.
 * @return resumo textual com contagem, média, palavra mais longa e palavras longas.
 */
fun gerarResumo(bundle: PalavraBundle): String {
    // Lambda `(String) -> Boolean`: mantém as palavras com mais de LIMITE_PALAVRA_LONGA caracteres.
    val palavrasLongas = bundle.palavras.filter { it.length > LIMITE_PALAVRA_LONGA }

    // Arredonda a média a duas casas decimais (sem depender do locale).
    val media = (bundle.comprimentoMedio * 100).roundToInt() / 100.0

    // Texto das palavras longas (ou "nenhuma" quando não existem).
    val longasTexto =
        if (palavrasLongas.isEmpty()) "nenhuma" else palavrasLongas.joinToString()

    return """
        Palavras: ${bundle.contar}
        Comprimento médio: $media
        Palavra mais longa: ${bundle.palavraMaisLonga}
        Palavras longas (> $LIMITE_PALAVRA_LONGA): $longasTexto
    """.trimIndent()
}
