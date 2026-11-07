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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "Aplicação iniciada com sucesso.")
        enableEdgeToEdge()
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

@Composable
fun GreetingScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var text by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Type your message") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Botão clicado!", Toast.LENGTH_SHORT).show()
                Log.d("MainActivity", "Utilizador digitou a mensagem: $text")
                val intent = Intent(context, DisplayMessageActivity::class.java).apply {
                    putExtra("EXTRA_MESSAGE", text)
                }
                context.startActivity(intent)
            }
        ) {
            Text("Send")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingScreenPreview() {
    MaterialTheme {
        GreetingScreen()
    }
}
