package com.michalbrz.fbnotifier

import com.michalbrz.fbnotifier.model.Fanpage

interface MainActivityView {

    fun displayFanpages(fanpages: List<Fanpage>)
}