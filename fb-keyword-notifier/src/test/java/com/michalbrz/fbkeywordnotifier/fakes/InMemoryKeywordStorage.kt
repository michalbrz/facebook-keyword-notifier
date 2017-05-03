package com.michalbrz.fbkeywordnotifier.fakes

import com.michalbrz.fbkeywordnotifier.storage.KeywordStorage

class InMemoryKeywordStorage(val initializedKeywords:List<String>) : KeywordStorage {

    constructor(vararg keywords: String) : this(keywords.toList())

    override fun getKeywords(): List<String> = initializedKeywords
}