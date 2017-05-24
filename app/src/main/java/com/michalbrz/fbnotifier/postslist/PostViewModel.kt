package com.michalbrz.fbnotifier.postslist

data class PostViewModel(val dateAndTime: String,
                         val text: String,
                         val url: String,
                         val imageUrl: String,
                         val fanpageName: String,
                         val fanpagePictureUrl: String,
                         val hasKeyword: Boolean)