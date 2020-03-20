package project.sigmasoftwares.izenico

import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    var url: String? = "http://izenico.sigmasoftwares.net/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        var wv: WebView? = null

        wv = findViewById(R.id.webView)
        wv.webViewClient = MyWebClient()
        wv.settings.builtInZoomControls = true
        wv.settings.displayZoomControls = false
        wv.settings.javaScriptEnabled = true
        wv.webChromeClient = WebChromeClient()
        wv.settings.javaScriptCanOpenWindowsAutomatically = true
        wv.setDownloadListener(MyDownloadListener())
        wv.loadUrl(url)
    }

    inner class MyWebClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            Log.d("***", "WebView URL : $url")
            progressBar!!.visibility = View.VISIBLE
            view.loadUrl(url)
            return true

        }

        override fun onPageFinished(view: WebView, url: String) {

            super.onPageFinished(view, url)
            progressBar!!.visibility = View.GONE
        }

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            val builder = AlertDialog.Builder(this@WebViewActivity)
            var message = "SSL Certificate error."
            when (error.primaryError) {
                SslError.SSL_UNTRUSTED -> message = "The certificate authority is not trusted."
                SslError.SSL_EXPIRED -> message = "The certificate has expired."
                SslError.SSL_IDMISMATCH -> message = "The certificate Hostname mismatch."
                SslError.SSL_NOTYETVALID -> message = "The certificate is not yet valid."
            }
            message += " Do you want to continue anyway?"

            builder.setTitle("SSL Certificate Error")
            builder.setMessage(message)
            builder.setPositiveButton(
                "continue"
            ) { dialog, which -> handler.proceed() }
            builder.setNegativeButton(
                "cancel"
            ) { dialog, which -> handler.cancel() }
            val dialog = builder.create()
            dialog.show()
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action === KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }

        }
        return super.onKeyDown(keyCode, event)
    }

    inner class MyDownloadListener : DownloadListener {
        override fun onDownloadStart(p0: String?, p1: String?, p2: String?, p3: String?, p4: Long) {
            if (p0 != null) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(p0)
                startActivity(i)
            }
        }
    }
}
