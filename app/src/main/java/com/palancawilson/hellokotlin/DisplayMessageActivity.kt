package com.palancawilson.hellokotlin

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Segunda actividade da aplicação.
 *
 * Recebe a mensagem enviada pela [MainActivity] através da Intent
 * (extra "EXTRA_MESSAGE"), constrói um [PalavraBundle] e apresenta o
 * resumo rico gerado por [gerarResumo].
 */
class DisplayMessageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Recupera o texto enviado pela MainActivity. Se não existir, usa uma mensagem por defeito.
        val message = intent.getStringExtra("EXTRA_MESSAGE") ?: "Nenhuma mensagem recebida."

        // 2.4 - Divide a mensagem, constrói o bundle e gera o resumo processado.
        val palavras = dividirPalavras(message)
        val bundle = PalavraBundle(palavras)
        val resumo = gerarResumo(bundle)

        // Registo do resumo no Logcat para facilitar a verificação.
        Log.d("DisplayMessageActivity", "Resumo gerado:\n$resumo")

        // Define o conteúdo da actividade com Jetpack Compose.
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Apresenta a mensagem original, o resumo e o botão "Voltar".
                    DisplayMessage(
                        message = message,
                        summary = resumo,
                        onBack = { finish() }
                    )
                }
            }
        }
    }
}

/**
 * Divide a mensagem numa lista de palavras, ignorando espaços vazios.
 *
 * @param message texto a dividir.
 * @return lista de palavras não vazias.
 */
fun dividirPalavras(message: String): List<String> =
    message.split(" ").filter { it.isNotBlank() }

/**
 * Gera um resumo simples com base no número de palavras (Exercício 2.1).
 *
 * @param message texto introduzido pelo utilizador.
 * @return resumo textual da contagem de palavras.
 */
fun gerarResumoContagem(message: String): String {
    val words = dividirPalavras(message)
    val wordCount = words.size

    var summary = when {
        wordCount == 0 -> "Nenhuma palavra encontrada!"
        wordCount == 1 -> "Apenas uma palavra!"
        else -> "Foram digitadas $wordCount palavras."
    }

    if (wordCount > 5) {
        summary += " (mensagem longa)"
    }

    return summary
}

/**
 * Composable que apresenta a mensagem original, o resumo processado e um botão "Voltar".
 *
 * @param message texto original recebido da primeira actividade.
 * @param summary resumo rico gerado por [gerarResumo].
 * @param onBack acção executada ao clicar no botão "Voltar".
 * @param modifier modificador aplicado ao contentor.
 */
@Composable
fun DisplayMessage(
    message: String,
    summary: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Box centra o conteúdo (a Column) no ecrã.
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            // Permite rolar caso o resumo não caiba no ecrã.
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            // Mensagem original recebida da primeira actividade.
            Text(
                text = "Mensagem: $message",
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Resumo processado (contagem, média, palavra mais longa e palavras longas).
            Text(
                text = summary,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botão visível para regressar à actividade anterior.
            Button(onClick = onBack) {
                Text("Voltar")
            }
        }
    }
}

/**
 * Pré-visualização do ecrã no Android Studio (não é executada em runtime).
 */
@Preview(showBackground = true)
@Composable
fun DisplayMessagePreview() {
    MaterialTheme {
        DisplayMessage(
            message = "Olá HelloKotlin mundo",
            summary = gerarResumo(PalavraBundle(listOf("Olá", "HelloKotlin", "mundo"))),
            onBack = {}
        )
    }
}
