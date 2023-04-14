package com.forgo7ten.attackapp

import com.forgo7ten.attackapp.attack.denialservice.DenialServiceActivity
import com.forgo7ten.attackapp.attack.unimplcomp.UnimplCompActivity
import com.forgo7ten.attackapp.model.Vulnerability


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
            add( Vulnerability( appContext.getString(R.string.defining_components_not_implemented), UnimplCompActivity::class.java ) )
            add( Vulnerability( appContext.getString(R.string.denial_service_title), DenialServiceActivity::class.java ) )
        }
    }
}
