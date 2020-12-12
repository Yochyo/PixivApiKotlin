package de.yochyo.pixiv_api.types

data class PixivTag(
    //TODO muss das tag heißen? da geht was nicht
  //  val tag: String?,
    val name: String?,
    val translatedName: String?,
    val addedByUploadedUser: Boolean?,
    val illust: PixivIllust?,
    val isRegistered: Boolean?
)
