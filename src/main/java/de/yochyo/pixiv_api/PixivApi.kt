package de.yochyo.pixiv_api

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import de.yochyo.pixiv_api.request_enums.HttpMethod
import de.yochyo.pixiv_api.types.*
import de.yochyo.pixiv_api.types.pixiv_user_detail.PixivUserDetail
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.security.MessageDigest
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap

@Suppress("NAME_SHADOWING")
class PixivApi(var username: String?, var password: String?) {
    private val mapper = JsonMapper.builder().apply {
        addModule(KotlinModule())
        propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }.build()

    private var additionalHeaders = map {
        put("App-OS", "ios")
        put("App-OS-Version", "9.3.3")
        put("App-Version", "6.0.9")
        put("User-Agent", "PixivIOSApp/5.8.7")
    }

    var auth: PixivClient? = null

    // val refreshToken get() = auth?.refreshToken
    val refreshToken get() = de.yochyo.pixiv_api.refreshToken

    companion object {
        const val BASE_URL = "https://app-api.pixiv.net"
        const val CLIENT_ID = "MOBrBDS8blbauoSck0ZfDbtuzpyT"
        const val CLIENT_SECRET = "lsACyCD94FhDUtGTXi3QzcFE2uU1hqtDaKeqrdwj"
        const val HASH_SECRET = "28c1fdd170a5204386cb1313c7077b34f83e4aaf4aa829ce78c231e05b0bae2c"
        const val FILTER = "for_ios"
    }

    suspend fun login(username: String, password: String): PixivClient = withContext(Dispatchers.IO) {
        this@PixivApi.username = username
        this@PixivApi.password = password
        fun getClientHash(timeString: String): String {
            val h = MessageDigest.getInstance("MD5").digest((timeString + HASH_SECRET).toByteArray(Charsets.UTF_8))
            val hexString = StringBuilder()
            for (element in h) {
                val hex = Integer.toHexString(0xFF and element.toInt())
                if (hex.length == 1)
                    hexString.append('0')
                hexString.append(hex)
            }
            return hexString.toString()
        }

        val localTime = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+00:00").withZone(ZoneId.of("+0"))
            .format(Instant.now())

        val headers = map {
            put("X-Client-Time", localTime)
            put("X-Client-Hash", getClientHash(localTime))
        }

        val refreshToken = this@PixivApi.refreshToken
        val data = map {
            put("client_id", CLIENT_ID)
            put("client_secret", CLIENT_SECRET)
            put("get_secure_url", "1")
            if (refreshToken == null) {
                put("grant_type", "password")
                put("username", username)
                put("password", password)
            } else {
                put("grant_type", "refresh_token")
                put("refresh_token", refreshToken)
            }
        }


        val auth = getResponse<PixivClient>(HttpMethod.POST, "https://oauth.secure.pixiv.net/auth/token", data = data, headers = headers)
        setAccessToken(auth.accessToken)

        this@PixivApi.auth = auth
        setAccessToken(auth.accessToken)
        auth
    }

    private fun setAccessToken(accessToken: String) {
        additionalHeaders["Authorization"] = "Bearer $accessToken"
    }


    /**
     * Get a user's profile.
     */
    suspend fun getUserDetail(id: Int, params: PixivParams = PixivParams()): PixivUserDetail {
        val params = params.copy(userId = id, filter = FILTER)
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/user/detail", params = params.toMapByJackson())
    }

    /**
     * Retrieves all of a users illusts.
     */
    suspend fun getUserIllusts(id: Int, params: PixivParams = PixivParams()): PixivIllustSearch {
        val params = params.copy(userId = id, type = "illust", filter = FILTER)
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/user/illusts", params = params.toMapByJackson())
    }

    /**
     * Follows a user.
     */
    suspend fun followUser(id: Int, params: PixivParams = PixivParams()) {
        val params = params.copy(userId = id, restrict = params.restrict ?: "public", filter = FILTER)
        getResponse<Unit>(HttpMethod.POST, "$BASE_URL/v1/user/follow/add", data = params.toMapByJackson())
    }

