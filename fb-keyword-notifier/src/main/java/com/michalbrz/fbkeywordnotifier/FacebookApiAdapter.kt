package com.michalbrz.fbkeywordnotifier

interface FacebookApiAdapter {

    fun getJsonForPagesWithId(fanpagesId: List<String>, jsonProcessor: (String) -> Unit)
}