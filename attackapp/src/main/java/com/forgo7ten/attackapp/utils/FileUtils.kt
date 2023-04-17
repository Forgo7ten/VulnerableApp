package com.forgo7ten.attackapp.utils

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * @ClassName FileUtils
 * @Description // 文件工具类
 * @Author Forgo7ten
 **/
object FileUtils {
    /**
     * @Description // 从输入流中读取数据
     * @Param [inputStream] 输入流
     * @Return java.lang.String
     */
    fun readFromInputStream(inputStream: InputStream): String {
        val sb = StringBuilder()
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String? = reader.readLine()
        while (line != null) {
            sb.append(line)
            line = reader.readLine()
        }
        return sb.toString()
    }
}