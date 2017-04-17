package com.michalbrz.fbkeywordnotifier

import com.michalbrz.fbkeywordnotifier.model.Fanpage

interface FacebookInfoRetriever {

    fun getFanpagesInfo(fanpagesId: List<String>, fanpagesProcessor: (List<Fanpage>) -> Unit)
}

typealias FanpagesProcessor = (List<Fanpage>) -> Unit

