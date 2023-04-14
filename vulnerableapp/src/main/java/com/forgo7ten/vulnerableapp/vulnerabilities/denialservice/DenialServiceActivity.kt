package com.forgo7ten.vulnerableapp.vulnerabilities.denialservice

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import com.forgo7ten.vulnerableapp.databinding.ActivityDenialServiceBinding
import com.forgo7ten.vulnerableapp.vulnerabilities.VulnerabilityActivity

/**
 * @ClassName DenialServiceActivity
 * @Description // App通用型拒绝服务漏洞
 * @Author Forgo7ten
 **/
class DenialServiceActivity : VulnerabilityActivity() {
    private val TAG = "DenialService"
    private lateinit var binding: ActivityDenialServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        vulnerable()
        super.onCreate(savedInstanceState)
        binding = ActivityDenialServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        vulnerability?.let {
            binding.titleTv.text = it.title
            val descHtml = it.descHtml
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.descTv.text = Html.fromHtml(descHtml, Html.FROM_HTML_MODE_LEGACY);
            } else {
                binding.descTv.text = Html.fromHtml(descHtml);
            }
        }
    }

    private fun vulnerable() {
        // 随意从intent中取得一个Extra，任何类型和任何key
        val message = intent.getStringExtra("message")
        Log.d(TAG, "intent getString: $message")
    }
}