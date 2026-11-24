package com.dxyc.commonmark

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import commonmark_kotlincs.app.shared.generated.resources.Res
import commonmark_kotlincs.app.shared.generated.resources.compose_multiplatform
import org.commonmark.node.AbstractVisitor
import org.commonmark.node.Code
import org.commonmark.node.Emphasis
import org.commonmark.node.Heading
import org.commonmark.node.Link
import org.commonmark.node.Node
import org.commonmark.node.Paragraph
import org.commonmark.node.StrongEmphasis
import org.commonmark.node.Text
import org.commonmark.parser.Parser
import org.jetbrains.compose.resources.painterResource



@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("点击我！")
//            }
//            AnimatedVisibility(showContent) {
//                val greeting = remember { Greeting().greet() }
//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                }
//            }
            val markdown = """
                This is _Sparta_
                
                * 1212121
                
                # 这是一个一级标题
                ## 这是一个二级标题
                ### 这是一个三级标题

                ``` kotlin
                    MarkdownText(markdown)
                ```

                - 这是一个列表项
            """.trimIndent()

            MarkdownText(markdown)
        }
    }
}

@Composable
fun MarkdownText(markdown: String) {
    val parser = remember { Parser.builder().build() }
    val document = remember(markdown) { parser.parse(markdown) }
    val annotatedString = remember(markdown) {
        AnnotatedStringVisitor().traverse(document)
    }//remember(document) { buildAnnotatedString(document) }

    Text(text = annotatedString) // 注意：块级元素的换行/布局可能不够完美
}

class AnnotatedStringVisitor(
    private val builder: AnnotatedString.Builder = AnnotatedString.Builder()
) : AbstractVisitor() {

    // 遍历入口
    fun traverse(node: Node): AnnotatedString {
        node.accept(this)
        return builder.toAnnotatedString()
    }

    // 1. 普通文本：直接追加
    override fun visit(text: Text) {
        builder.append(text.literal)
    }

    // 2. 加粗 (StrongEmphasis)：记录起点，递归子节点，结束时添加样式
    override fun visit(strongEmphasis: StrongEmphasis) {
        val start = builder.length
        visitChildren(strongEmphasis)  // 递归处理内部的 Text 等节点
        builder.addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, builder.length)
    }

    // 3. 斜体 (Emphasis)
    override fun visit(emphasis: Emphasis) {
        val start = builder.length
        visitChildren(emphasis)
        builder.addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, builder.length)
    }

    // 4. 行内代码 (Code)
    override fun visit(code: Code) {
        val start = builder.length
        builder.append(code.literal)
        builder.addStyle(SpanStyle(fontFamily = FontFamily.Monospace), start, builder.length)
    }

    // 5. 链接 (Link)：为文本添加 LinkAnnotation
    override fun visit(link: Link) {
        val start = builder.length
        visitChildren(link) // 先追加链接文字
        link.destination.let { url ->
            builder.addLink(
                url =  LinkAnnotation.Url(url),
                start = start,
                end = builder.length
            )
        }
    }

    // 6. 段落 (Paragraph)：结尾换行
    override fun visit(paragraph: Paragraph) {
        visitChildren(paragraph)
        builder.append("\n\n")
    }

    // 7. 标题 (Heading)：追加文本并换行，可按 level 添加样式
    override fun visit(heading: Heading) {
        val start = builder.length
        visitChildren(heading)
        // 示例：一级标题加粗
        if (heading.level == 1) {
            builder.addStyle(
                SpanStyle(
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 10.sp
                ), start, builder.length)
        }else if (heading.level == 2) {
            builder.addStyle(SpanStyle(
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            ), start, builder.length)
        }else if (heading.level == 3) {
            builder.addStyle(SpanStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            ), start, builder.length)
        }
        builder.append("\n")
    }

}