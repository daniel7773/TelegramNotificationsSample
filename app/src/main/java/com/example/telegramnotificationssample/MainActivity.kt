package com.example.telegramnotificationssample

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.telegramnotificationssample.base.BaseActivity
import com.example.telegramnotificationssample.telegramhack.NotificationCenter

/**
 * @property MainActivity receives notifications from sample fragment
 */
class MainActivity: BaseActivity(), NotificationCenter.NotificationCenterDelegate {

    private val TAG = this.javaClass.simpleName

    lateinit var eventTypeTextView: TextView
    lateinit var eventMessageTextView: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        eventTypeTextView = findViewById(R.id.event_type_text_view)
        eventMessageTextView = findViewById(R.id.event_message_text_view)

        observe(
            this, NotificationCenter.event1, NotificationCenter.event2
        )
    }

    override fun didReceivedNotification(id: Int, vararg args: Any?) {
        var eventLogMessage = ""
        var eventType = ""
        var eventMessage = ""

        if(id == NotificationCenter.event1) {
            eventLogMessage ="event1 received."
            eventType = "event1"
            eventMessage = args[0] as String // ага, тут костыль небольшой, нужно следить за тем чтобы у нужного события был аргумент

        } else if (id == NotificationCenter.event2) {
            eventLogMessage ="event2 received."
            eventType = "event2"

            eventMessage = "${args[0] as String} ${args[1] as Boolean}" // ага, тут костыль небольшой, нужно следить за тем чтобы у нужного события был аргумент

        } else if(id == NotificationCenter.event3) { // it will not come because we not subscribed on it in onCreate observe method
            // unreachable

        } else {
            eventLogMessage ="unknown received."
            eventType = "unknown"
            eventMessage = "unknown"
        }

        eventTypeTextView.text = eventType
        eventMessageTextView.text = eventMessage

        Log.d(TAG, eventLogMessage)
    }
}