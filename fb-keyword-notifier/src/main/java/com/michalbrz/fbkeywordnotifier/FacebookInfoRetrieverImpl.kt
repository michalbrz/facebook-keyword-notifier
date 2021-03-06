package com.michalbrz.fbkeywordnotifier

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.michalbrz.fbkeywordnotifier.facebookadapter.FacebookApiAdapter
import com.michalbrz.fbkeywordnotifier.facebookadapter.FanpageInfoJson
import com.michalbrz.fbkeywordnotifier.logger.Logger
import com.michalbrz.fbkeywordnotifier.facebookadapter.FanpageJson

class FacebookInfoRetrieverImpl(val facebookApiAdapter: FacebookApiAdapter) : FacebookInfoRetriever {

    override fun getPostsForFanpages(fanpagesId: List<String>, fanpagesProcessor: FanpagesProcessor) {
        facebookApiAdapter.getJsonForPostsWithFanpagesId(fanpagesId) { json ->
            val fanpagesList = parseJsonOrEmptyList(json) { json, mapper ->
                val fanpageJsonList = mapper.readValue<Map<String, FanpageJson>>(json).values
                fanpageJsonList.map { it.toFanpage() }
            }
            fanpagesProcessor.invoke(fanpagesList.toList())
        }

    }

    override fun getFanpagesInfo(fanpagesId: List<String>, fanpagesInfoProcessor: FanpagesInfoProcessor) {
        facebookApiAdapter.getJsonForPagesWithId(fanpagesId) { json ->
            val fanpagesInfoList = parseJsonOrEmptyList(json) { json, mapper ->
//                no idea why, but below line can't be extracted to reified method
                val fanpageInfoJsonList = mapper.readValue<Map<String, FanpageInfoJson>>(json).values
                fanpageInfoJsonList.map { it.toFanpageInfo() }
            }
            fanpagesInfoProcessor.invoke(fanpagesInfoList.toList())
        }
    }

    inline private fun <reified T> parseJsonOrEmptyList(json: String?,
                                                        jsonParser: (String, ObjectMapper) -> Collection<T>) : Collection<T> {
        if (json == null ) {
            Logger.error("Service temporarily unavailable")
            return emptyList()
        } else {
            Logger.json(json)
            val mapper = jacksonObjectMapper()
            return jsonParser.invoke(json, mapper)
        }
    }
}