package com.forgo7ten.vulnerableapp.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.forgo7ten.vulnerableapp.R
import com.forgo7ten.vulnerableapp.databinding.ActivityMainBinding
import com.forgo7ten.vulnerableapp.model.Vulnerability
import com.forgo7ten.vulnerableapp.view.adapter.VulnerabilitiesAdapter

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vulnerabilities = ArrayList<Vulnerability>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 初始化视图
        initViews()
    }

    /**
     * 初始化视图
     */
    private fun initViews() {
        // 设置toolbar
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.let {
            // 修改原本的返回按钮为自定义的图标
            it.setHomeAsUpIndicator(R.drawable.home)
            // 显示原本的返回按钮
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "主页"
        }


        // 设置导航栏

        // 设置默认选中
        // binding.navView.setCheckedItem(R.id.user)
        // 设置导航栏的点击事件
        binding.navView.setNavigationItemSelectedListener {
            // 像onOptionsItemSelected一样处理
            when (it.itemId) {
                R.id.user -> Toast.makeText(this, "User", Toast.LENGTH_SHORT).show()
                R.id.region -> Toast.makeText(this, "Region", Toast.LENGTH_SHORT).show()
                R.id.help -> Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show()
                R.id.share -> Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
            }
            // 关闭侧边栏
            binding.drawerLayout.closeDrawers()
            true
        }

        initVulnerabilities()
    }

    /**
     * 初始化漏洞列表，向RecyclerView中添加数据
     */
    private fun initVulnerabilities() {
        repeat(5) {
            vulnerabilities.add(Vulnerability("测试项目1", MainActivity::class.java))
            vulnerabilities.add(Vulnerability("测试项目2", MainActivity::class.java))
            vulnerabilities.add(Vulnerability("测试项目3", MainActivity::class.java))
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = VulnerabilitiesAdapter(vulnerabilities)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        /* 要想创建菜单，必须要重写这个方法：设置菜单的布局文件，并返回true */
        menuInflater.inflate(R.menu.menu_toobar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /* 设置菜单图标的点击事件，重写这个方法 */
        when (item.itemId) {
            // 设置原本的返回按钮，修改为打开侧边滑动菜单
            android.R.id.home -> binding.drawerLayout.openDrawer(GravityCompat.START)
            // 这俩是菜单图标的点击事件
            R.id.refresh -> Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show()
            R.id.settings -> Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}