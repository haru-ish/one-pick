package com.example.onepick

import android.app.Application
import com.example.onepick.ui.screens.SharedViewModel

class OnePickApplication : Application() {

    // SharedViewModelのインスタンスを作成
    val sharedViewModel: SharedViewModel by lazy {
        SharedViewModel()
    }

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
    }
}