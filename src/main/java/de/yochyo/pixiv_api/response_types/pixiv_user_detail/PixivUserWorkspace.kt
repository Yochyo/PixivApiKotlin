package de.yochyo.pixiv_api.response_types.pixiv_user_detail

data class PixivUserWorkspace(
    val pc: String,
    val monitor: String,
    val tool: String,
    val scanner: String,
    val tablet: String,
    val mouse: String,
    val printer: String,
    val desktop: String,
    val music: String,
    val desk: String,
    val chair: String,
    val comment: String,
    val workspaceImageUrl: String?
)
