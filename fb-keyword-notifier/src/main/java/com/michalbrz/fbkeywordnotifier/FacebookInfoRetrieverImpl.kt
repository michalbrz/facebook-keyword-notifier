package com.michalbrz.fbkeywordnotifier


class FacebookInfoRetrieverImpl(val facebookApiAdatper: FacebookApiAdapter) : FacebookInfoRetriever {

    override fun getFanpagesInfo(fanpagesId: List<String>, fanpagesProcessor: FanpagesProcessor) {

        facebookApiAdatper.getJsonForPagesWithId(fanpagesId) {json ->
            println(json)
        }
    }
}