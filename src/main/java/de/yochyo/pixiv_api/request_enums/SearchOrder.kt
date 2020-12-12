package de.yochyo.pixiv_api.request_enums

enum class SearchOrder {
    DESC{
        override fun toString(): String {
            return "date_desc"
        }
    },
    ASC{
        override fun toString(): String {
            return "date_asc"
        }
    },
    POPULAR_DESC{
        override fun toString(): String {
            return "popular_desc"
        }
    }
}