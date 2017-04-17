package com.michalbrz.fbnotifier

import com.michalbrz.fbkeywordnotifier.FacebookInfoRetriever
import com.michalbrz.fbkeywordnotifier.FanpagesProcessor
import com.michalbrz.fbnotifier.DummyFanpagesStorage
import com.michalbrz.fbnotifier.MainActivityPresenter
import com.michalbrz.fbnotifier.MainActivityView
import com.michalbrz.fbkeywordnotifier.model.Fanpage
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
        val facebookInfoRetriever = mock<com.michalbrz.fbkeywordnotifier.FacebookInfoRetriever>()
        val fanpagesStorage = DummyFanpagesStorage()

        val fanpages = listOf(sampleFanpage(), sampleFanpage())
        val savedFanpagesId = fanpagesStorage.getFavoriteFanpagesId()

        callFanpagesReadyCallback(facebookInfoRetriever, fanpages, savedFanpagesId)
        val presenter = MainActivityPresenter(mainActivityView, facebookInfoRetriever, fanpagesStorage)

        it("should get info about saved fanpages") {
            verify(facebookInfoRetriever).getFanpagesInfo(eq(savedFanpagesId), any())
        }

        context("fanpages info is downloaded") {
            it("should display fanpages info") {
                verify(mainActivityView).displayFanpages(fanpages.map { it.fanpageInfo })
            }
        }

        context("user clicks on a fanpage") {
            it("should move to posts view") {

            }
        }


    }


})

private fun callFanpagesReadyCallback(facebookInfoRetriever: com.michalbrz.fbkeywordnotifier.FacebookInfoRetriever, fanpages: List<Fanpage>, savedFanpagesId: List<String>) {
    doAnswer { invocation ->
        val fanpagesProcessor: FanpagesProcessor = invocation.arguments[1] as FanpagesProcessor
        fanpagesProcessor.invoke(fanpages)
        ""
    }.whenever(facebookInfoRetriever).getFanpagesInfo(eq(savedFanpagesId), any())
}

