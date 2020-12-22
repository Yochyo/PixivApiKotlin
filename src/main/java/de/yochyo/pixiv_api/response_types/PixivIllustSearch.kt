package de.yochyo.pixiv_api.response_types

import de.yochyo.pixiv_api.PixivApi
import de.yochyo.pixiv_api.iterables.SuspendedIterator


data class PixivIllustSearch(
    val illusts: List<PixivIllust>,
    val nextUrl: String?,
    val searchSpanLimit: Int?
) {
    fun iterator(api: PixivApi) = PixivIllustSearchIterator(api, this)
}

class PixivIllustSearchIterator(private val api: PixivApi, search: PixivIllustSearch) : SuspendedIterator<PixivIllustSearch> {
    var url: String? = search.nextUrl
    override suspend fun hasNext(): Boolean {
        return url != null
    }

    override suspend fun next(): PixivIllustSearch {
        val url = this.url ?: throw NoSuchElementException("reached end of list")
        val result = api.getResponse(url, PixivIllustSearch::class.java)
        this.url = result.nextUrl
        return result
    }
}