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

/**
 * Primeira actividade da aplicação (ecrã principal).
 *
 * Apresenta um campo de texto e um botão "Send". Ao clicar no botão,
 * a mensagem escrita é enviada para a [DisplayMessageActivity] através de uma Intent.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Registo no Logcat de que a aplicação arrancou (tag "MainActivity").
        Log.d("MainActivity", "Aplicação iniciada com sucesso.")

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
}

/**
 * Ecrã principal em Compose: campo de texto + botão para enviar a mensagem.
 *
 * @param modifier modificador aplicado ao contentor da UI.
 */
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

                // 2) Regista no Logcat a mensagem digitada.
                Log.d("MainActivity", "Utilizador digitou a mensagem: $text")

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

/**
 * Pré-visualização do ecrã no Android Studio (não é executada em runtime).
 */
@Preview(showBackground = true)
@Composable
fun GreetingScreenPreview() {
    MaterialTheme {
        GreetingScreen()
    }
}
