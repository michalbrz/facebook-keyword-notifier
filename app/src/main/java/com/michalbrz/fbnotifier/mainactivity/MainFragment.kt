package com.michalbrz.fbnotifier.mainactivity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.michalbrz.fbkeywordnotifier.FacebookInfoRetrieverImpl
import com.michalbrz.fbkeywordnotifier.fanpage.DummyFanpagesStorage
import com.michalbrz.fbkeywordnotifier.fanpage.FanpageInfo
import com.michalbrz.fbnotifier.FacebookApiAdapterImpl
import com.michalbrz.fbnotifier.R
import com.michalbrz.fbnotifier.toastWithMessage
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), MainFragmentView {

    val TAG: String = this.javaClass.simpleName

    private val callbackManager = CallbackManager.Factory.create()

    private val fanpagesAdapter = FanpagesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity.title = getString(R.string.main_fragment)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        registerFacebookLoginCallbacks()
        val presenter = MainFragmentPresenter(this, FacebookInfoRetrieverImpl(FacebookApiAdapterImpl()), DummyFanpagesStorage())
        presenter.init()

        setUpFanpagesList()
    }

    private fun setUpFanpagesList() {
        fanpagesRecyclerView.setHasFixedSize(true)
        fanpagesRecyclerView.layoutManager = LinearLayoutManager(context)
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

    private fun registerFacebookLoginCallbacks() {
        fbLoginButton.registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {
                        this@MainFragment.toastWithMessage("Successfully logged with Facebook")
                        Log.i(TAG, "SUCCESS")
                    }

                    override fun onError(error: FacebookException?) {
                        this@MainFragment.toastWithMessage("Logging failed")
                        Log.e(TAG, "Logging failed: $error")
                    }

                    override fun onCancel() {
                        this@MainFragment.toastWithMessage("Logging cancelled")
                        Log.e(TAG, "CANCEL")
                    }
                })
    }
}