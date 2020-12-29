package com.example.telegramnotificationssample

import android.app.Application

open class BaseApplication : Application(){


    private lateinit var instance: Application

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
        instance = this
    }

    open fun initAppComponent(){
       // TODO: can user dagger here
    }

    open fun getInstance(): Application {
        if(instance == null) {
            instance = this
        }
        return instance
    }


}