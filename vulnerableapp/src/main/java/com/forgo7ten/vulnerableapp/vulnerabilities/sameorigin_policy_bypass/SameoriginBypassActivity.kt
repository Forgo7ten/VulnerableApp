package com.forgo7ten.vulnerableapp.vulnerabilities.sameorigin_policy_bypass

import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.JsResult
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.forgo7ten.vulnerableapp.databinding.ActivitySameoriginBypassBinding


/**
 * @ClassName SameoriginBypassActivity
 * @Description // 复现【WebView跨域】漏洞的Activity
 * @Author Forgo7ten
 **/
class SameoriginBypassActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "SameoriginBypass"
    }

    private lateinit var binding: ActivitySameoriginBypassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivitySameoriginBypassBinding.inflate(layoutInflater).apply { setContentView(root) }
        initPrivateInfo("flag", "flag{webview_file_sameorigin_policy_bypass}")
        initViews()
        val data = intent.data
        if (data?.scheme == "fvulnerable" && data.getQueryParameter("url") != null) {
            // 尝试加载deeplink中传入的url
            Log.d(TAG, "onCreate: intent.data=${data}")
            if ("appclone" in data.path!!) {
                // 复现 应用克隆 设置的环境
                initAppclone()
                Log.d(TAG, "onCreate: appclone")
            } else if ("contaminatedCookie" in data.path!!) {
                // 复现 污染cookie 设置的环境
                initContaminatedCookie()
                Log.d(TAG, "onCreate: contaminated cookie")
            }
            binding.webview.loadUrl(data.getQueryParameter("url")!!)
        } else {
            binding.webview.loadUrl("https://forgo7ten.github.io")
            // 由于未禁止file域，因此可以加载本地文件
            // binding.webview.loadUrl("file:///data/local/tmp/test1.html")
        }
    }

    /**
     * @Description // 初始化污染cookie的环境
     */
    private fun initContaminatedCookie() {
        binding.webview.settings.apply {
            javaScriptEnabled = true
            allowFileAccess = true
            allowFileAccessFromFileURLs = false
        }
    }

    /**
     * @Description // 初始化应用克隆的环境
     */
    private fun initAppclone() {
        binding.webview.settings.apply {
            javaScriptEnabled = true
            allowFileAccess = true
            allowFileAccessFromFileURLs = true
        }
    }

    /**
     * @Description // 初始化的cookie信息
     * @Param [flag] 添加的信息
     * @return void
     */
    private fun initPrivateInfo(key: String, value: String) {
        val cookieManager: CookieManager = CookieManager.getInstance()
        cookieManager.setCookie("https://forgo7ten.github.io", "$key=$value")
    }

    private fun initViews() {
        binding.webview.apply {
            // 打印一些默认值
            Log.d(TAG, "API Level: ${android.os.Build.VERSION.SDK_INT}, Default Settings:")
            Log.d(TAG, "javaScriptEnabled=${settings.javaScriptEnabled}")
            Log.d(TAG, "allowFileAccess=${settings.allowFileAccess}")
            Log.d(TAG, "allowFileAccessFromFileURLs=${settings.allowFileAccessFromFileURLs}")

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    // 返回false，加载任意url
                    return false
                }
            }
            webChromeClient = object : android.webkit.WebChromeClient() {
                override fun onConsoleMessage(consoleMessage: android.webkit.ConsoleMessage): Boolean {
                    // 打印console.log
                    Log.d(TAG, "WebView onConsoleMessage: ${consoleMessage.message()}")
                    return true
                }

                override fun onJsAlert(
                    view: WebView?,
                    url: String?,
                    message: String?,
                    result: JsResult?
                ): Boolean {
                    return super.onJsAlert(view, url, message, result)
                }
            }
        }
    }
}