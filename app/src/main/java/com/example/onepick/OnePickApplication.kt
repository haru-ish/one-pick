package com.example.onepick

import android.app.Application
import com.example.onepick.data.ChatGptContainer
import com.example.onepick.data.DefaultChatGptContainer
import com.example.onepick.data.DefaultServerContainer
import com.example.onepick.data.DefaultTmdbContainer
import com.example.onepick.data.ServerContainer
import com.example.onepick.data.TmdbContainer

class OnePickApplication : Application() {
    lateinit var chatGptContainer: ChatGptContainer
    lateinit var tmdbContainer: TmdbContainer
    lateinit var serverContainer: ServerContainer
    override fun onCreate() {
        super.onCreate()
        chatGptContainer = DefaultChatGptContainer()
        tmdbContainer = DefaultTmdbContainer()
        serverContainer = DefaultServerContainer()
    }
}