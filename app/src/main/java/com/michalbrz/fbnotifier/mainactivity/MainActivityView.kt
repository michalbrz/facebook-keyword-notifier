package com.michalbrz.fbnotifier.mainactivity

import com.michalbrz.fbkeywordnotifier.model.FanpageInfo

interface MainActivityView {

    fun displayFanpages(fanpagesInfo: List<FanpageInfo>)
}