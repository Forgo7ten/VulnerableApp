package com.forgo7ten.vulnerableapp.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.forgo7ten.vulnerableapp.R
import com.forgo7ten.vulnerableapp.model.Vulnerability

/**
 * @ClassName VulnerabilitiesAdapter
 * @Description // 用于显示漏洞列表的Adapter
 * @Author Forgo7ten
 **/
class VulnerabilitiesAdapter(val VulnerabilityList: List<Vulnerability>) :
    RecyclerView.Adapter<VulnerabilitiesAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // 这里的view就是每个item的布局，在这里对布局中的控件进行初始化
        val titleTv = view.findViewById(R.id.title_tv) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 这里是加载item布局，并创建ViewHolder实例 返回
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_vulnerability, parent, false)
        val holder = ViewHolder(view)
        holder.titleTv.setOnClickListener {
            // 设置点击事件，点击时候打开相应的Activity
            val position = holder.adapterPosition
            val vulnerability = VulnerabilityList[position]
            val intent = Intent(parent.context, vulnerability.targetActivity)
            intent.putExtra("position", position)
            parent.context.startActivity(intent)
        }
        return holder
    }

    // 返回item的个数
    override fun getItemCount(): Int = VulnerabilityList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 这里是对item的数据进行赋值
        holder.titleTv.text = VulnerabilityList[position].title
    }
}