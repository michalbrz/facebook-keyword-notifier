package com.michalbrz.fbnotifier

import com.michalbrz.fbkeywordnotifier.FacebookInfoRetriever
import com.michalbrz.fbkeywordnotifier.FanpagesInfoProcessor
import com.michalbrz.fbkeywordnotifier.fanpage.DummyFanpagesStorage
import com.michalbrz.fbkeywordnotifier.fanpage.FanpageInfo
import com.michalbrz.fbnotifier.mainactivity.MainFragmentPresenter
import com.michalbrz.fbnotifier.mainactivity.MainFragmentView
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class MainActivityPresenterTest {

    val facebookInfoRetriever: FacebookInfoRetriever = mock()
    val mainFragmentView: MainFragmentView = mock()
    val fanpagesStorage = DummyFanpagesStorage()
    lateinit var mainFragmentPresenter: MainFragmentPresenter

    @Before
    fun setUp() {
        mainFragmentPresenter = MainFragmentPresenter(mainFragmentView, facebookInfoRetriever, fanpagesStorage)
    }

    @Test
    fun shouldRetrieveFanpagesInfo() {
        mainFragmentPresenter.init()

        verify(facebookInfoRetriever).getFanpagesInfo(eq(fanpagesStorage.getFavoriteFanpagesId()), any())
    }

    @Test
    fun shouldDisplayFanpagesInfoWhenAfterItIsRetrieved() {
        val fanpagesInfo = listOf(sampleFanpageInfo(), sampleFanpageInfo())
        callFanpagesReadyCallback(facebookInfoRetriever, fanpagesInfo, fanpagesStorage.getFavoriteFanpagesId())

        mainFragmentPresenter.init()

        verify(mainFragmentView).displayFanpages(fanpagesInfo)
    }


    private fun callFanpagesReadyCallback(facebookInfoRetriever: FacebookInfoRetriever, fanpagesInfo: List<FanpageInfo>,
                                          savedFanpagesId: List<String>) {
        Mockito.doAnswer { invocation ->
            val fanpagesProcessor: FanpagesInfoProcessor = invocation.arguments[1] as FanpagesInfoProcessor
            fanpagesProcessor.invoke(fanpagesInfo)
            ""
        }.whenever(facebookInfoRetriever).getFanpagesInfo(eq(savedFanpagesId), any())
    }

}