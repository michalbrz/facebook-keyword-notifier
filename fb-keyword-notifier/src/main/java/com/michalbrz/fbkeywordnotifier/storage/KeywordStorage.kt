package com.michalbrz.fbkeywordnotifier.storage

interface KeywordStorage {
    fun getKeywords() : List<String>
}