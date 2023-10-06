package com.example.onepick

import android.app.Application
import com.example.onepick.data.ChatGptContainer
import com.example.onepick.data.DefaultChatGptContainer
import com.example.onepick.data.DefaultTmdbContainer
import com.example.onepick.data.TmdbContainer

class OnePickApplication : Application() {
    lateinit var chatGptcontainer: ChatGptContainer
    lateinit var tmdbContainer: TmdbContainer
    override fun onCreate() {
        super.onCreate()
        chatGptcontainer = DefaultChatGptContainer()
        tmdbContainer = DefaultTmdbContainer()
    }
}