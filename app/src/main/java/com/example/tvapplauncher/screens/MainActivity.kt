package com.example.tvapplauncher.screens

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.leanback.widget.HorizontalGridView
import com.example.tvapplauncher.R
import com.example.tvapplauncher.adapters.AppListAdapter
import com.example.tvapplauncher.adapters.ItemClickCallback
import com.example.tvapplauncher.data_a.ApplicationInfo
import com.example.tvapplauncher.data_s.AppData
import com.example.tvapplauncher.utils.AppManager
import com.example.tvapplauncher.utils.ShPrefUtils


class MainActivity : AppCompatActivity() {

    lateinit var spUtils: ShPrefUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spUtils = ShPrefUtils(this)
        val thisContext = this

        loadApps(thisContext, spUtils)


        findViewById<ImageView>(R.id.settings).apply {
            setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    v.background = v.context.getDrawable(R.drawable.selection_drawable)
                } else {
                    v.background = null
                }
            }
            setOnClickListener {
                startActivityForResult(Intent(thisContext, Settings::class.java), 777)
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loadApps(this, spUtils)
    }

    private fun filterApps(
        favApps: List<AppData>?,
        allApps: List<ApplicationInfo>
    ): List<ApplicationInfo> {
        return if (favApps == null) {
            allApps
        } else {
            val favAppsStrList = favApps.map { it.app_package }
            val favAppsInfo = mutableListOf<ApplicationInfo>()
            allApps.forEach {
                if (favAppsStrList.contains(it.packageName)) {
                    favAppsInfo.add(it)
                }
            }
            favAppsInfo
        }
    }

    private fun loadApps(
        thisContext: MainActivity,
        spUtils: ShPrefUtils
    ) {

        val favApps = spUtils.getSavedApps()
        val allApps = AppManager(this).getAppList()

        val apps = filterApps(favApps, allApps)


        val adapter = AppListAdapter(apps, object : ItemClickCallback {
            override fun onClick(appInfo: ApplicationInfo) {
                val appIntent =
                    thisContext.packageManager.getLaunchIntentForPackage(appInfo.packageName)
                thisContext.startActivity(appIntent)
            }

        })
        findViewById<HorizontalGridView>(R.id.apps_grid).apply {
            this.adapter = adapter
        }
    }
}
