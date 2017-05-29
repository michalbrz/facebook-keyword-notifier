package com.michalbrz.fbnotifier

import android.app.Activity
import android.support.v4.app.Fragment
import android.widget.Toast

fun Activity.toastWithMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toastWithMessage(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}