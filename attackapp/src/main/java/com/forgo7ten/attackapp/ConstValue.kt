package com.forgo7ten.attackapp

import com.forgo7ten.attackapp.attack.denialservice.DenialServiceActivity
import com.forgo7ten.attackapp.attack.improperuriauthorization.ImproperUriActivity
import com.forgo7ten.attackapp.attack.provider_directory_traversal.DirectoryTraversalActivity
import com.forgo7ten.attackapp.attack.unimplcomp.UnimplCompActivity
import com.forgo7ten.attackapp.model.Vulnerability


/**
 * @ClassName ConstValue
 * @Description //存放一些全局变量，也负责初始化已复现过的漏洞列表
 * @Author Forgo7ten
 **/
object ConstValue {

    lateinit var appContext: App
    val vulnerabilities = ArrayList<Vulnerability>()
    fun initVulnerabilities() {
        vulnerabilities.apply {
            add( Vulnerability( appContext.getString(R.string.defining_components_not_implemented), UnimplCompActivity::class.java ) )
            add( Vulnerability( appContext.getString(R.string.denial_service_title), DenialServiceActivity::class.java ) )
            add( Vulnerability( appContext.getString(R.string.improper_uri_authorization), ImproperUriActivity::class.java ) )
            add( Vulnerability( appContext.getString(R.string.provider_directory_traversal), DirectoryTraversalActivity::class.java ) )
        }
    }
}
