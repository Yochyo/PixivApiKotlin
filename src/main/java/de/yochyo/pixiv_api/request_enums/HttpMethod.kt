package de.yochyo.pixiv_api.request_enums

enum class HttpMethod {
    POST {
        override fun toString(): String = "POST"
    },
    GET {
        override fun toString() = "GET"
    },
    DELETE {
        override fun toString() = "DELETE"
    }
}