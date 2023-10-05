package com.example.onepick

import android.app.Application
import com.example.onepick.data.DefaultTmdbContainer
import com.example.onepick.data.TmdbContainer

class TmdbApplication : Application() {

    lateinit var container: TmdbContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultTmdbContainer()
    }
}