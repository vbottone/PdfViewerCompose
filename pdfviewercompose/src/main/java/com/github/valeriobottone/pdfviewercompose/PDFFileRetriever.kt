package com.github.valeriobottone.pdfviewercompose

import java.io.File

interface PDFFileRetriever {
    suspend fun from(source: String): File?
}