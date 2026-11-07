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

// Segunda actividade: recebe a mensagem e mostra o resumo.
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

        // Registo do resumo no Logcat para facilitar a verificação (3.1).
        Log.d("DisplayMessageActivity", "Resumo gerado:\n$resumo")
        Log.i("DisplayMessageActivity", "Mensagem recebida com ${bundle.contar} palavra(s).")

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

// Divide a mensagem em palavras não vazias.
fun dividirPalavras(message: String): List<String> =
    message.split(" ").filter { it.isNotBlank() }

// 2.1: resumo com when/if baseado na contagem de palavras.
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

// Mostra a mensagem original, o resumo e o botão "Voltar".
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

// Pré-visualização no Android Studio.
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
