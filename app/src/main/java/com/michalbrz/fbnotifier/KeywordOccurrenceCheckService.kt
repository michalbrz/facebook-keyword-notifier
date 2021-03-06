package com.michalbrz.fbnotifier

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.michalbrz.fbkeywordnotifier.fanpage.DummyFanpagesStorage
import com.michalbrz.fbkeywordnotifier.keywordoccurrence.FacebookKeywordOccurrenceFactory
import com.michalbrz.fbkeywordnotifier.keywordoccurrence.NotificationMessages
import com.michalbrz.fbkeywordnotifier.logger.Logger
import com.michalbrz.fbkeywordnotifier.storage.DummyKeywordStorage
import com.michalbrz.fbnotifier.mainactivity.MainActivity

class KeywordOccurrenceCheckService : JobService() {

    override fun onStopJob(job: JobParameters?): Boolean = false

    override fun onStartJob(job: JobParameters?): Boolean {
        Logger.info("Facebook posts retrieval job started")
        val simpleStringPosts = FacebookKeywordOccurrenceFactory.forSimpleStringPosts(
                FacebookApiAdapterImpl(),
                DummyFanpagesStorage(),
                DummyKeywordStorage(),
                ShownNotificationsStorageImpl(applicationContext))
        simpleStringPosts.ifKeywordOccurredInPosts { notificationMessages ->
            showNotification(notificationMessages)
        }
        return false
    }

    fun showNotification(notificationMessages: NotificationMessages) {
        val notificationIntent = Intent(this, MainActivity::class.java)
        //TODO: add data to display PostsLists fragment
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("New post with keyword appeared!")
                .setContentText(notificationMessages.first())
                .setContentIntent(pendingIntent)

        val inboxStyle = NotificationCompat.InboxStyle()
        notificationMessages.forEach { inboxStyle.addLine(it) }
        builder.setStyle(inboxStyle)

        val notificationManager: NotificationManager = getSystemService(JobService.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, builder.build())
    }
}