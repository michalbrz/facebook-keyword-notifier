package com.michalbrz.fbkeywordnotifier

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.michalbrz.fbkeywordnotifier.model.FanpageInfo

@JsonIgnoreProperties(ignoreUnknown = true)
data class FanpageInfoJson(val id: String, val name: String, val picture:ProfilePicture, val fan_count: Int) {

    fun toFanpageInfo() : FanpageInfo = FanpageInfo(name, picture.data.url, fan_count)
}

data class ProfilePicture(val data: PictureData)

data class PictureData (val url: String)