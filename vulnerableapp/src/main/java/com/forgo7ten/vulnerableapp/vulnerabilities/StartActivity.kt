package com.forgo7ten.vulnerableapp.vulnerabilities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {
    private val TAG = "StartActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            // 直接从intent中获取intent，不经校验的启动Activity
            intent.getParcelableExtra<Intent>("intent")?.let {
                startActivity(it)
            }
        } catch (e: RuntimeException) {
            Log.e(TAG, "intent error ${e.message}")
        }
    }
}