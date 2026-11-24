> [!CAUTION]
>
> **注意：本库是本作者个人私库（不公开分享本库，不建议他人使用），如果想使用本库可以在项目中使用依赖库或克隆分支（可以自己新建一个分支修改本库，不可提交到本库），请不要上传提交，请勿私自外传本项目。**


<div align="center">

<h1>
  Commonmark-Kotlin
</h1>

**一个用于解析 CommonMark Markdown 语法的 Kotlin
多平台库，基于原始的 [commonmark-java](https://github.com/commonmark/commonmark-java) 项目改编，
将 Java 文件全部转为 Kotlin 文件。**

**该库旨在让 `commonmark-java` 能用于 Kotlin 多平台，提供 CommonMark Markdown 语法的解析和渲染功能。**

[![许可证](https://img.shields.io/badge/License-BSD%202--Clause-orange.svg)](https://opensource.org/licenses/BSD-2-Clause)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.dxycw/Commonmark--Kotlin?label=Maven%20Central&color=orange)](https://central.sonatype.com/artifact/io.github.dxycw/Commonmark--Kotlin)
[![Kotlin](https://img.shields.io/badge/kotlin-v2.4.21-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-v1.12.2-blue)](https://github.com/JetBrains/compose-multiplatform)

![badge-android](http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-ios](http://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat)
![badge-desktop](http://img.shields.io/badge/platform-desktop-DB413D.svg?style=flat)
![badge-web](https://img.shields.io/badge/platform-web-59B6EC.svg?style=flat)

</div>


## 特性

- 🚀 **Kotlin 多平台**：支持 Android、iOS、Desktop 和 wasmJs/Js 平台
- 📝 **兼容 CommonMark**：完全支持 CommonMark 规范
- 🔧 **可扩展**：支持多种扩展
- 🎯 **类型安全**：完全用 Kotlin 编写，具备类型安全

## 扩展

该库包含多个实用扩展：

- **commonmark-ext-autolink** - 自动链接识别
- **commonmark-ext-footnotes** - 脚注支持
- **commonmark-ext-gfm-strikethrough** - GitHub 风格 Markdown 删除线
- **commonmark-ext-gfm-tables** - GitHub 风格 Markdown 表格
- **commonmark-ext-heading-anchor** - 标题锚点
- **commonmark-ext-html-converter** - HTML 转换器
- **commonmark-ext-image-attributes** - 图片属性
- **commonmark-ext-ins** - 插入文本支持
- **commonmark-ext-task-list-items** - 任务列表项
- **commonmark-ext-yaml-front-matter** - YAML 前置数据


## 在项目中添加依赖项的方法：

```kotlin
// 在项目的 build.gradle.kts 文件中添加依赖项
kotlin {
    sourceSets {
        // 多平台，
        commonMain.dependencies {
            // 如果使用多平台 Jetpack Compose Multiplatform 项目，请添加以下依赖项
            implementation("io.github.dxycw:commonmark-kotlin:1.0.0")
        }
    }
}
```

## 使用

### 基本用法

```kotlin
val parser = Parser.builder().build()
val document = parser.parse("This is _Sparta_")
val renderer = HtmlRenderer.builder().build()
val html = renderer.render(document) // "This is _Sparta_\n"
```

### 使用扩展

```kotlin
val extensions = listOf(TablesExtension.create())
val parser = Parser.builder()
    .extensions(extensions)
    .build()
val renderer = HtmlRenderer.builder()
    .extensions(extensions)
    .build()

val markdown = """
| Feature | Support |
|---------|---------|
| Tables  | ✅      |
| Kotlin  | ✅      |
"""

val document = parser.parse(markdown)
val html = renderer.render(document)
```

## 示例

可参考 commonmark-java 项目的[示例](https://github.com/commonmark/commonmark-java#usage)


## 更新日志

### 1.0.0

* 优化 项目文档，完善使用方法、平台支持、依赖库等信息；
* 发布到 Maven Central 仓库；
* 把`io.github.feiyin0719:commonmark`依赖库内容导入到本库中；
* 把`io.github.feiyin0719:commonmark-ext-autolink`依赖库内容导入到本库中；
* 把`io.github.feiyin0719:commonmark-ext-footnotes`依赖库内容导入到本库中；
* 把`io.github.feiyin0719:commonmark-ext-gfm-strikethrough`依赖库内容导入到本库中；
* 把`io.github.feiyin0719:commonmark-ext-gfm-tables`依赖库内容导入到本库中；
* 把`io.github.feiyin0719:commonmark-ext-heading-anchor`依赖库内容导入到本库中；
* 把`io.github.feiyin0719:commonmark-ext-html-converter`依赖库内容导入到本库中；
* 把`io.github.feiyin0719:commonmark-ext-image-attributes`依赖库内容导入到本库中；
* 把`io.github.feiyin0719:commonmark-ext-ins`依赖库内容导入到本库中；
* 把`io.github.feiyin0719:commonmark-ext-task-list-items`依赖库内容导入到本库中；
* 把`io.github.feiyin0719:commonmark-ext-yaml-front-matter`依赖库内容导入到本库中；
