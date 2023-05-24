package com.forgo7ten.vulnerableapp

import com.forgo7ten.vulnerableapp.model.Vulnerability
import com.forgo7ten.vulnerableapp.vulnerabilities.VulnerabilityShowActivity
import com.forgo7ten.vulnerableapp.vulnerabilities.denialservice.DenialServiceActivity
import com.forgo7ten.vulnerableapp.vulnerabilities.mitm.MitmActivity
import com.forgo7ten.vulnerableapp.vulnerabilities.webview.WebviewActivity

/**
 * @ClassName ConstValue
 * @Description //存放一些全局变量，负责初始化漏洞列表
 * @Author Forgo7ten
 **/
object ConstValue {

    lateinit var appContext: App
    val vulnerabilities = ArrayList<Vulnerability>()
    fun initVulnerabilities() {
        vulnerabilities.apply {
            /* Manifest安全 */
            add( Vulnerability( appContext.getString(R.string.defining_components_not_implemented), "低危",
                "<h1>Manifest中定义组件未实现</h1>\n" + "<h2>漏洞简介</h2>\n" + "<p>在Androidmanifest.xml文件中定义了组件且没有代码实现，则攻击者可以通过发送Intent，通过ddos攻击导致app崩溃。</p>\n" + "<h3>漏洞详情</h3>\n" + "<ol>\n" + "<li>当Androidmanifest.xml文件中定义了组件A且没有代码实现时，如果组件A在manifest文件里设置为导出（exported=true），则任意第三方应用均可通过发送Intent给A的形式导致APP崩溃。</li>\n" + "<li>当Androidmanifest.xml文件中定义了组件A且没有代码实现时，如果组件A在manifest文件里设置为非导出（exported=false），则第三方应用可以分析所有的导出组件，若通过发送Intent已导出的组件B，使其可以发送Intent给组件A，依然可以导致APP崩溃。</li>\n" + "\n" + "</ol>\n" + "<h3>漏洞危害</h3>\n" + "<p>造成APP反复崩溃</p>\n" + "<h3>修复建议</h3>\n" + "<p>删除AndroidManifest.xml文件中所有未实现代码的声明。</p>\n" + "<h2>漏洞复现</h2>\n" + "<blockquote><p>复现环境：Pixel; AOSP_Android10.0.0_r2</p>\n" + "</blockquote>\n" + "<p>执行adb命令：</p>\n" + "<pre><code>adb shell am start -n com.forgo7ten.vulnerableapp/.vulnerabilities.NonexistentActivity\n" + "</code></pre>\n" + "<p>&nbsp;</p>\n",
                VulnerabilityShowActivity::class.java ) )
            add( Vulnerability( appContext.getString(R.string.allow_backup_config), "低危",
                "<h1>应用数据备份配置不当漏洞</h1>\n" + "<h2>漏洞简介</h2>\n" + "<p><code>AndroidManifest.xml</code>文件中<code>android:allowBackup</code>属性错误的设置为<code>true</code>，导致客户端应用数据可以被备份导出。</p>\n" + "<h2>漏洞复现</h2>\n" + "<blockquote><p>复现环境：Pixel; AOSP_Android10.0.0_r2</p>\n" + "</blockquote>\n" + "<p>通过adb命令，将应用备份出来，命令如下。</p>\n" + "<pre><code>adb shell am start -n com.forgo7ten.vulnerableapp/.vulnerabilities.NonexistentActivity\n" + "</code></pre>\n" + "<p>[-system | -nosystem] 是否备份系统</p>\n" + "<p>[-apk | -noapk] 是否备份apk安装文件</p>\n" + "<p>[-shared | -noshared]是否备份手机存储空间</p>\n" + "<p>-f *.ab 存档格式一定要是.ab</p>\n" + "<p>期间应用会弹出如下窗口，选择右下角的“BACK UP MY DATA”按钮即可备份，密码可输入可不输入。</p>\n" + "<p>&nbsp;</p>\n",
            VulnerabilityShowActivity::class.java ) )
            add( Vulnerability( appContext.getString(R.string.allow_debug_config), "低危",
                "<h1>应用调试模式配置不当漏洞</h1>\n" + "<h2>漏洞简介</h2>\n" + "<p>&#39;AndroidManifest.xml&#39;文件中&#39;android:debuggable&#39;属性错误的设置为&#39;true&#39;,导致客户端应用可以被任意调试。</p>\n" + "<p>&nbsp;</p>\n",
            VulnerabilityShowActivity::class.java ) )
            /* 通用组件安全 */
            add( Vulnerability( appContext.getString(R.string.denial_service_title), "低危",
                "<h1>App通用型拒绝服务漏洞</h1>\n" + "<h2>漏洞简介</h2>\n" + "<p>当Activity等组件接收Intent、并且使用了<code>get...Extra()</code>等方法时候，如果没有对传入的数据进行判断和处理，攻击者可以通过传入序列化对象导致App崩溃。</p>\n" + "<h3>漏洞详情</h3>\n" + "<p>当<code>get...Extra()</code>接收到一个自定义的序列化对象Serializable或者Parcelable对象时，在其中的getSerializableExtra()、getParcelable()方法中，由于无法解析传入的序列化类（找不到该类）导致报错</p>\n" + "<h3>漏洞危害</h3>\n" + "<p>当应用组件对外暴露时，第三方应用向手机传入构造好的Intent可以导致应用崩溃，极度影响用户体验。</p>\n" + "<h3>修复建议</h3>\n" + "<ul>\n" + "<li>非必要到处组件设置exported属性为false</li>\n" + "<li>导出组件在getIntent()后，get…Extra()方法时<strong>注意异常捕获</strong></li>\n" + "<li>对导出的组件设置好权限控制，不让任意第三方应用访问。</li>\n" + "\n" + "</ul>\n" + "<p>&nbsp;</p>\n" + "<h2>漏洞复现</h2>\n" + "<blockquote><p>复现环境：Pixel; AOSP_Android10.0.0_r2</p>\n" + "</blockquote>\n" + "<p>参考AttackApp应用</p>\n" + "<p>&nbsp;</p>\n",
                DenialServiceActivity::class.java ) )
            /* provider组件安全 */
            add( Vulnerability(appContext.getString(R.string.improper_uri_authorization),"低危",
                "<h1>ContentProviderURI授权不当</h1>\n" + "<h2>漏洞简介</h2>\n" + "<p>在AndroidManifest.xml中，当<code>&lt;provider&gt;</code>标签的<code>android:grantUriPermissions</code>属性被设置为<code>true</code>时，能临时超过<code>permission</code>、<code>readPermission</code>、<code>writePermission</code>属性的限制，使得平常无权对ContentProvider数据的访问进行授权。当ContentProvider存储敏感信息时，可能会造成敏感信息泄露。</p>\n" + "<h3>漏洞详情</h3>\n" + "<p>条件：</p>\n" + "<ul>\n" + "<li>含ContentProvider的应用A设置了<code>&lt;provider&gt;</code>标签的<code>grantUriPermissions=&quot;true”</code>属性</li>\n" + "<li>该应用A启动了应用B，并使用了B的intent，使得B可以添加授权的Flags</li>\n" + "\n" + "</ul>\n" + "<p>当含ContentProvider的应用A，启动另一个应用B时，如果应用A设置了<code>grantUriPermissions=&quot;true”</code>，即使该provider的<code>exported=&quot;false”</code>，即使该provider的<code>permission</code>、<code>readPermission</code>、<code>writePermission</code>属性设置了所需的自定义权限。应用B不需要设置权限即可访问该Provider。</p>\n" + "<p>正常情况下：即使从应用A启动了应用B，应用B也应含有<code>permission</code>等属性所需要的权限才可以访问该provider。</p>\n" + "<p><a href='https://developer.android.google.cn/guide/topics/manifest/provider-element?hl=zh-cn'>Android 开发者  | Provider介绍</a></p>\n" + "<h3>漏洞危害</h3>\n" + "<p>导致应用B可以访问应用A的provider，导致数据泄露。</p>\n" + "<h3>修复建议</h3>\n" + "<p>如果无需对外提供数据，将<code>&lt;provider&gt;</code>的<code>android:grantUriPermissions</code>属性设置为<code>false</code>。</p>\n" + "<h2>漏洞复现</h2>\n" + "<blockquote><p>复现环境：Pixel; AOSP_Android10.0.0_r2</p>\n" + "</blockquote>\n" + "<p>参考AttackApp应用</p>\n",
                VulnerabilityShowActivity::class.java) )
            add( Vulnerability(appContext.getString(R.string.provider_directory_traversal),"低危",
            "<h1>Content Provider文件目录遍历漏洞</h1>\n" + "<h2>漏洞简介</h2>\n" + "<p>对外暴露的Content Provider实现了openFile()接口，因此其他有相应调用该ContentProvider权限的应用即可调用ContentProvider的openFile接口进行文件数据访问。</p>\n" + "<p>但是如果没有进行ContentProvider访问权限控制和对访问的目标文件Uri进行有效判断，攻击者利用文件目录遍历访问任意可读文件。</p>\n" + "<h3>漏洞详情</h3>\n" + "<p>Android ContentProvider存在文件目录遍历安全漏洞，该漏洞源于对外暴露ContentProvider组件的应用，没有对ContentProvider组件的访问进行权限控制和对访问的目标文件的ContentQueryUri进行有效判断。</p>\n" + "<p>攻击者利用该应用暴露的ContentProvider的openFile()接口进行文件目录遍历以达到访问任意可读文件的目的。</p>\n" + "<pre><code class='language-markdown' lang='markdown'>public ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String mode)\n" + "</code></pre>\n" + "<p>对外暴露的ContentProvider组件<strong>实现了openFile()接口</strong>，<strong>没有对所访问的目标文件Uri进行有效判断</strong>，如<strong>没有过滤</strong>限制如<code>../</code>可实现任意刻度文件的访问的ContentQueryUri</p>\n" + "<h3>漏洞危害</h3>\n" + "<h3>修复建议</h3>\n" + "<ul>\n" + "<li><p>非必要导出设置为不导出</p>\n" + "</li>\n" + "<li><p>如果没有读写文件的必要，去除<code>openFile()</code>方法接口</p>\n" + "</li>\n" + "<li><p>过滤限制跨域访问，对访问的目标文件路径进行有效判断</p>\n" + "<ul>\n" + "<li>使用Uri.decode()先对ContentQueryUri进行解码后，再对<code>uri</code>进行校验，过滤其中类似于<code>../</code>的字符串</li>\n" + "\n" + "</ul>\n" + "</li>\n" + "<li><p>设置权限来进行内部应用通过Content Provider的数据共享</p>\n" + "</li>\n" + "\n" + "</ul>\n" + "<h2>漏洞复现</h2>\n" + "<blockquote><p>复现环境：Pixel; AOSP_Android10.0.0_r2</p>\n" + "</blockquote>\n" + "<p>参考AttackApp应用</p>\n",
            VulnerabilityShowActivity::class.java) )
            /* WebView组件安全 */
            add( Vulnerability(appContext.getString(R.string.webview_vulnerability),"高危", "TODO", WebviewActivity::class.java) )
            /* Http/Https安全 */
            add( Vulnerability(appContext.getString(R.string.mitm),"高危", "TODO", MitmActivity::class.java) )
        }
    }
}
