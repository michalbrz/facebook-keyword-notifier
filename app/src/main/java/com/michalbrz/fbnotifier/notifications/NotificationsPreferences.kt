package com.michalbrz.fbnotifier.notifications

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.firebase.jobdispatcher.Constraint
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.firebase.jobdispatcher.Trigger
import com.michalbrz.fbkeywordnotifier.logger.Logger
import com.michalbrz.fbnotifier.KeywordOccurrenceCheckService
import com.michalbrz.fbnotifier.R

class NotificationsPreferences : PreferenceFragmentCompat() {

    val dispatcher: FirebaseJobDispatcher by lazy { FirebaseJobDispatcher(GooglePlayDriver(context)) }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener({ sharedPreferences, key ->
            when (key) {
                getString(R.string.pref_notifications_active) ->
                    handleNotificationsActiveChange(sharedPreferences.getBoolean(key, true),
                            { getFrequencyInMinutes(sharedPreferences) })
                getString(R.string.pref_sync_period_in_min) ->
                    handleNotificationsFrequencyChange(getFrequencyInMinutes(sharedPreferences))
            }
        })

    }

    private fun getFrequencyInMinutes(sharedPreferences: SharedPreferences): Int {
        val frequencyPrefsKey = getString(R.string.pref_sync_period_in_min)
        return sharedPreferences.getString(frequencyPrefsKey, "60").toInt()
    }

    private fun handleNotificationsFrequencyChange(frequencyInMinutes: Int) {
        dispatcher.cancelAll()
        schedulePostPollingJob(dispatcher, frequencyInMinutes)
    }

    private fun handleNotificationsActiveChange(active: Boolean, frequencyProvider: () -> Int) {
        if (active) {
            schedulePostPollingJob(dispatcher, frequencyProvider())
        } else {
            Logger.info("Cancelling background jobs")
            dispatcher.cancelAll()
        }
    }

    private fun schedulePostPollingJob(dispatcher: FirebaseJobDispatcher, frequencyInMinutes: Int) {
        Logger.info("Scheduling job with frequency of $frequencyInMinutes minutes")
        val frequency = frequencyInMinutes * 60
        val frequencyErrorMargin = frequency / 10

        val myJob = dispatcher.newJobBuilder()
                .setService(KeywordOccurrenceCheckService::class.java)
                .setTag("fb-keyword-notifier")
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(frequency - frequencyErrorMargin, frequency + frequencyErrorMargin))
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setReplaceCurrent(true)
                .build()
        dispatcher.mustSchedule(myJob)
    }
}
