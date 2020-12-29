package com.example.telegramnotificationssample

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.telegramnotificationssample.telegramhack.NotificationCenter
import com.example.telegramnotificationssample.telegramhack.NotificationCenterLifecycleAware
import com.example.telegramnotificationssample.telegramhack.ObserversManager
import com.example.telegramnotificationssample.telegramhack.ObserversManagerProvider

abstract class BaseFragment
constructor(
    private @LayoutRes val layoutRes: Int
): Fragment(), NotificationCenterLifecycleAware, ObserversManagerProvider {

    val TAG = this.javaClass.simpleName

    private val _observersManager: ObserversManager = ObserversManager()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }
    abstract fun inject()

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

    override fun removeObservation(observer: NotificationCenter.NotificationCenterDelegate?, id: Int) {
        _observersManager.removeObserver(observer, id)
    }

    override fun getObserversManager(): ObserversManager {
        return _observersManager
    }

    override fun observe(observer: NotificationCenter.NotificationCenterDelegate, vararg ids: Int) {
        val arr = IntArray(ids.size)
        var i = 0
        ids.forEach {
            arr[i] = it
            i +=1
        }
        _observersManager.observe(observer, ids)
    }

    open fun onBackPressed(): Boolean { // TODO: to handle onBackPressed in nested fragment
        return false
    }
}