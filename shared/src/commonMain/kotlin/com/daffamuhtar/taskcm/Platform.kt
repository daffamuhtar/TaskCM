package com.daffamuhtar.taskcm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform