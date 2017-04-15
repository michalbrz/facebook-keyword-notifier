package com.michalbrz.fbnotifier

import com.michalbrz.fbkeywordnotifier.model.FanpageInfo

interface MainActivityView {

    fun displayFanpages(fanpagesInfo: List<FanpageInfo>)
}