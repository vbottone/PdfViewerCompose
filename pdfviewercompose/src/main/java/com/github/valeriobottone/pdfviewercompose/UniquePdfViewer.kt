package com.github.valeriobottone.pdfviewercompose

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import java.io.File

@Composable
fun UniquePdfViewer(url: String, uniqueFile: File, loadingContent: @Composable BoxScope.() -> Unit = {}) {
    val fileRetriever = DefaultFileRetriever(
        targetTempFile = uniqueFile
    )

    PdfViewer(
        url = url,
        fileRetriever = fileRetriever,
        loadingContent = loadingContent
    )
}