package com.fatahapps.pulatest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PulaApp: Application() {
    override fun onCreate() {
        super.onCreate()
        mSelf = this
    }

    companion object {
        private var mSelf: PulaApp? = null

        fun self(): PulaApp {
            return mSelf!!
        }
    }
}

