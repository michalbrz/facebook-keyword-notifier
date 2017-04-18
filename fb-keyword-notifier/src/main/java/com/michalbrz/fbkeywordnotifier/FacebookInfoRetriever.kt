package com.michalbrz.fbkeywordnotifier

import com.michalbrz.fbkeywordnotifier.model.FanpageInfo

interface FacebookInfoRetriever {

    fun getFanpagesInfo(fanpagesId: List<String>, fanpagesProcessor: FanpagesInfoProcessor)
}

typealias FanpagesInfoProcessor = (List<FanpageInfo>) -> Unit

