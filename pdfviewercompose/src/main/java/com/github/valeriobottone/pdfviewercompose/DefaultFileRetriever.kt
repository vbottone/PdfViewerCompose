package com.github.valeriobottone.pdfviewercompose

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class DefaultFileRetriever(
    private val targetTempFile: File,
    httpClient: HttpClient? = null
) : PDFFileRetriever, AutoCloseable {

    private val client: HttpClient = httpClient ?: HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
    }

    override suspend fun from(source: String): File? {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.get(source)
                val channel = response.bodyAsChannel()
                targetTempFile.outputStream().use { output ->
                    channel.copyTo(output)
                }
                targetTempFile
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    override fun close() {
        if (targetTempFile.exists()) {
            targetTempFile.delete()
        }
        client.close()
    }
}