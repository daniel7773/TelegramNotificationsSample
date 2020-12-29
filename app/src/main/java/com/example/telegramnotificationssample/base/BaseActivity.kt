package com.example.telegramnotificationssample.base

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import com.example.telegramnotificationssample.telegramhack.NotificationCenter
import com.example.telegramnotificationssample.telegramhack.NotificationCenterLifecycleAware
import com.example.telegramnotificationssample.telegramhack.ObserversManager
import com.example.telegramnotificationssample.telegramhack.ObserversManagerProvider

open class BaseActivity : AppCompatActivity(), NotificationCenterLifecycleAware,
    ObserversManagerProvider {

    private val observersManager: ObserversManager = ObserversManager()

    override fun observe(observer: NotificationCenter.NotificationCenterDelegate, vararg ids: Int) {
        if(ids.isNotEmpty()) {
            val arr = IntArray(ids.size)
            var i = 0
            ids.forEach {
                arr[i] = it
                i += 1
            }
            observersManager.observe(observer, arr)
        }
    }


    override fun removeObservation(observer: NotificationCenter.NotificationCenterDelegate?, id: Int) {
        observersManager.removeObserver(observer, id)
    }

    override fun getObserversManager(): ObserversManager {
        return observersManager
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //AndroidUtilities.checkDisplaySize(this, newConfig)
    }
}