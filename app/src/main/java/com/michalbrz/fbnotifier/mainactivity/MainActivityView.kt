package com.michalbrz.fbnotifier.mainactivity

import com.michalbrz.fbkeywordnotifier.fanpage.FanpageInfo

interface MainActivityView {

    fun displayFanpages(fanpagesInfo: List<FanpageInfo>)
}