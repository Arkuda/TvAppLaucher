package com.example.tvapplauncher.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.tvapplauncher.data_s.AppData
import com.example.tvapplauncher.data_s.AppDataList
import com.google.gson.Gson

class ShPrefUtils(var context: Context) {

    val APPS_FAVOR_STR = "apps_favorite_json"
    val pref: SharedPreferences

    init {
        pref = context.getSharedPreferences("apps", Context.MODE_PRIVATE)
    }


    fun getSavedApps(): List<AppData>? {
        val appsJson = pref.getString(APPS_FAVOR_STR, "")
        return Gson().fromJson(appsJson, AppDataList::class.java)?.apps
    }

    fun saveFavApps(apps: AppDataList) {
        val editor = pref.edit()
        editor.putString(APPS_FAVOR_STR, Gson().toJson(apps))
        editor.apply()
    }


}