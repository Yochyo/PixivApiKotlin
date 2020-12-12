package de.yochyo.pixiv_api.types

import com.fasterxml.jackson.annotation.JsonProperty

data class ProfileImageUrls(
    @JsonProperty("px_16x16") val px16X16: String?,
    @JsonProperty("px_50x50") val px50X50: String?,
    @JsonProperty("px_170x170") val px170X170: String?,
    val medium: String?
)