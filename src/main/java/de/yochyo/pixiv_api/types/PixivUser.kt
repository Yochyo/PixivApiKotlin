package de.yochyo.pixiv_api.types

data class PixivUser(
    val id: Int,
    val name: String,
    val account: String,
    val profileImageUrls: ProfileImageUrls,
    val comment: String?,
    val isFollowed: Boolean
)
