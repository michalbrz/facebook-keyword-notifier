package com.michalbrz.keywordnotifier

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class FbKeywordNotifierSpek: Spek({
        given("I am subscribed to fanpage x with keyword y") {

            on("it publishes a post containing keyword y and state is synchronized") {
                    it("should notify ") {
                        println("Shiat")
                    }
            }

            on("it publishes a post NOT containing keyword y and state is synchronized") {
                it("should not notify") {
                        println("Shiat2")

                }
            }


        }


})

