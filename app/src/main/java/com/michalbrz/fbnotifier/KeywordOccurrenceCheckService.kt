package com.michalbrz.fbnotifier

import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.michalbrz.fbkeywordnotifier.FacebookInfoRetrieverImpl
import com.michalbrz.fbkeywordnotifier.Logger

class KeywordOccurrenceCheckService : JobService() {

    override fun onStopJob(job: JobParameters?): Boolean = false

    override fun onStartJob(job: JobParameters?): Boolean {
        Logger.error("MAGIC")
        val facebookInfoRetrieverImpl = FacebookInfoRetrieverImpl(FacebookApiAdapterImpl())
        val keywordChecker = FacebookKeywordChecker(facebookInfoRetrieverImpl,
                                                    DummyFanpagesStorage(),
                                                    DummyKeywordStorage())
        keywordChecker.ifOccured()
        return false
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

