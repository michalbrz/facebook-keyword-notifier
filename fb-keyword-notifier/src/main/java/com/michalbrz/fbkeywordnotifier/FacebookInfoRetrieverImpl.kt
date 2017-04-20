package com.michalbrz.fbkeywordnotifier

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.michalbrz.fbkeywordnotifier.model.FanpageJson


class FacebookInfoRetrieverImpl(val facebookApiAdatper: FacebookApiAdapter) : FacebookInfoRetriever {

    override fun getPostsForFanpages(fanpagesId: List<String>, fanpagesProcessor: FanpagesProcessor) {
        facebookApiAdatper.getJsonForPostsWithFanpagesId(fanpagesId) { json ->
            if (json == null ) {
                Logger.error("Service temporarily unavailable")
            } else {
                Logger.json(json)
                val mapper = jacksonObjectMapper()
                val fanpagesInfoJson = mapper.readValue<Map<String, FanpageJson>>(json).values
                fanpagesProcessor.invoke(fanpagesInfoJson.map { it.toFanpage() })
            }

//            val fanpagesInfoJsonList = json.parseJsonToListOf<FanpageJson>()
//            fanpagesProcessor.invoke(fanpagesInfoJsonList.map { it.toFanpage() })
        }

    }

    override fun getFanpagesInfo(fanpagesId: List<String>, fanpagesInfoProcessor: FanpagesInfoProcessor) {
        facebookApiAdatper.getJsonForPagesWithId(fanpagesId) { json ->
//            val fanpagesInfoJsonList = json.parseJsonToListOf<FanpageInfoJson>()
//            fanpagesInfoProcessor.invoke(fanpagesInfoJsonList.map { it.toFanpageInfo() })
            if (json == null ) {
                Logger.error("Service temporarily unavailable")
            } else {
                Logger.json(json)
                val mapper = jacksonObjectMapper()
                //no idea why, but below line can't be extracted to reified method
                val fanpagesInfoJson = mapper.readValue<Map<String, FanpageInfoJson>>(json).values
                fanpagesInfoProcessor.invoke(fanpagesInfoJson.map { it.toFanpageInfo() })
            }
        }
    }

    inline private fun <reified T> String?.parseJsonToListOf() : Collection<T> {
        if (this == null ) {
            Logger.error("Service temporarily unavailable")
            return emptyList()
        } else {
            Logger.json(this)
            val mapper = jacksonObjectMapper()
            Logger.info(T::class.java.simpleName)
            val values = mapper.readValue<Map<String, T>>(this).values

            Logger.info(T::class.java.simpleName)
            val first = values.first() as Any
            Logger.info(first.javaClass.simpleName.toString())
//            Logger.info(values.first()::class.simpleName ?: "")
            return values
        }
    }

    inline private fun <T> String?.parseJsonToListOf(clazz: T) : Collection<T> {
        if (this == null ) {
            Logger.error("Service temporarily unavailable")
            return emptyList()
        } else {
            Logger.json(this)
            val mapper = jacksonObjectMapper()
            val values = mapper.readValue<Map<String, T>>(this).values
            Logger.info(values.toString())
//            Logger.info(values.first()::class.simpleName ?: "")
            return values
        }
    }
}