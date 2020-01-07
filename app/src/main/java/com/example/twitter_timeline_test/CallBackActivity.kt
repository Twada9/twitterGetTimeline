package com.example.twitter_timeline_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_call_back.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class CallBackActivity : AppCompatActivity(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_back)

        val uri = intent.data                //認証情報の受け取り
        if (uri != null && uri.toString().startsWith("callback://CallBackActivity")){
            launch {
                val mtoken = async(context = Dispatchers.IO){           //asyncは値を返せる
                    val varif = uri.getQueryParameter("oauth_verifier")
                    val token = MainActivity.mOauth.getOAuthAccessToken(MainActivity.mRequest, varif)
                    return@async token
                }.await()

                textView_token.text = mtoken.token          //ID:textView_tokenのTextViewにアクセストークンを表示
                textView_secret.text = mtoken.tokenSecret   //ID:textView_secretのTextViewにシークレットキーを表示
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
