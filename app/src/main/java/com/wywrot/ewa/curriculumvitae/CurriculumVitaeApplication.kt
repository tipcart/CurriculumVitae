package com.wywrot.ewa.curriculumvitae

import android.content.Context
import android.content.SharedPreferences
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

@Suppress("PrivatePropertyName")
class CurriculumVitaeApplication : MultiDexApplication() {

    companion object {
        lateinit var instance: CurriculumVitaeApplication
        var sharedPreferences: SharedPreferences? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}

fun sharedPreferences(): SharedPreferences? {
    if (CurriculumVitaeApplication.sharedPreferences == null) {
        CurriculumVitaeApplication.sharedPreferences =
            CurriculumVitaeApplication.instance.applicationContext
                .getSharedPreferences("CV_SP_KEY", Context.MODE_PRIVATE)
    }
    return CurriculumVitaeApplication.sharedPreferences
}