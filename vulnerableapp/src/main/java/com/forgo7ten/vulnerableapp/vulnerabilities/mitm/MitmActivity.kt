package com.forgo7ten.vulnerableapp.vulnerabilities.mitm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.forgo7ten.vulnerableapp.databinding.ActivityMitmBinding
import org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * @ClassName MitmActivity
 * @Description // 中间人攻击漏洞复现Activity
 * @Author Forgo7ten
 **/
class MitmActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MitmActivity"
    }

    private lateinit var binding: ActivityMitmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMitmBinding.inflate(layoutInflater).apply { setContentView(root) }
        initViews()
    }


    private fun initViews() {
        binding.btnMitm1.setOnClickListener {
            Thread {
                try {
                    mitm1()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }
        binding.btnMitm2.setOnClickListener {
            Thread {
                try {
                    mitm2()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }
        binding.btnMitm2fix.setOnClickListener {
            Thread {
                try {
                    mitm2fix()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }
        binding.btnMitm3.setOnClickListener {
            Thread {
                try {
                    mitm3()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }
    }

    /**
     * 信任所有主机名
     */
    fun mitm1() {
        // 创建URL对象
        val url = URL("https://www.baidu.com")
        // 创建HttpURLConnection对象
        val connection: HttpsURLConnection = url.openConnection() as HttpsURLConnection
        // 设置信任所有主机名
        connection.hostnameVerifier = ALLOW_ALL_HOSTNAME_VERIFIER

        // 发起连接
        connection.connect()

        // 获取响应码
        val responseCode: Int = connection.getResponseCode()
        Log.d(TAG, "Response Code: $responseCode")

        // 读取响应内容
        val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
        var line: String?
        val response = StringBuilder()
        while (reader.readLine().also { line = it } != null) {
            response.append(line)
        }
        reader.close()

        // 打印响应内容
        Log.d(TAG, "Response: $response")

        // 断开连接
        connection.disconnect()
    }

    /**
     * 设置主机名验证器但不做任何验证
     */
    private fun mitm2() {
        // 创建URL对象
        val url = URL("https://www.baidu.com")
        // 创建HttpURLConnection对象
        val connection: HttpsURLConnection = url.openConnection() as HttpsURLConnection
        // 设置主机名验证器，但是这里不做任何验证
        connection.hostnameVerifier = HostnameVerifier { hostname, session -> true }

        // 发起连接
        connection.connect()

        // 获取响应码
        val responseCode: Int = connection.getResponseCode()
        Log.d(TAG, "Response Code: $responseCode")

        // 读取响应内容
        val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
        var line: String?
        val response = StringBuilder()
        while (reader.readLine().also { line = it } != null) {
            response.append(line)
        }
        reader.close()
        // 打印响应内容
        Log.d(TAG, "Response: $response")
        // 断开连接
        connection.disconnect()

    }

    /**
     * 校验域名
     */
    private fun mitm2fix() {
        // 创建URL对象
        val url = URL("https://www.baidu.com")
        // 创建HttpURLConnection对象
        val connection: HttpsURLConnection = url.openConnection() as HttpsURLConnection

        // 设置主机名验证器，验证域名是否为github.com
        connection.hostnameVerifier = HostnameVerifier { hostname, session ->
            HttpsURLConnection.getDefaultHostnameVerifier().let { it.verify("github.com", session) }
        }

        // 发起连接
        connection.connect()

        // 获取响应码
        val responseCode: Int = connection.getResponseCode()
        Log.d(TAG, "Response Code: $responseCode")

        // 读取响应内容
        val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
        var line: String?
        val response = StringBuilder()
        while (reader.readLine().also { line = it } != null) {
            response.append(line)
        }
        reader.close()
        // 打印响应内容
        Log.d(TAG, "Response: $response")
        // 断开连接
        connection.disconnect()
    }

    /**
     * 不校验证书
     */
    private fun mitm3() {
        // 创建URL对象
        val url = URL("https://www.baidu.com")
        // 创建HttpURLConnection对象
        val connection: HttpsURLConnection = url.openConnection() as HttpsURLConnection

        // 设置SSLContext，不校验证书
        connection.sslSocketFactory = getSSLSocketFactory()

        // 发起连接
        connection.connect()

        // 获取响应码
        val responseCode: Int = connection.getResponseCode()
        Log.d(TAG, "Response Code: $responseCode")

        // 读取响应内容
        val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
        var line: String?
        val response = StringBuilder()
        while (reader.readLine().also { line = it } != null) {
            response.append(line)
        }
        reader.close()

        // 打印响应内容
        Log.d(TAG, "Response: $response")

        // 断开连接
        connection.disconnect()

    }

    @Throws(java.lang.Exception::class)
    fun getSSLSocketFactory(): SSLSocketFactory? {
        val trustAllCerts: Array<TrustManager> = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(
                chain: Array<X509Certificate?>?,
                authType: String?
            ) {
                // do nothing, 接收任意客户端证书
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(
                chain: Array<X509Certificate?>?,
                authType: String?
            ) {
                // do nothing, 接收任意服务端证书
            }

            override fun getAcceptedIssuers(): Array<X509Certificate?> =
                arrayOfNulls<X509Certificate>(0)

        })
        val sslContext: SSLContext = SSLContext.getInstance("TLS")
        sslContext.init(
            null, trustAllCerts,
            SecureRandom()
        )
        return sslContext
            .getSocketFactory()
    }
}