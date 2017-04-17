package com.michalbrz.fbnotifier

import android.os.Bundle
import android.util.Log
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.michalbrz.fbkeywordnotifier.FacebookApiAdapter

class FacebookApiAdapterImpl : com.michalbrz.fbkeywordnotifier.FacebookApiAdapter {
    override fun getJsonForPagesWithId(fanpagesId: List<String>, jsonProcessor: (String) -> Unit) {
        Log.d("ids: ", fanpagesId.joinToString(separator = ","))

        val request = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(), "/",
                { graphResponse ->
                    jsonProcessor.invoke(graphResponse.rawResponse)
                })
        val parameters = Bundle()
        parameters.putString("ids", fanpagesId.joinToString(separator = ","))
        parameters.putString("fields", "id,name,about,cover,fan_count")
        request.parameters = parameters
        request.executeAsync()
    }
}