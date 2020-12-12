package de.yochyo.pixiv_api.response_types


internal data class UgoiraMetaDataWrapper(val ugoiraMetadata: UgoiraMetaData)

data class UgoiraMetaData(val zipUrls: UgoiraZipUrls, val frames: List<UgoiraFrame>)

data class UgoiraZipUrls(val medium: String)
data class UgoiraFrame(val file: String, val delay: Int)