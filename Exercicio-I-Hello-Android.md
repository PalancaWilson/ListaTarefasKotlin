# Exercício I – Hello Android (Duas Actividades) — Guia do projecto HelloKotlin

Documentação com o código final e **onde colocar** cada parte neste projecto.

- **Package:** `com.palancawilson.hellokotlin`
- **Stack:** AGP 9.4.1 + Kotlin integrado 2.2.10 + Jetpack Compose (BOM 2026.02.01)
- **minSdk:** 24 · **compileSdk/targetSdk:** 37

> O projecto foi convertido de **Views/XML** para **Jetpack Compose**.
> Foram removidos `SecondViews.kt`, `activity_main.xml` e `activity_second_views.xml`.

---

## 1. Configuração Gradle (activar Compose)

### 1.1 `gradle/libs.versions.toml`

**Onde:** ficheiro de catálogo de versões, na raiz do projecto.
Adicionar `kotlin`, `composeBom`, as bibliotecas Compose e o plugin do compilador:

```toml
[versions]
agp = "9.4.1"
kotlin = "2.2.10"          # igual ao Kotlin integrado no AGP 9.4.1
# ...
composeBom = "2026.02.01"

[libraries]
# ...
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityKtx" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
androidx-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
androidx-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
androidx-material3 = { group = "androidx.compose.material3", name = "material3" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
compose-compiler = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
```

### 1.2 `build.gradle.kts` (raiz do projecto)

**Onde:** ficheiro `build.gradle.kts` de topo (o que tem `apply false`).

```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.compose.compiler) apply false
}
```

### 1.3 `app/build.gradle.kts` (módulo `app`)

**Onde:** ficheiro `app/build.gradle.kts`. Aplicar o plugin, ligar `compose` e juntar as dependências.

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}

android {
    // ...
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    // ...
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
}
```

> **Porquê?** `buildFeatures.compose = true` liga o Compose no módulo e o plugin
> `org.jetbrains.kotlin.plugin.compose` fornece o compilador Compose.

---

## 2. `MainActivity.kt` — 1.ª actividade (Compose)

**Onde:** `app/src/main/java/com/palancawilson/hellokotlin/MainActivity.kt`
(substitui o ficheiro inteiro).

```kotlin
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
```

---

## 3. `DisplayMessageActivity.kt` — 2.ª actividade

**Onde:** novo ficheiro
`app/src/main/java/com/palancawilson/hellokotlin/DisplayMessageActivity.kt`.

```kotlin
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
```

---

## 4. `AndroidManifest.xml` — declarar a 2.ª actividade

**Onde:** `app/src/main/AndroidManifest.xml`, dentro de `<application> ... </application>`.
A entrada `.SecondViews` foi substituída por `.DisplayMessageActivity`:

```xml
<activity
    android:name=".DisplayMessageActivity"
    android:exported="false"
    android:windowSoftInputMode="adjustResize" />
```

---

## 5. Como testar

1. Abrir **Device Manager** e iniciar o emulador (ou ligar telemóvel com USB debugging).
2. Clicar **Run ▶**.
3. Escrever um texto → **Send**.
4. Verificar: aparece o **Toast**, surge a linha no **Logcat** (tag `MainActivity`) e abre a
   `DisplayMessageActivity` com a mensagem. O botão **back** regressa.

---

## 6. Respostas às perguntas

**1. Qual o papel do `Intent`?**
Descreve a operação a executar (abrir uma actividade) e transporta os dados nos *extras*
(`putExtra`/`getStringExtra`). Aqui leva o texto da `MainActivity` para a `DisplayMessageActivity`.

**2. Qual o papel de `remember` e `mutableStateOf`?**
`mutableStateOf` cria um estado observável; `remember` mantém o valor entre recomposições.
Juntos guardam o texto do campo e fazem a UI recompor automaticamente.

**3. Porquê `LocalContext.current`?**
Os composables não têm acesso directo ao `Context` necessário para `Toast` e `startActivity`;
`LocalContext.current` disponibiliza o `Context` da actividade actual.

**4. Onde configurar a filtragem do Logcat?**
Na aba **Logcat** do Android Studio: através da *query bar* (package, tag, nível, palavras‑chave)
e dos filtros guardáveis (ícone de funil).
