package com.michalbrz.fbnotifier

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldContain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class ShownNotificationsStorageImplTest {

    lateinit var shownNotificationsStorage: ShownNotificationsStorage

    @Before
    fun before() {
        shownNotificationsStorage = ShownNotificationsStorageImpl(RuntimeEnvironment.application)
    }

    @Test
    fun shouldReturnEmptySetIfNoValuesWereAdded() {
        shownNotificationsStorage.getAlreadyShownUrls().shouldBeEmpty()
    }

    @Test
    fun shouldReturnValuesThatWereAdded() {
        shownNotificationsStorage.addAlreadyShownUrl("url1")
        shownNotificationsStorage.addAlreadyShownUrl("url2")

        val savedUrls = shownNotificationsStorage.getAlreadyShownUrls()
        savedUrls.size shouldBe 2
        savedUrls shouldContain "url1"
        savedUrls shouldContain "url2"
    }
}