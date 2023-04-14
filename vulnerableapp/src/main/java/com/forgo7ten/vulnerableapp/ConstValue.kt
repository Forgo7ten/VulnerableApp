package com.forgo7ten.vulnerableapp

import com.forgo7ten.vulnerableapp.model.Vulnerability
import com.forgo7ten.vulnerableapp.vulnerabilities.VulnerabilityShowActivity
import com.forgo7ten.vulnerableapp.vulnerabilities.denialservice.DenialServiceActivity

/**
 * @ClassName ConstValue
 * @Description //存放一些全局变量
 * @Author Forgo7ten
 **/
object ConstValue {

    lateinit var appContext: App
    val vulnerabilities = ArrayList<Vulnerability>()
    fun initVulnerabilities() {
        vulnerabilities.apply {
            add( Vulnerability( appContext.getString(R.string.denial_service_title), "低危",
                    "<h2>漏洞简介</h2>\n" + "<p>当Activity等组件接收Intent、并且使用了<code>get...Extra()</code>等方法时候，如果没有对传入的数据进行判断和处理，攻击者可以通过传入序列化对象导致App崩溃。</p>\n" + "<h3>漏洞详情</h3>\n" + "<p>当<code>get...Extra()</code>接收到一个自定义的序列化对象Serializable或者Parcelable对象时，在其中的getSerializableExtra()、getParcelable()方法中，由于无法解析传入的序列化类（找不到该类）导致报错</p>\n" + "<h3>漏洞危害</h3>\n" + "<p>当应用组件对外暴露时，第三方应用向手机传入构造好的Intent可以导致应用崩溃，极度影响用户体验。</p>\n" + "<h3>修复建议</h3>\n" + "<ul>\n" + "<li>非必要到处组件设置exported属性为false</li>\n" + "<li>导出组件在getIntent()后，get…Extra()方法时<strong>注意异常捕获</strong></li>\n" + "<li>对导出的组件设置好权限控制，不让任意第三方应用访问。</li>\n" + "\n" + "</ul>",
                    DenialServiceActivity::class.java ) )
            add( Vulnerability( appContext.getString(R.string.defining_components_not_implemented), "低危",
                    "<h2>漏洞简介</h2>\n" + "<p>在Androidmanifest.xml文件中定义了组件且没有代码实现，则攻击者可以通过发送Intent，通过ddos攻击导致app崩溃。</p>\n" + "<h3>漏洞详情</h3>\n" + "<p>1.当Androidmanifest.xml文件中定义了组件A且没有代码实现时，如果组件A在manifest文件里设置为导出（exported=true），则任意第三方应用均可通过发送Intent给A的形式导致APP崩溃。</p>\n" + "<p>2.当Androidmanifest.xml文件中定义了组件A且没有代码实现时，如果组件A在manifest文件里设置为非导出（exported=false），则第三方应用可以分析所有的导出组件，若通过发送Intent已导出的组件B，使其可以发送Intent给组件A，依然可以导致APP崩溃。</p>\n" + "<h3>漏洞危害</h3>\n" + "<p>造成APP反复崩溃</p>\n" + "<h3>修复建议</h3>\n" + "<p>删除AndroidManifest.xml文件中所有未实现代码的声明。</p>\n" + "<h2>漏洞复现</h2>\n" + "<p>执行adb命令：</p>\n" + "<pre><code>adb shell am start -n com.forgo7ten.vulnerableapp/.vulnerabilities.NonexistentActivity\n" + "</code></pre>\n" + "<p>&nbsp;</p>\n",
                    VulnerabilityShowActivity::class.java ) )
        }
    }
}
