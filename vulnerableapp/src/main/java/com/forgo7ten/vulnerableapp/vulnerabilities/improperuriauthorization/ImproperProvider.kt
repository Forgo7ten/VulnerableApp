package com.forgo7ten.vulnerableapp.vulnerabilities.improperuriauthorization

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.forgo7ten.vulnerableapp.MyDbHelper

class ImproperProvider : ContentProvider() {

    private val TAG = "ImproperProvider"
    private val userDir = 1
    private val userItem = 2
    private lateinit var dbHelper: MyDbHelper
    private val AUTHORITY = "com.forgo7ten.vulnerableapp.IMPROPERPROVIDER"
    private val uriMatcher by lazy {
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        matcher.addURI(AUTHORITY, "user", userDir)
        matcher.addURI(AUTHORITY, "user/#", userItem)
        matcher
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int =
        dbHelper?.let {
            val db = it.writableDatabase
            val deletedRows = when (uriMatcher.match(uri)) {
                userDir -> db.delete("User", selection, selectionArgs)
                userItem -> {
                    val id = uri.pathSegments[1]
                    db.delete("User", "id = ?", arrayOf(id))
                }
                else -> 0
            }
            if (deletedRows > 0) {
                context?.contentResolver?.notifyChange(uri, null)
            }
            Log.d(TAG, "delete: $deletedRows")
            deletedRows
        } ?: 0  // 如果dbHelper为空，返回0


    override fun getType(uri: Uri): String? = when (uriMatcher.match(uri)) {
        userDir -> "vnd.android.cursor.dir/vnd.com.forgo7ten.vulnerableapp.IMPROPERPROVIDER.user"
        userItem -> "vnd.android.cursor.item/vnd.com.forgo7ten.vulnerableapp.IMPROPERPROVIDER.user"
        else -> null
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? = dbHelper?.let {
        val db = it.writableDatabase
        val id = when (uriMatcher.match(uri)) {
            userDir, userItem -> db.insert("User", null, values)
            else -> 0
        }
        Log.d(TAG, "insert: $id")
        if (id > 0) {
            val uri = Uri.parse("content://$AUTHORITY/user/$id")
            context?.contentResolver?.notifyChange(uri, null)
            uri
        } else {
            null
        }
    }

    override fun onCreate(): Boolean = context?.let {
        dbHelper = MyDbHelper(it, "ImproperProvider.db", 1)
        true
    } ?: false

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? = dbHelper?.let {
        val db = it.readableDatabase
        val cursor = when (uriMatcher.match(uri)) {
            userDir -> db.query("User", projection, selection, selectionArgs, null, null, sortOrder)
            userItem -> {
                val id = uri.pathSegments[1]
                db.query("User", projection, "id = ?", arrayOf(id), null, null, sortOrder)
            }
            else -> null
        }
        cursor
    }


    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = dbHelper?.let {
        val db = it.writableDatabase
        val updatedRows = when (uriMatcher.match(uri)) {
            userDir -> db.update("User", values, selection, selectionArgs)
            userItem -> {
                val id = uri.pathSegments[1]
                db.update("User", values, "id = ?", arrayOf(id))
            }
            else -> 0
        }
        Log.d(TAG, "update: $updatedRows")
        updatedRows
    } ?: 0
}