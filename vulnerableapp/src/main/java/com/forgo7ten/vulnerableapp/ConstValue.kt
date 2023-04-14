package com.forgo7ten.vulnerableapp

import com.forgo7ten.vulnerableapp.model.Vulnerability
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
            add(
                Vulnerability(
                    appContext.getString(R.string.denial_service_title),
                    "<h2>漏洞简介</h2>\n" + "<p>当Activity等组件接收Intent、并且使用了<code>get...Extra()</code>等方法时候，如果没有对传入的数据进行判断和处理，攻击者可以通过传入序列化对象导致App崩溃。</p>\n" + "<h3>漏洞详情</h3>\n" + "<p>当<code>get...Extra()</code>接收到一个自定义的序列化对象Serializable或者Parcelable对象时，在其中的getSerializableExtra()、getParcelable()方法中，由于无法解析传入的序列化类（找不到该类）导致报错</p>\n" + "<h3>漏洞危害</h3>\n" + "<p>当应用组件对外暴露时，第三方应用向手机传入构造好的Intent可以导致应用崩溃，极度影响用户体验。</p>\n" + "<h3>修复建议</h3>\n" + "<ul>\n" + "<li>非必要到处组件设置exported属性为false</li>\n" + "<li>导出组件在getIntent()后，get…Extra()方法时<strong>注意异常捕获</strong></li>\n" + "<li>对导出的组件设置好权限控制，不让任意第三方应用访问。</li>\n" + "\n" + "</ul>",
                    DenialServiceActivity::class.java
                )
            )
        }
    }
}
