package com.azzam.cryptocurrency

import android.icu.text.NumberFormat
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import java.io.BufferedReader
import java.util.Locale

fun commonSetup() {
    mockkStatic(NumberFormat::class)
    val mockNumberFormat = mockk<NumberFormat>(relaxed = true)
    every { NumberFormat.getNumberInstance(Locale.US) } returns mockNumberFormat
}

fun readJsonFile(fileName: String): String {
    val classLoader = Thread.currentThread().contextClassLoader
    val inputStream = classLoader?.getResourceAsStream(fileName)
        ?: throw IllegalArgumentException("File $fileName not found in resources.")
    return inputStream.bufferedReader().use(BufferedReader::readText)
}