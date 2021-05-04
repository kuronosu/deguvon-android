package dev.kuronosu.deguvon.utils

import com.google.gson.Gson
import java.security.MessageDigest
import java.util.*

fun String.sha256(): String = Base64.getEncoder().encodeToString(
    MessageDigest
        .getInstance("SHA-256")
        .digest(this.toByteArray())
)

fun <T> Hash(data: T): String = Gson().toJson(data).sha256()