    /**
     * Unfollows a user.
     */
    suspend fun unfollowUser(id: Int, params: PixivParams = PixivParams()) {
        val params = params.copy(userId = id, restrict = params.restrict ?: "public", filter = FILTER)
        getResponse<Unit>(HttpMethod.POST, "$BASE_URL/v1/user/follow/delete", data = params.toMapByJackson())
    }

    //TODO restrict sollte ein enum sein?
    /**
     * Gets a user's bookmarked illusts.
     */
    suspend fun getBookmarkedIllustsFromUser(id: Int, params: PixivParams = PixivParams()): PixivIllustSearch {
        val params = params.copy(userId = id, restrict = params.restrict ?: "public", filter = FILTER)
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/user/bookmarks/illust", params = params.toMapByJackson())
    }

    /**
     * Gets the users that a user is following.
     */
    suspend fun getFollowedUsersOf(id: Int, params: PixivParams = PixivParams()): PixivUserSearch {
        val params = params.copy(userId = id, restrict = params.restrict ?: "public", filter = FILTER)
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/user/following", params = params.toMapByJackson())
    }

    /**
     * Gets the users who follow a user.
     */
    suspend fun getFollowersOfUser(id: Int, params: PixivParams = PixivParams()): PixivUserSearch {
        val params = params.copy(userId = id, filter = FILTER)
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/user/follower", params = params.toMapByJackson())
    }

    /**
     * Gets your friends on Mypixiv.
     */
    suspend fun getFriendsMypixiv(id: Int, params: PixivParams = PixivParams()): PixivUserSearch {
        val params = params.copy(userId = id)
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/user/mypixiv", params = params.toMapByJackson())
    }

    /**
     * Gets a user list.
     */
    suspend fun userList(id: Int, params: PixivParams = PixivParams()) {
        TODO()
        val params = params.copy(userId = id, filter = FILTER)
        getResponse<Unit>(HttpMethod.GET, "$BASE_URL/v1/user/list", params = params.toMapByJackson())
    }

    /**
     * Returns detailed info for a pixiv illust.
     */
    suspend fun getIllustDetails(id: Int, params: PixivParams = PixivParams()): PixivIllustDetail {
        val params = params.copy(illustId = id, filter = FILTER)
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/illust/detail", params = params.toMapByJackson())
    }

    /**
     * Searches new illusts.
     */
    suspend fun getNewIllusts(params: PixivParams = PixivParams()): PixivIllustSearch {
        val params = params.copy(contentType = "illust", filter = FILTER)
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/illust/new", params = params.toMapByJackson())
    }

    /**
     * Searches new illusts from users you follow.
     */
    //TODO Pixiv Params individueller machen
    suspend fun getNewIllustsOfFollowed(id: Int, params: PixivParams = PixivParams()): PixivIllustSearch {
        val params = params.copy(userId = id, restrict = params.restrict ?: "public", filter = FILTER)
        return getResponse(HttpMethod.GET, "$BASE_URL/v2/illust/follow", params = params.toMapByJackson())
    }

    /**
     * Returns the comments on an illust.
     */
    suspend fun getIllustComments(id: Int, params: PixivParams = PixivParams()): PixivCommentSearch {
        val params = params.copy(illustId = id, includeTotalComments = true)
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/illust/comments", params = params.toMapByJackson())
    }

    /**
     * Searches for illusts related to the one provided.
     */
    suspend fun getRelatedIllusts(id: Int, params: PixivParams = PixivParams()): PixivIllustSearch {
        val params = params.copy(illustId = id, filter = FILTER)
        return getResponse(HttpMethod.GET, "$BASE_URL/v2/illust/related", params = params.toMapByJackson())
    }

    /**
     * Returns recommended illusts.
     */
    suspend fun getRecommendedIllusts(params: PixivParams = PixivParams()): PixivIllustSearch {
        val params = params.copy(contentType = "illust", includeRankingLabel = true, filter = FILTER)
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/illust/recommended", params = params.toMapByJackson())
    }

