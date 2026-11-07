package com.palancawilson.hellokotlin

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

// Teste instrumental (dispositivo).
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Contexto da app.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.palancawilson.hellokotlin", appContext.packageName)
    }
}