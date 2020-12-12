package de.yochyo.pixiv_api.request_types

enum class RankingMode {
    DAILY {
        override fun toString(): String {
            return "daily"
        }
    },
    WEEKLY {
        override fun toString(): String {
            return "weekly"
        }
    },
    MONTHLY {
        override fun toString(): String {
            return "monthly"
        }
    },
    ROOKIE {
        override fun toString(): String {
            return "rookie"
        }
    },
    ORIGINAL {
        override fun toString(): String {
            return "original"
        }
    },
    MALE {
        override fun toString(): String {
            return "male"
        }
    },
    FEMALE {
        override fun toString(): String {
            return "female"
        }
    },
    DAILY_R18 {
        override fun toString(): String {
            return "daily_r18"
        }
    },
    WEEKLY_R18 {
        override fun toString(): String {
            return "weekly_r18"
        }
    },
    MALE_R18 {
        override fun toString(): String {
            return "male_r18"
        }
    },
    FEMALE_R18 {
        override fun toString(): String {
            return "female_r18"
        }
    },
    R18G {
        override fun toString(): String {
            return "r18g"
        }
    };
}