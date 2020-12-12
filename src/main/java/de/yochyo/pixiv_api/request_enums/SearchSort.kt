package de.yochyo.pixiv_api.request_enums

enum class SearchSort {
    DATE{
        override fun toString(): String {
            return "date"
        }
    }
}