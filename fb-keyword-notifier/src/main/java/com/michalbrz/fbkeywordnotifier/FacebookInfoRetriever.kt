package com.michalbrz.fbkeywordnotifier

import com.michalbrz.fbkeywordnotifier.model.Fanpage
import com.michalbrz.fbkeywordnotifier.model.FanpageInfo

interface FacebookInfoRetriever {

    fun getFanpagesInfo(fanpagesId: List<String>, fanpagesInfoProcessor: FanpagesInfoProcessor)

    fun getPostsForFanpages(fanpagesId: List<String>, fanpagesProcessor: FanpagesProcessor)
}

typealias FanpagesInfoProcessor = (List<FanpageInfo>) -> Unit

typealias FanpagesProcessor = (List<Fanpage>) -> Unit

