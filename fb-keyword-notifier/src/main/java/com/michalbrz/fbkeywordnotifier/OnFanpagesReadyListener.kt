package com.michalbrz.fbkeywordnotifier

import com.michalbrz.fbkeywordnotifier.model.Fanpage

interface OnFanpagesReadyListener {

    fun onReady(fanpages: List<Fanpage>)

}