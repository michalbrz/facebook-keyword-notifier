package com.michalbrz.fbnotifier

import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.michalbrz.fbkeywordnotifier.facebookadapter.FacebookApiAdapter

class FacebookApiAdapterImpl : FacebookApiAdapter {
    override fun getJsonForPostsWithFanpagesId(fanpagesId: List<String>, jsonProcessor: (String?) -> Unit) {
        val request = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(), "/",
                { graphResponse ->
                    println(graphResponse)
                    println(graphResponse.rawResponse)
                    jsonProcessor.invoke(graphResponse.rawResponse)
                })
        val parameters = Bundle()
        parameters.putString("ids", fanpagesId.joinToString(separator = ","))
        parameters.putString("fields", "posts.limit(10){message,picture,created_time,id,permalink_url},picture{url}," +
                "name")
        request.parameters = parameters
        request.executeAsync()
    }

    override fun getJsonForPagesWithId(fanpagesId: List<String>, jsonProcessor: (String?) -> Unit) {
        val request = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(), "/",
                { graphResponse ->
                    jsonProcessor.invoke(graphResponse.rawResponse)
                })
        val parameters = Bundle()
        parameters.putString("ids", fanpagesId.joinToString(separator = ","))
        parameters.putString("fields", "name,fan_count,picture{url}")
        request.parameters = parameters
        request.executeAsync()
    }
}