package com.example.tvapplauncher.utils

import android.content.Context
import com.example.tvapplauncher.data_a.ApplicationInfo
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.media.tv.TvInputManager


class AppManager(var context: Context) {

    fun getAppList(): List<ApplicationInfo> {


        val manager = context.packageManager
        val apps = mutableListOf<ApplicationInfo>()


        val allAppsInfo = mutableListOf<ResolveInfo>().apply {
            addAll(getMobileApps(manager))
            addAll(getTvApps(manager))
        }

        allAppsInfo.forEach {
            apps.add(
                ApplicationInfo(
                    it.loadLabel(manager).toString(),
                    it.activityInfo.packageName,
                    it.activityInfo.loadIcon(manager)
                )
            )
        }
        return apps

    }

    private fun getTvApps(manager : PackageManager): MutableList<ResolveInfo> {
        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)

        return manager.queryIntentActivities(i, 0)
    }

    private fun getMobileApps(manager : PackageManager): MutableList<ResolveInfo> {
        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)

        return manager.queryIntentActivities(i, 0)
    }
}