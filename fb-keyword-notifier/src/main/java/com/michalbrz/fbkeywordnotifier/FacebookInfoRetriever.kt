package com.michalbrz.fbkeywordnotifier

import com.michalbrz.fbkeywordnotifier.fanpage.Fanpage
import com.michalbrz.fbkeywordnotifier.fanpage.FanpageInfo

interface FacebookInfoRetriever {

    fun getFanpagesInfo(fanpagesId: List<String>, fanpagesInfoProcessor: FanpagesInfoProcessor)

    fun getPostsForFanpages(fanpagesId: List<String>, fanpagesProcessor: FanpagesProcessor)
}

typealias FanpagesInfoProcessor = (List<FanpageInfo>) -> Unit

typealias FanpagesProcessor = (List<Fanpage>) -> Unit

