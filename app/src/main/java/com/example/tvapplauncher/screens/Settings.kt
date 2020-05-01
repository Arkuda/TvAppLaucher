package com.example.tvapplauncher.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.leanback.widget.VerticalGridView
import com.example.tvapplauncher.R
import com.example.tvapplauncher.adapters.ItemSettingsCallback
import com.example.tvapplauncher.adapters.SelectionAppListAdapter
import com.example.tvapplauncher.data_a.ApplicationInfo
import com.example.tvapplauncher.data_s.AppData
import com.example.tvapplauncher.data_s.AppDataList
import com.example.tvapplauncher.utils.AppManager
import com.example.tvapplauncher.utils.ShPrefUtils

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        val spUtils = ShPrefUtils(this)
        val thisContext = this


        val favAppsPackageNameList = mutableSetOf<String>()

        spUtils.getSavedApps().apply {
            if (this != null) {
                favAppsPackageNameList.addAll(this.map { it.app_package })
            }
        }


        val apps = AppManager(this).getAppList()

        val adapter = SelectionAppListAdapter(apps,spUtils.getSavedApps()?.map { it.app_package }, object : ItemSettingsCallback {
            override fun onChecked(item: ApplicationInfo, state: Boolean) {
                if (state) {
                    favAppsPackageNameList.add(item.packageName)
                } else {
                    favAppsPackageNameList.remove(item.packageName)
                }
                doSave(favAppsPackageNameList, spUtils)
            }


        })

        findViewById<VerticalGridView>(R.id.apps_grid).adapter = adapter
    }


    fun doSave(
        favAppsPackageNameList: MutableSet<String>,
        spUtils: ShPrefUtils
    ) {
        spUtils.saveFavApps(AppDataList(favAppsPackageNameList.map { AppData(it) }.toList()))
    }
}
