package com.michalbrz.fbkeywordnotifier

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue


class FacebookInfoRetrieverImpl(val facebookApiAdatper: FacebookApiAdapter) : FacebookInfoRetriever {

    override fun getFanpagesInfo(fanpagesId: List<String>, fanpagesProcessor: FanpagesInfoProcessor) {

        facebookApiAdatper.getJsonForPagesWithId(fanpagesId) {json ->
            Logger.json(json)
            val mapper = jacksonObjectMapper()
            val fanpagesInfoJson = mapper.readValue<Map<String, FanpageInfoJson>>(json).values
            fanpagesProcessor.invoke(fanpagesInfoJson.map { it.toFanpageInfo() })
        }
    }
}