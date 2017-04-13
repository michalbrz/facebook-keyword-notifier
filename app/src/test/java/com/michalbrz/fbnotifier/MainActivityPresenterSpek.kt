package com.michalbrz.fbnotifier

import com.nhaarman.mockito_kotlin.verify
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(JUnitPlatform::class)
class FbKeywordNotifierSpek: Spek({
    given("Presenter is created") {

        val mainActivityView = mock(MainActivityView::class.java)
        val facebookInfoRetriever = mock(FacebookInfoRetriever::class.java)
        val fanpagesStorage = mock(FanpagesStorage::class.java)

        val presenter = MainActivityPresenter(mainActivityView, facebookInfoRetriever, fanpagesStorage)
        it("should get info about saved fanpages") {
            verify(fanpagesStorage)
        }

        on("fanpages info is downloaded") {
            it("should display fanpages info") {

            }
        }

        on("user clicks on a fanpage") {
            it("should move to posts view") {

            }
        }


    }


})

