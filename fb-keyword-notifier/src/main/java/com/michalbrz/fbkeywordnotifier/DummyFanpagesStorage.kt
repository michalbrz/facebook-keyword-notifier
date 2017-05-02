package com.michalbrz.fbkeywordnotifier

import com.michalbrz.fbkeywordnotifier.FanpagesStorage

class DummyFanpagesStorage : com.michalbrz.fbkeywordnotifier.FanpagesStorage {
    override fun getFavoriteFanpagesId(): List<String> {
        return listOf(
                "796474383697246", //fly4free europe
                "240436482717", //fly4free
                "119810774697739", //mleczne podroze
                "128860613834658" //loter
        )
    }
}