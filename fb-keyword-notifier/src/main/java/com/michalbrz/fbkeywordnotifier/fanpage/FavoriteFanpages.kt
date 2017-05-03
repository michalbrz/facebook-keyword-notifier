package com.michalbrz.fbkeywordnotifier.fanpage

import com.michalbrz.fbkeywordnotifier.FanpagesInfoProcessor
import com.michalbrz.fbkeywordnotifier.FanpagesProcessor

interface FavoriteFanpages {

    fun getAll(fanpagesProcessor: FanpagesProcessor)

    fun getFanpagesInfo(fanpagesInfoProcessor: FanpagesInfoProcessor)
}