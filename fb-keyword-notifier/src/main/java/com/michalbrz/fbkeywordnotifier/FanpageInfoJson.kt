package com.michalbrz.fbkeywordnotifier

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.michalbrz.fbkeywordnotifier.model.FanpageInfo

data class FanpageInfoJson(val id: String, val name: String, val cover:Cover, val fan_count: Int) {

    fun toFanpageInfo() : FanpageInfo = FanpageInfo(name, cover.source)
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Cover(val source: String)