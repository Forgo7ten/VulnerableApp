package com.forgo7ten.attackapp.view

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.forgo7ten.attackapp.databinding.ActivityDbBinding

/**
 * @ClassName DbActivity
 * @Description // 该Activity负责对vulnerableapp的Provider的数据库进行增删改查；是【ContentProviderURI授权不当漏洞】复现的一部分
 * @Author Forgo7ten
 **/
class DbActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDbBinding
    private val TAG = "DbActivity"
    lateinit var uri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDbBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        // 设定了intent的data必须传递过来uri，否则会报错
        uri = intent.data!!
        binding.btnAdd.setOnClickListener {
            val name = binding.etName.text.toString()
            val iphone = binding.etIphone.text.toString()
            if (name.isEmpty() || iphone.isEmpty()) {
                binding.tvShow.text = "请输入name、iphone"
                return@setOnClickListener
            }
            addUser(name, iphone)
        }
        binding.btnDelete.setOnClickListener {
            val deleteId: String = binding.etId.text.toString()
            if (deleteId.isEmpty()) {
                binding.tvShow.text = "请输入id"
                return@setOnClickListener
            }
            deleteUser(deleteId.toLong())
        }
        binding.btnUpdate.setOnClickListener {
            val updateId: String = binding.etId.text.toString()
            val name = binding.etName.text.toString()
            val iphone = binding.etIphone.text.toString()
            if (updateId.isEmpty() || name.isEmpty() || iphone.isEmpty()) {
                binding.tvShow.text = "请输入id、name、iphone"
                return@setOnClickListener
            }
            updateUser(updateId.toLong(), name, iphone)
        }
        binding.btnQuery.setOnClickListener {
            queryUser()
        }

    }

    private fun updateUser(updateId: Long, name: String, iphone: String) {
        val updateUri = ContentUris.withAppendedId(uri, updateId)
        val values = ContentValues().apply {
            put("name", name)
            put("iphone", iphone)
        }
        try {
            contentResolver.update(updateUri, values, null, null)
            queryUser()
        } catch (e: SecurityException) {
            e.printStackTrace()
            binding.tvShow.text = "无权限"
        }
    }


    private fun deleteUser(deleteId: Long) {
        val deleteUri = ContentUris.withAppendedId(uri, deleteId)
        Log.d(TAG, "deleteUser: $deleteUri")
        try {
            contentResolver.delete(deleteUri, null, null)
            queryUser()
        } catch (e: SecurityException) {
            e.printStackTrace()
            binding.tvShow.text = "无权限"
        }
    }

    private fun addUser(name: String, iphone: String) {
        val values = ContentValues().apply {
            put("name", name)
            put("iphone", iphone)
        }
        try {
            contentResolver.insert(uri, values)
            queryUser()
        } catch (e: SecurityException) {
            e.printStackTrace()
            binding.tvShow.text = "无权限"
        }
    }

    @SuppressLint("Range")
    private fun queryUser() {
        try {
            val cursor = contentResolver.query(uri, null, null, null, null)
            val text = StringBuilder().apply {
                cursor?.apply {
                    while (moveToNext()) {
                        val id = getInt(getColumnIndex("id"))
                        val name = getString(getColumnIndex("name"))
                        val iphone = getString(getColumnIndex("iphone"))
                        append("id: $id, name: $name, iphone: $iphone\n")
                    }
                }
            }.toString()
            binding.tvShow.text = text
        } catch (e: SecurityException) {
            e.printStackTrace()
            binding.tvShow.text = "无权限"
        }
    }


}