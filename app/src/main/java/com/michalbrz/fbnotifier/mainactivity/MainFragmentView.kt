package com.michalbrz.fbnotifier.mainactivity

import com.michalbrz.fbkeywordnotifier.fanpage.FanpageInfo

interface MainFragmentView {

    fun displayFanpages(fanpagesInfo: List<FanpageInfo>)
}