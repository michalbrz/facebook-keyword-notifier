package com.michalbrz.fbkeywordnotifier.facebookadapter

interface FacebookApiAdapter {

    fun getJsonForPagesWithId(fanpagesId: List<String>, jsonProcessor: (String?) -> Unit)

    fun getJsonForPostsWithFanpagesId(fanpagesId: List<String>, jsonProcessor: (String?) -> Unit)
}