package com.forgo7ten.vulnerableapp.vulnerabilities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * @ClassName StartActivity
 * @Description // 该Activity负责接收intent中的intent，然后不经校验的启动Activity
 * @Author Forgo7ten
 **/
class StartActivity : AppCompatActivity() {
    private val TAG = "StartActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
/*
            // 这是正常授予其他应用Provider权限的方式
            grantUriPermission(
                "com.forgo7ten.attackapp",
                "content://com.forgo7ten.vulnerableapp.IMPROPERPROVIDER/user".toUri(),
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
*/
            // 直接从intent中获取intent，不经校验的启动Activity
            intent.getParcelableExtra<Intent>("intent")?.let {
                startActivity(it)
            }
        } catch (e: RuntimeException) {
            Log.e(TAG, "intent error ${e.message}")
        }
    }
}