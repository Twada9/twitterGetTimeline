package com.example.twitter_timeline_test

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_twitter_oauth.*
import kotlinx.coroutines.*
import twitter4j.auth.OAuthAuthorization
import twitter4j.auth.RequestToken
import twitter4j.conf.ConfigurationContext
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    companion object {
        val mOauth = OAuthAuthorization(ConfigurationContext.getInstance())
        lateinit var mRequest: RequestToken
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitter_oauth)

        action_start_oauth.setOnClickListener {
            onClick()
        }
    }

    private fun onClick() {
        launch {
            mOauth.setOAuthConsumer("rEVgd8hhQKpMWZ86D7ukJyZg0", "cHMG2Q9SxZr4VNKlTa8VQyHlHKpxn61CIgHfk43YYoI9r0tjpF")
            mOauth.oAuthAccessToken = null
            var uri: Uri?

            async(context = Dispatchers.IO) {
                mRequest = mOauth.getOAuthRequestToken("callback://CallBackActivity")

                var intent = Intent(Intent.ACTION_VIEW)
                uri = Uri.parse(mRequest.authenticationURL)
                intent.data =uri
                startActivityForResult(intent,0)


            }
        }
    }

    override fun onDestroy() {
        job.cancel()                                 //すべてのコルーチンキャンセル用
        super.onDestroy()
    }
}