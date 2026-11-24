package com.dxyc.commonmark

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.commonmark.node.*
import org.commonmark.node.Node
import org.commonmark.parser.Parser


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Commonmarkkotlincs",
    ) {
        App()
    }
}


