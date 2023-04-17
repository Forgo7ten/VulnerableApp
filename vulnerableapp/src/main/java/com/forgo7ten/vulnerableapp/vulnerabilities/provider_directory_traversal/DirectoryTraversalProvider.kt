package com.forgo7ten.vulnerableapp.vulnerabilities.provider_directory_traversal

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.File
import java.io.FileNotFoundException

/**
 * @ClassName DirectoryTraversalProvider
 * @Description // 复现【Provider文件目录遍历】漏洞的Provider
 * @Author Forgo7ten
 **/
class DirectoryTraversalProvider : ContentProvider() {
    private val TAG = "DirectoryTraversal"
    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor? {
        // 从Uri中获取路径
        uri.path?.let {
            // 并没有对路径进行校验，而是直接拼接到文件系统的路径中，导致了文件目录遍历漏洞
            val file = File(context!!.filesDir, it)
            // 默认的根路径是/data/data/packagename/files
            Log.d(TAG, "openFile: ${file.absolutePath}")
            if (file.exists()) {
                // 文件存在的话，返回可读的文件
                return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
            }
        }
        // 文件不存在，抛出异常
        throw FileNotFoundException(uri.path)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        TODO("Implement this to handle query requests from clients.")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}