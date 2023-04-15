package com.forgo7ten.attackapp.view

import android.annotation.SuppressLint
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.forgo7ten.attackapp.databinding.ActivityDbBinding

/**
 * @ClassName DbActivity
 * @Description // 该Activity负责对vulnerableapp的Provider的数据库进行增删改查
 * @Author Forgo7ten
 **/
class DbActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDbBinding
    lateinit var authorities: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDbBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        authorities = intent.getStringExtra("authorities").toString()
        binding.btnAdd.setOnClickListener {
            val name = binding.etName.text.toString()
            val iphone = binding.etIphone.text.toString()
            addUser(name, iphone)
        }
        binding.btnDelete.setOnClickListener {
            val deleteId: Int = binding.etId.text.toString().toInt()
            deleteUser(deleteId)
        }
        binding.btnUpdate.setOnClickListener {
            val updateId: Int = binding.etId.text.toString().toInt()
            val name = binding.etName.text.toString()
            val iphone = binding.etIphone.text.toString()
            updateUser(updateId, name, iphone)
        }
        binding.btnQuery.setOnClickListener {
            queryUser()
        }

    }

    private fun updateUser(updateId: Int, name: String, iphone: String) {
        val uri = Uri.parse("content://$authorities/user/$updateId")
        val values = ContentValues().apply {
            put("name", name)
            put("iphone", iphone)
        }
        contentResolver.update(uri, values, null, null)
        queryUser()
    }


    private fun deleteUser(deleteId: Int) {
        val uri = Uri.parse("content://$authorities/user/$deleteId")
        contentResolver.delete(uri, null, null)
        queryUser()
    }

    private fun addUser(name: String, iphone: String) {
        val uri = Uri.parse("content://$authorities/user")
        val values = ContentValues().apply {
            put("name", name)
            put("iphone", iphone)
        }
        contentResolver.insert(uri, values)
        queryUser()
    }

    @SuppressLint("Range")
    private fun queryUser() {
        val uri = Uri.parse("content://$authorities/user")
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
    }


}