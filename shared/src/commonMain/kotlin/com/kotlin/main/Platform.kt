package com.kotlin.main

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform