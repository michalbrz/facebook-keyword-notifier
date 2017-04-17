package com.michalbrz.fbnotifier

import com.michalbrz.fbkeywordnotifier.model.Fanpage
import com.michalbrz.fbkeywordnotifier.model.FanpageInfo
import com.michalbrz.fbkeywordnotifier.model.Post

fun sampleFanpage(): Fanpage {
    return Fanpage(
            FanpageInfo("FanpageName", "url"),
            listOf(Post(0, "post content", "url", "postUrl"))
    )
}

fun sampleFanpageInfo() : FanpageInfo = FanpageInfo("FanpageName", "url")
