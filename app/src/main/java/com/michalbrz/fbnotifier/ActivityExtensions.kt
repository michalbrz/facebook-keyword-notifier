package com.michalbrz.fbnotifier

import android.app.Activity
import android.widget.Toast

fun Activity.toastWithMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}