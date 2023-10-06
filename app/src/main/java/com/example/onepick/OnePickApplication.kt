package com.example.onepick

import android.app.Application
import com.example.onepick.data.ChatGptContainer
import com.example.onepick.data.DefaultChatGptContainer

class OnePickApplication : Application() {
    lateinit var container: ChatGptContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultChatGptContainer()
    }
}