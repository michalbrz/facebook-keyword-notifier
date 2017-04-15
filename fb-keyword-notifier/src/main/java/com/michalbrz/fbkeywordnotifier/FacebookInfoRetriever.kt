package com.michalbrz.fbkeywordnotifier

import com.michalbrz.fbkeywordnotifier.model.Fanpage

interface FacebookInfoRetriever {

    fun getFanpagesInfo(fanpagesId: List<String>, fanpagesProcessor: (List<Fanpage>) -> Unit)

    fun doWithString(stringFunction: (String) -> Unit)

}

typealias FanpagesProcessor = (List<Fanpage>) -> Unit

