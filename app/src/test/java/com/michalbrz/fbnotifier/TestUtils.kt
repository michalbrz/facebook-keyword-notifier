package com.michalbrz.fbnotifier

import com.michalbrz.fbnotifier.model.Fanpage
import com.michalbrz.fbnotifier.model.FanpageInfo
import com.michalbrz.fbnotifier.model.Post

fun sampleFanpage(): Fanpage {
    return Fanpage(
            FanpageInfo("FanpageName", "url"),
            listOf(Post(0, "post content", "url", "postUrl"))
    )
}

fun sampleFanpageInfo() : FanpageInfo = FanpageInfo("FanpageName", "url")
