package com.palancawilson.hellokotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DisplayMessageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val message = intent.getStringExtra("EXTRA_MESSAGE") ?: "Nenhuma mensagem recebida."

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DisplayMessage(message)
                }
            }
        }
    }
}

@Composable
fun DisplayMessage(message: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = message, fontSize = 24.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayMessagePreview() {
    MaterialTheme {
        DisplayMessage("Olá, HelloKotlin!")
    }
}
