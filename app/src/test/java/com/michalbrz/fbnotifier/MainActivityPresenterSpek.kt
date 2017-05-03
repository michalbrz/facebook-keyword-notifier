package com.michalbrz.fbnotifier

import com.michalbrz.fbkeywordnotifier.fanpage.DummyFanpagesStorage
import com.michalbrz.fbkeywordnotifier.FacebookInfoRetriever
import com.michalbrz.fbkeywordnotifier.FanpagesInfoProcessor
import com.michalbrz.fbkeywordnotifier.fanpage.FanpageInfo
import com.michalbrz.fbnotifier.mainactivity.MainActivityPresenter
import com.michalbrz.fbnotifier.mainactivity.MainActivityView
import com.nhaarman.mockito_kotlin.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mockito.doAnswer

@RunWith(JUnitPlatform::class)
class FbKeywordNotifierSpek: Spek({
    given("Presenter is created") {

        val mainActivityView = mock<MainActivityView>()
        val facebookInfoRetriever = mock<FacebookInfoRetriever>()
        val fanpagesStorage = DummyFanpagesStorage()

        val fanpagesInfo = listOf(sampleFanpageInfo(), sampleFanpageInfo())
        val savedFanpagesId = fanpagesStorage.getFavoriteFanpagesId()

        callFanpagesReadyCallback(facebookInfoRetriever, fanpagesInfo, savedFanpagesId)
        val presenter = MainActivityPresenter(mainActivityView, facebookInfoRetriever, fanpagesStorage)

        it("should get info about saved fanpages") {
            verify(facebookInfoRetriever).getFanpagesInfo(eq(savedFanpagesId), any())
        }

        context("fanpages info is downloaded") {
            it("should display fanpages info") {
                verify(mainActivityView).displayFanpages(fanpagesInfo)
            }
        }

        context("user clicks on a fanpage") {
            it("should move to posts view") {

            }
        }


    }


})

private fun callFanpagesReadyCallback(facebookInfoRetriever: FacebookInfoRetriever, fanpagesInfo: List<FanpageInfo>,
                                      savedFanpagesId: List<String>) {
    doAnswer { invocation ->
        val fanpagesProcessor: FanpagesInfoProcessor = invocation.arguments[1] as FanpagesInfoProcessor
        fanpagesProcessor.invoke(fanpagesInfo)
        ""
    }.whenever(facebookInfoRetriever).getFanpagesInfo(eq(savedFanpagesId), any())
}

