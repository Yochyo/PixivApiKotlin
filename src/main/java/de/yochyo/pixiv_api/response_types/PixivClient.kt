package de.yochyo.pixiv_api.response_types

data class PixivClient(
    val accessToken: String,
    val expiresIn: Int,
    val tokenType: String,
    val scope: String,
    val refreshToken: String,
    val user: PixivClientUser,
    val deviceToken: String?
)