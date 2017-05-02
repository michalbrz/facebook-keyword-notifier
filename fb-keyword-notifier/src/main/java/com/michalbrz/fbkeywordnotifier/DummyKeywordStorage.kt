package com.michalbrz.fbkeywordnotifier

class DummyKeywordStorage : KeywordStorage {

    override fun getKeywords(): List<String> {
        return listOf("Puerto Rico",
                "Indonesia",
                "Indonezj",
                "Malaysia",
                "Malezj",
                "Kuala Lumpur",
                "Bali",
                "Singap").map { it.toLowerCase() }
    }
}