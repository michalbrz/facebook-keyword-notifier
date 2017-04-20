package com.michalbrz.fbnotifier

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.michalbrz.fbkeywordnotifier.FacebookInfoRetrieverImpl
import com.michalbrz.fbkeywordnotifier.model.FanpageInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityView {

    val TAG = this.javaClass.simpleName

    private val callbackManager = CallbackManager.Factory.create()

    private val fanpagesAdapter: FanpagesAdapter = FanpagesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerCallbacks()
        if (AccessToken.getCurrentAccessToken() != null) {
//            fbLoginButton.visibility = View.GONE
        }
        MainActivityPresenter(this, FacebookInfoRetrieverImpl(FacebookApiAdapterImpl()), DummyFanpagesStorage())

        setUpFanpagesList()

        veryRandomButton.setOnClickListener { startActivity(Intent(this, PostsListActivity::class.java)) }
    }

    private fun setUpFanpagesList() {
        fanpagesRecyclerView.setHasFixedSize(true)
        fanpagesRecyclerView.layoutManager = LinearLayoutManager(this)
        fanpagesRecyclerView.adapter = fanpagesAdapter
    }

    override fun displayFanpages(fanpagesInfo: List<FanpageInfo>) {
        fanpagesAdapter.fanpages = fanpagesInfo
        fanpagesAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun registerCallbacks() {
        fbLoginButton.registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {
                        this@MainActivity.toastWithMessage("Successfully logged with Facebook")
                        Log.i(TAG, "SUCCESS")
                    }

                    override fun onError(error: FacebookException?) {
                        this@MainActivity.toastWithMessage("Logging failed")
                        Log.e(TAG, "Logging failed: $error")
                    }

                    override fun onCancel() {
                        this@MainActivity.toastWithMessage("Logging cancelled")
                        Log.e(TAG, "CANCEL")
                    }
                })
    }
}