    /**
     * Returns recommended illusts (logged out).
     */
    suspend fun getRecommendedIllustsNoLogin(params: PixivParams = PixivParams()): PixivIllustSearch {
        val params = params.copy(includeRankingIllusts = true, filter = FILTER)
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/illust/recommended-nologin", params = params.toMapByJackson())
    }

    /**
     * Returns top daily illusts by default.
     */
    suspend fun getIllustRanking(params: PixivParams = PixivParams()): PixivIllustSearch {
        val params = params.copy(filter = FILTER, mode = params.mode ?: Mode.DAY)
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/illust/ranking", params = params.toMapByJackson())
    }

    /**
     * Returns an array of trending tags.
     */
    suspend fun getTrendingTags(params: PixivParams = PixivParams()): PixivTrendTags {
        val params = params.copy(filter = FILTER)
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/trending-tags/illust", params = params.toMapByJackson())
    }

    /**
     * Searches for illusts with the provided query.
     */
    suspend fun searchIllust(word: String, params: PixivParams = PixivParams()): PixivIllustSearch {
        val params =
            params.copy(word = word, filter = FILTER, sort = params.sort ?: "date_desc", searchTarget = params.searchTarget ?: "partial_match_for_tags")
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/search/illust", params = params.toMapByJackson())
    }

    /**
     * Searches for novels with the provided query.
     */
    suspend fun searchNovel(word: String, params: PixivParams = PixivParams()): PixivNovelSearch {
        val params =
            params.copy(word = word, filter = FILTER, sort = params.sort ?: "date_desc", searchTarget = params.searchTarget ?: "partial_match_for_tags")
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/search/novel", params = params.toMapByJackson())
    }

    /**
     * Searches for users with the provided query.
     */
    suspend fun searchUser(word: String, params: PixivParams = PixivParams()): PixivUserSearch {
        val params = params.copy(word = word, filter = FILTER)
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/search/user", params = params.toMapByJackson())
    }

    /**
     * Returns an array of auto-completed words from the input.
     */
    suspend fun searchAutoCompletion(word: String): PixivAutoComplete {
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/search/autocomplete", params = map { put("word", word) })
    }

    /**
     * Returns detailed info on a bookmark.
     */
    suspend fun getIllustBookmarkDetail(id: Int, params: PixivParams = PixivParams()): PixivBookmarkDetail {
        val params = params.copy(illustId = id)
        return getResponse<PixivBookmarkDetailWrapper>(HttpMethod.GET, "$BASE_URL/v2/illust/bookmark/detail", params = params.toMapByJackson()).bookmarkDetail
    }

    /**
     * Adds a new bookmark.
     */
    suspend fun bookmarkIllust(id: Int, params: PixivParams = PixivParams()) {
        val params = params.copy(illustId = id, restrict = params.restrict ?: "public")
        getResponse<Unit>(HttpMethod.POST, "$BASE_URL/v2/illust/bookmark/add", data = params.toMapByJackson())
    }

    /**
     * Deletes a bookmark.
     */
    suspend fun unbookmarkIllust(id: Int, params: PixivParams = PixivParams()) {
        val params = params.copy(illustId = id)
        getResponse<Unit>(HttpMethod.POST, "$BASE_URL/v1/illust/bookmark/delete", data = params.toMapByJackson())
    }

    /**
     * Searches your bookmark tags.
     */
    suspend fun getBookmarkTagIllusts(params: PixivParams = PixivParams()): PixivBookmarkSearch {
        val params = params.copy(restrict = params.restrict ?: "public")
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/user/bookmark-tags/illust", params = params.toMapByJackson())
    }

    /**
     * Searches recommended novels.
     */
    suspend fun getRecommendedNovels(params: PixivParams = PixivParams()): PixivNovelSearch {
        val params = params.copy(filter = FILTER, includeRankingNovels = true)
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/novel/recommended", params = params.toMapByJackson())
    }

    /**
     * Searches new manga.
     */
    //TODO return is Unit
    suspend fun getNewMangas(params: PixivParams = PixivParams()) {
        TODO()
        val params = params.copy(filter = FILTER, contentType = params.contentType ?: "manga")
        getResponse<Unit>(HttpMethod.GET, "$BASE_URL/v1/manga/new", params = params.toMapByJackson())
    }

