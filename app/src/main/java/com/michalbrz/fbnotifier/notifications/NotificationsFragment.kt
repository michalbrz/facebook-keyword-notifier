package com.michalbrz.fbnotifier.notifications

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration
import com.firebase.jobdispatcher.Constraint
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.Trigger
import com.michalbrz.fbkeywordnotifier.storage.DummyKeywordStorage
import com.michalbrz.fbnotifier.KeywordOccurrenceCheckService
import com.michalbrz.fbnotifier.R
import kotlinx.android.synthetic.main.fragment_notifications.*


class NotificationsFragment : Fragment(), NotificationsView {

    val presenter: NotificationsPresenter = NotificationsPresenter(this, DummyKeywordStorage())
    val keywordsAdapter = KeywordsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity.title = getString(R.string.notifications_fragment)
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        fragmentManager.beginTransaction().replace(R.id.prefsHolder, NotificationsPreferences()).commit()

        keywordsRecyclerView.addItemDecoration(
                SpacingItemDecoration(
                        resources.getDimensionPixelOffset(R.dimen.keyword_horizontal_margin),
                        resources.getDimensionPixelOffset(R.dimen.keyword_vertical_margin)
                ))
        keywordsRecyclerView.layoutManager = createChipsLayoutManager()
        keywordsRecyclerView.adapter = keywordsAdapter

        presenter.init()

//        val dispatcher: FirebaseJobDispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))
//        schedulePostPollingJob(dispatcher)
//
//        stopJobButton.setOnClickListener { dispatcher.cancelAll() }
    }

    private fun createChipsLayoutManager(): ChipsLayoutManager {
        val chipsLayoutManager = ChipsLayoutManager.newBuilder(context)
                //set vertical gravity for all items in a row. Default = Gravity.CENTER_VERTICAL
                .setChildGravity(Gravity.NO_GRAVITY)
                //whether RecyclerView can scroll. TRUE by default
                .setScrollingEnabled(true)
                //set gravity resolver where you can determine gravity for item in position.
                //This method have priority over previous one
                .setGravityResolver { Gravity.NO_GRAVITY }
                //a layoutOrientation of layout manager, could be VERTICAL OR HORIZONTAL. HORIZONTAL by default
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                // row strategy for views in completed row, could be STRATEGY_DEFAULT, STRATEGY_FILL_VIEW,
                //STRATEGY_FILL_SPACE or STRATEGY_CENTER
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .build()
        return chipsLayoutManager
    }

    override fun showKeywords(keywords: List<String>) {
        keywordsAdapter.keywords.addAll(keywords)
        keywordsAdapter.notifyDataSetChanged()

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

