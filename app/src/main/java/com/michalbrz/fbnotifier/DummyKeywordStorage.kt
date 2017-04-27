package com.michalbrz.fbnotifier

class DummyKeywordStorage : KeywordStorage {

    override fun getKeywords(): List<String> {
        return listOf("Puerto Rico",
                "Indonesia",
                "Indonezj",
                "Malaysia",
                "Malezj",
                "Kuala Lumpur",
                "Bali",
                "Sinagapore",
                "Costa Rica",
                "Singapur").map { it.toLowerCase() }
    }
}