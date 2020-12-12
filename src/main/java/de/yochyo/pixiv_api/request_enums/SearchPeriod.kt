package de.yochyo.pixiv_api.request_enums

enum class SearchPeriod {
    ALL {
        override fun toString(): String {
            return "all"
        }
    },
    DAY {
        override fun toString(): String {
            return "day"
        }
    },
    WEEK {
        override fun toString(): String {
            return "week"
        }
    },
    MONTH {
        override fun toString(): String {
            return "month"
        }
    };
}