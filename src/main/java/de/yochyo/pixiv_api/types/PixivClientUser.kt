package de.yochyo.pixiv_api.types

data class PixivClientUser(
        val profileImageUrls: ProfileImageUrls,
        val id: String,
        val name: String,
        val account: String,
        val mailAddress: String,
        val isPremium: String,
        val xRestrict: Int,
        val isMailAuthorized: Boolean
)