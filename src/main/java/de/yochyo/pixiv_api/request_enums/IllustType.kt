package de.yochyo.pixiv_api.request_enums

enum class IllustType {
    ILLUSTRATION {
        override fun toString(): String {
            return "illustration"
        }
    },
    MANGA {
        override fun toString(): String {
            return "manga"
        }
    },
    UGOIRA {
        override fun toString(): String {
            return "ugoira"
        }
    };

    companion object{
        val all = arrayOf(ILLUSTRATION, MANGA, UGOIRA)
    }
}