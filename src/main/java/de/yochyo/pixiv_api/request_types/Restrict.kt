package de.yochyo.pixiv_api.request_types

enum class Restrict {
    PUBLIC{
        override fun toString(): String {
            return "public"
        }
    }, PRIVATE{
        override fun toString(): String {
            return "private"
        }
    }
}