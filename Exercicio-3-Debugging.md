# Exercício 3 – Debugging e Logcat

Guia do que foi implementado (3.1) e dos passos manuais no Android Studio (3.2 e 3.3),
com as respostas às perguntas.

---

## 3.1. Filtro de Logcat

### O que foi adicionado no código

Em `MainActivity.kt`:

- `Log.d(TAG, "Aplicação iniciada com sucesso.")` — nível **Debug**.
- `Log.i(TAG, "onCreate: ecrã principal criado.")` — nível **Info**.
- `executarExperimentoLogcat()` — produz `Log.d`, `Log.i` e `Log.e`.
  O erro é uma **divisão por zero** apanhada num `try/catch`, por isso a app **não fecha**;
  o `Log.e(TAG, "...", e)` imprime a **excepção + stack trace** no Logcat.

Em `DisplayMessageActivity.kt`:

- `Log.d("DisplayMessageActivity", "Resumo gerado:\n$resumo")`.
- `Log.i("DisplayMessageActivity", "Mensagem recebida com ${bundle.contar} palavra(s).")`.

### Resultado observado no Logcat

```
D MainActivity: Aplicação iniciada com sucesso.
I MainActivity: onCreate: ecrã principal criado.
D MainActivity: Debug: a preparar o experimento de logging.
I MainActivity: Info: a aplicação está a funcionar normalmente.
E MainActivity: Erro intencional: divisão por zero!
E MainActivity: java.lang.ArithmeticException: divide by zero
E MainActivity:     at ...MainActivity.executarExperimentoLogcat(MainActivity.kt:80)
E MainActivity:     at ...MainActivity.onCreate(MainActivity.kt:49)
...
```

### Como filtrar no Android Studio

Na aba **Logcat** (parte inferior):

- **Por tag:** escrever `tag:MainActivity` na barra de pesquisa.
- **Por nível:** escolher `Debug`, `Info`, `Warn`, `Error` na lista de níveis.
- **Por package:** escolher o processo `com.palancawilson.hellokotlin` na lista de processos.
- Guardar filtros com o ícone de **funil** (filtros personalizados).

### Ver o crash "real" (opcional)

Para ver a excepção **não apanhada** (a app fecha), remover o `try/catch` em
`executarExperimentoLogcat` deixando apenas `val resultado = 10 / 0`. O Logcat mostra
`FATAL EXCEPTION` com o stack trace. (O código entregue mantém o `try/catch` para não fechar a app.)

---

## 3.2. Usando Breakpoints e o Debugger

### Passos no Android Studio

1. Abrir `MainActivity.kt` e clicar na **margem esquerda** da linha dentro de `onCreate`
   (ex.: a linha do `Log.d`) para criar um **breakpoint** (ponto vermelho).
2. Executar em **debug**: menu **Run → Debug 'app'** ou o ícone do **bug** (atalho `Shift+F9`).
3. Quando a execução parar no breakpoint, usar a janela **Debug**:
   - **Step Over** (`F8`) — avança uma linha, **sem entrar** nos métodos chamados.
   - **Step Into** (`F7`) — **entra** no método chamado na linha actual.
   - **Step Out** (`Shift+F8`) — sai do método actual e volta ao chamador.
   - **Resume Program** (`F9`) — continua até ao próximo breakpoint/fim.
   - **Variables** — inspeccionar valores.
   - **Evaluate Expression** (`Alt+F8`) — calcular expressões em runtime.
   - **Threads / Frames** — ver threads e a pilha de chamadas.
4. Para testar o handler do botão, colocar um breakpoint **dentro do `onClick`** em
   `GreetingScreen` e clicar em **Send** na app.

> Nota (Compose): os composables podem ser executados várias vezes por **recomposição**,
> pelo que um breakpoint num composable pode ser atingido mais do que uma vez — é normal.

### Resposta à pergunta

**O que acontece quando inspecciona um objecto no painel Variables?**
O objecto é **expandido** e mostram-se as suas propriedades/campos e respectivos valores
(incluindo objectos aninhados, listas, arrays, `null`). Permite "mergulhar" na estrutura do
objecto e perceber o seu estado em runtime. Também se pode usar **Evaluate Expression** para
avaliar expressões sobre esses valores.

**O que fazem "Step Over" e "Step Into"?**
- **Step Over** executa a linha actual e pára na **linha seguinte do mesmo método**, sem entrar
  nas funções chamadas (trata a chamada como uma caixa negra).
- **Step Into** **entra dentro** da função chamada na linha actual, permitindo depurar linha a
  linha no seu interior.

---

## 3.3. Inspecção de Estado de Execução

### Passos

1. Colocar um **breakpoint** dentro do `onClick` do botão **Send** (em `GreetingScreen`).
2. Executar em **debug** (`Shift+F9`) e, na app, **escrever uma mensagem** e **clicar em Send**.
3. A execução pára no breakpoint. Inspeccionar:
   - **Variables:** `text` (o texto digitado), `context`, `intent`, etc.
   - **Threads:** a *main thread* (UI) e as restantes threads; ver a **pilha de chamadas**.
   - **Memory:** usar **View → Tool Windows → Profiler** (ou **Memory** no Profiler) para
     observar a utilização de memória enquanto a app corre.
4. Usar **Resume** (`F9`) para continuar e **Step Over/Into** para percorrer o handler.

### O que observar

- O valor de `text` deve corresponder ao que foi escrito no campo.
- A `Intent` deve conter o extra `EXTRA_MESSAGE` com esse texto.
- O estado mantém-se coerente entre a `MainActivity` e a `DisplayMessageActivity`.

---

## Referência

- [Ferramenta de Debugging do Android Studio](https://developer.android.com/studio/debug)
- [Documentação do Logcat](https://developer.android.com/studio/debug/logcat)
