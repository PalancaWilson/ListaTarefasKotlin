package com.palancawilson.hellokotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Tag usada no Logcat.
private const val TAG = "MainActivity"

// Ecrã principal: campo de texto + botão "Send".
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 3.1 - Mensagens de log em diferentes níveis (D = debug, I = info).
        Log.d(TAG, "Aplicação iniciada com sucesso.")
        Log.i(TAG, "onCreate: ecrã principal criado.")

        // Experimento de logging/erro intencional do Exercício 3.1.
        executarExperimentoLogcat()

        // Permite desenhar a UI por baixo das barras do sistema (edge-to-edge).
        enableEdgeToEdge()

        // Define o conteúdo da actividade usando Jetpack Compose.
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingScreen()
                }
            }
        }
    }

    // 3.1: gera logs D, I e E; o erro é apanhado de propósito.
    private fun executarExperimentoLogcat() {
        Log.d(TAG, "Debug: a preparar o experimento de logging.")
        Log.i(TAG, "Info: a aplicação está a funcionar normalmente.")

        try {
            val divisor = 0
            val resultado = 10 / divisor
            Log.i(TAG, "Resultado da divisão: $resultado")
        } catch (e: ArithmeticException) {
            // Log.e com o throwable -> imprime a mensagem + stack trace no Logcat.
            Log.e(TAG, "Erro intencional: divisão por zero!", e)
        }
    }
}

// Ecrã principal em Compose.
@Composable
fun GreetingScreen(modifier: Modifier = Modifier) {
    // Contexto da actividade actual (necessário para Toast e startActivity).
    val context = LocalContext.current

    // Estado observável que guarda o texto digitado; remember mantém-no entre recomposições.
    var text by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Campo onde o utilizador introduz a mensagem.
        OutlinedTextField(
            value = text,
            onValueChange = { text = it }, // actualiza o estado a cada alteração
            label = { Text("Type your message") },
            modifier = Modifier.fillMaxWidth()
        )

        // Espaçamento vertical entre o campo e o botão.
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // 1) Mostra uma confirmação rápida (Toast).
                Toast.makeText(context, "Botão clicado!", Toast.LENGTH_SHORT).show()

                // 2) Regista no Logcat a mensagem digitada (níveis D e I).
                Log.d(TAG, "Utilizador digitou a mensagem: $text")
                Log.i(TAG, "Botão Send clicado; a iniciar DisplayMessageActivity.")

                // 3) Cria a Intent para abrir a segunda actividade, levando o texto.
                val intent = Intent(context, DisplayMessageActivity::class.java).apply {
                    putExtra("EXTRA_MESSAGE", text)
                }

                // 4) Inicia a segunda actividade.
                context.startActivity(intent)
            }
        ) {
            Text("Send")
        }
    }
}

// Pré-visualização no Android Studio.
@Preview(showBackground = true)
@Composable
fun GreetingScreenPreview() {
    MaterialTheme {
        GreetingScreen()
    }
}
