package de.yochyo.pixiv_api

import java.net.HttpURLConnection
import java.text.SimpleDateFormat
import java.util.*


fun Date.toPixivDate(): String {
    return SimpleDateFormat("yyyy-MM-dd").format(this)
}

fun HttpURLConnection.readAsString(): String {
    return this.inputStream.use { String(it.readAllBytes()) }
}