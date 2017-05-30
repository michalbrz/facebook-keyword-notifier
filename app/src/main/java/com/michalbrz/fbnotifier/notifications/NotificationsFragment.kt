package com.michalbrz.fbnotifier.notifications

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.jobdispatcher.Constraint
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.firebase.jobdispatcher.Trigger
import com.michalbrz.fbnotifier.KeywordOccurrenceCheckService
import com.michalbrz.fbnotifier.R
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity.title = getString(R.string.notifications_fragment)
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        val dispatcher: FirebaseJobDispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))
        schedulePostPollingJob(dispatcher)

        stopJobButton.setOnClickListener { dispatcher.cancelAll() }
    }

    private fun schedulePostPollingJob(dispatcher: FirebaseJobDispatcher) {
        val oneHour: Int = 60 * 60
        val oneHourTenMinutes: Int = oneHour + 10 * 60
        val myJob = dispatcher.newJobBuilder()
                .setService(KeywordOccurrenceCheckService::class.java)
                .setTag("my-unique-tag")
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(oneHour, oneHourTenMinutes))
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setReplaceCurrent(true)
                .build()
        dispatcher.mustSchedule(myJob)
    }
}