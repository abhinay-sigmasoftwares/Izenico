package project.sigmasoftwares.izenico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startSplashTimer()
    }


    private fun startSplashTimer() {
        Handler().postDelayed({
            gotoNextActivity()
        }, SPLASH_TIME_OUT)


    }

    private fun gotoNextActivity() {
        startActivity(Intent(this, WebViewActivity::class.java))
        finish()

    }

}
