package com.michalbrz.fbnotifier

class DummyKeywordStorage : KeywordStorage {

    override fun getKeywords(): List<String> {
        return listOf("Puerto Rico",
                "Indonesia",
                "Indonezj",
                "Hong-Kong",
                "Hongkong",
                "Malaysia",
                "Malezj",
                "Kuala Lumpur",
                "Bali",
                "Sinagapore",
                "Singapur")
    }
}