    /**
     * Searches recommended manga.
     */
    suspend fun getRecommendedManga(params: PixivParams = PixivParams()): PixivMangaSearch {
        val params = params.copy(filter = FILTER, includeRankingLabel = true)
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/manga/recommended", params = params.toMapByJackson())
    }

    /**
     * Searches recommended novels (logged out).
     */
    suspend fun getNovelRecommendedNoLogin(params: PixivParams = PixivParams()): PixivNovelSearch {
        val params = params.copy(filter = FILTER, includeRankingNovels = true)
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/novel/recommended", params = params.toMapByJackson())
    }

    /**
     * Searches new novels.
     */
    suspend fun getNewNovels(params: PixivParams = PixivParams()): PixivNovelSearch {
        val params = params.copy(filter = FILTER)
        return getResponse(HttpMethod.GET, "$BASE_URL/v1/novel/new", params = params.toMapByJackson())
    }

    /**
     * Retrieves the zip url and frames for a Pixiv Ugoira.
     */
    suspend fun getUgoiraMetaData(id: Int, params: PixivParams = PixivParams()): UgoiraMetaData {
        val params = params.copy(illustId = id, filter = FILTER)
        return getResponse<UgoiraMetaDataWrapper>(HttpMethod.GET, "$BASE_URL/v1/ugoira/metadata", params = params.toMapByJackson()).ugoiraMetadata
    }

    /**
     * builds a connection to an url
     */
    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun getHttpUrlConnection(
        method: HttpMethod, url: String, headers: MutableMap<String, String> = HashMap(),
        params: MutableMap<String, String> = HashMap(),
        data: MutableMap<String, String>? = null
    ): HttpURLConnection = withContext(Dispatchers.IO) {
        //Builds url from url and params
        fun buildUrl(): String {
            val builder = StringBuilder()
            builder.append(url)
            if (params.isNotEmpty()) {
                builder.append('?')
                builder.append(
                    params.toList().joinToString("&") {
                        "${
                            URLEncoder.encode(it.first, "UTF-8")
                        }=${URLEncoder.encode(it.second.toLowerCase(), "UTF-8")}"
                    })
            }
            return builder.toString()
        }
        println(buildUrl())
        val con = URL(buildUrl()).openConnection() as HttpURLConnection
        con.requestMethod = method.toString()
        additionalHeaders.forEach { con.addRequestProperty(it.key, it.value) }
        headers.forEach { con.addRequestProperty(it.key, it.value) }

        //Attaches body if method is POST
        if (method == HttpMethod.POST) {
            con.doOutput = true
            if (data != null)
                con.outputStream.use { pair ->
                    pair.write(data.toList().joinToString("&") {
                        "${URLEncoder.encode(it.first, "UTF-8")}=${URLEncoder.encode(it.second, "UTF-8")}"
                    }.toByteArray())
                }
        }
        try {
            con.connect()
        } catch (e: Exception) {
            e.printStackTrace()
            login(this@PixivApi.username ?: "", this@PixivApi.password ?: "")
            con.connect()
        }
        con
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend inline fun <reified T> getResponse(
        method: HttpMethod, url: String, headers: MutableMap<String, String> = HashMap(),
        params: MutableMap<String, String> = HashMap(),
        data: MutableMap<String, String>? = null
    ): T = withContext(Dispatchers.IO) {
        mapper.readValue(getHttpUrlConnection(method, url, headers, params, data).readAsString().apply { println(this) }, T::class.java)
    }

    private fun map(f: MutableMap<String, String>.() -> Unit): MutableMap<String, String> {
        val map = HashMap<String, String>()
        f(map)
        return map
    }


    @Suppress("SENSELESS_COMPARISON")
    private fun Any.toMapByJackson(): MutableMap<String, String> {
        return mapper.convertValue(this, object : TypeReference<MutableMap<String, String>>() {})
            .filter { it.value != null } as MutableMap<String, String>
    }
}