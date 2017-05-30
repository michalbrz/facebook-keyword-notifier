package com.michalbrz.fbnotifier.notifications

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.michalbrz.fbnotifier.R

class NotificationsPreferences : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }
}
