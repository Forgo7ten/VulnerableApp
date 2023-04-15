package com.forgo7ten.vulnerableapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * @ClassName MyDbHelper
 * @Description // 用于测试的数据库
 * @Author Forgo7ten
 **/
class MyDbHelper(val context: Context, val name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {
    private val TAG = "MyDbHelper"
    private val createUserSql =
        "create table User(id integer primary key autoincrement, name text, iphone text)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.apply {
            execSQL(createUserSql)
            Log.d(TAG, "$name database created")
            // 插入一些初始数据
            insert("User", null, ContentValues().apply {
                put("name", "Tom")
                put("iphone", "11111")
            })
            insert("User", null, ContentValues().apply {
                put("name", "Jack")
                put("iphone", "22222")
            })
            insert("User", null, ContentValues().apply {
                put("name", "Mary")
                put("iphone", "33333")
            })
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.apply {
            execSQL("drop table if exists User")
            onCreate(this)
        }
    }
}