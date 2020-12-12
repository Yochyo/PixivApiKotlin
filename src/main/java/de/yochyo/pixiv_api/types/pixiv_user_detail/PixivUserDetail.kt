package de.yochyo.pixiv_api.types.pixiv_user_detail

import de.yochyo.pixiv_api.types.PixivUser

data class PixivUserDetail(
    val user: PixivUser,
    val profile: PixivUserProfile,
    val profilePublicity: PixivUserProfilePublicity,
    val workspace: PixivUserWorkspace
)