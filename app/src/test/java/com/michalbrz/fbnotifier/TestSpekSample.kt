package com.michalbrz.fbnotifier

import com.michalbrz.fbnotifier.model.Fanpage
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.mock
import org.junit.Test
import org.mockito.Mockito


class someTest {

    open class Sample {
        open fun doWithString(stringFunction: (String) -> Unit) {
            stringFunction.invoke("String")
        }
    }

    @Test
    fun testDoWithString() {
        val sampleMock = mock<Sample>()

        sampleMock.doWithString(::println)


        val captor = argumentCaptor<(String) -> Unit>()
        verify(sampleMock).doWithString(captor.capture())

        captor.firstValue.invoke("some")


        val facebookInfoRetriever = Mockito.mock(FacebookInfoRetriever::class.java)

        facebookInfoRetriever.getFanpagesInfo(listOf(""), {fanpages -> println("DUPA")} )

        val captor2 = argumentCaptor<(List<Fanpage>) -> Unit>()
        verify(facebookInfoRetriever).getFanpagesInfo(any<List<String>>(), captor2.capture())
        captor2.lastValue.invoke(listOf(sampleFanpage()))
    }
}

