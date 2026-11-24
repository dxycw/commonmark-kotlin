package com.dxyc.commonmark

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform