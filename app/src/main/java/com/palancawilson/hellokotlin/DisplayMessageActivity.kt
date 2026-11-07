package com.palancawilson.hellokotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Segunda actividade da aplicação.
 *
 * Recebe a mensagem enviada pela [MainActivity] através da Intent
 * (extra "EXTRA_MESSAGE") e apresenta-a centrada no ecrã.
 */
class DisplayMessageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Recupera o texto enviado pela MainActivity. Se não existir, usa uma mensagem por defeito.
        val message = intent.getStringExtra("EXTRA_MESSAGE") ?: "Nenhuma mensagem recebida."

        // Define o conteúdo da actividade com Jetpack Compose.
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Mostra a mensagem e permite voltar (finish() fecha esta actividade).
                    DisplayMessage(message = message, onBack = { finish() })
                }
            }
        }
    }
}

/**
 * Composable que apresenta a mensagem recebida e um botão "Voltar".
 *
 * @param message texto a apresentar.
 * @param onBack acção executada ao clicar no botão "Voltar".
 * @param modifier modificador aplicado ao contentor.
 */
@Composable
fun DisplayMessage(message: String, onBack: () -> Unit, modifier: Modifier = Modifier) {
    // Box centra o conteúdo (a Column) no ecrã.
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Mensagem recebida da primeira actividade.
            Text(text = message, fontSize = 24.sp)

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
        DisplayMessage(message = "Olá, HelloKotlin!", onBack = {})
    }
}
