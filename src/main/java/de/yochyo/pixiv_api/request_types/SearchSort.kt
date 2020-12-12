package de.yochyo.pixiv_api.request_types

enum class SearchSort {
    DATE{
        override fun toString(): String {
            return "date"
        }
    }
}