package com.michalbrz.fbkeywordnotifier.facebookadapter

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.michalbrz.fbkeywordnotifier.fanpage.FanpageInfo

@JsonIgnoreProperties(ignoreUnknown = true)
data class FanpageInfoJson(val id: String, val name: String, val picture: PictureJson, val fan_count: Int) {

    fun toFanpageInfo() : FanpageInfo = FanpageInfo(name, picture.data.url, fan_count)
}

data class PictureJson(val data: PictureData)

data class PictureData (val url: String)