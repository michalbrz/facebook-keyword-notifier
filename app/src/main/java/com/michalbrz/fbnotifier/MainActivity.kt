package com.michalbrz.fbnotifier

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.simpleName

    private val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        centralTextView.text = "DUPA"
        registerCallbacks()
        if (AccessToken.getCurrentAccessToken() != null) {
//            fbLoginButton.visibility = View.GONE
        }

        cardView
        veryRandomButton.setOnClickListener { callFacebookGraph() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun registerCallbacks() {
        fbLoginButton.registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    private fun toast(message: String) {
                        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onSuccess(result: LoginResult?) {
                        toast("Successfully logged with Facebook")
                        Log.i(TAG, "SUCCESS")
                    }

                    override fun onError(error: FacebookException?) {
                        toast("Logging failed")
                        Log.e(TAG, "Logging failed: $error")
                    }

                    override fun onCancel() {
                        toast("Logging cancelled")
                        Log.e(TAG, "CANCEL")
                    }

                })
    }

    private fun callFacebookGraph() {
        centralTextView.text = Profile.getCurrentProfile().firstName
//        Log.e(TAG, AccessToken.getCurrentAccessToken().toString())
        val graphRequest = GraphRequest(AccessToken.getCurrentAccessToken(), "/me")
        graphRequest.setCallback { graphResponse ->
            centralTextView.append(graphResponse.rawResponse)
            centralTextView.append(graphResponse.toString())
        }
        graphRequest.executeAsync()

    }

    private fun callSomething() {
        Log.e(TAG, "calling facebook")
        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS)

        val graphRequest = GraphRequest()
        graphRequest.graphPath = "/me"
        val executeAndWait = graphRequest.executeAndWait()
        centralTextView.text = executeAndWait.rawResponse

        Log.e(TAG, "called facebook")
    }
}
