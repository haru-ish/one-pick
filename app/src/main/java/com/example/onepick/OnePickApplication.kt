package com.example.onepick

import android.app.Application
import com.example.onepick.data.ChatGptContainer
import com.example.onepick.data.DefaultChatGptContainer
import com.example.onepick.network.SharedViewModel

class OnePickApplication : Application() {

    lateinit var chatGptContainer: ChatGptContainer
    lateinit var sharedViewModel: SharedViewModel

    // SharedViewModelのインスタンスを作成
//    val sharedViewModel: SharedViewModel by lazy {
//        SharedViewModel()
//    }

    companion object {
        private lateinit var instance: OnePickApplication

        // アプリケーションクラスのインスタンスにアクセスするためのメソッド
        fun getInstance(): OnePickApplication {
            return instance
        }

        // SharedViewModelへのアクセスメソッド
        fun getSharedViewModel(): SharedViewModel {
            return getInstance().sharedViewModel
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        chatGptContainer = DefaultChatGptContainer()
        sharedViewModel = SharedViewModel()
    }

//    override fun onCreate() {
//        super.onCreate()
//        instance = this
//    }
}