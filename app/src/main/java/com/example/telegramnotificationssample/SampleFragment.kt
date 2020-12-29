package com.example.telegramnotificationssample

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.telegramnotificationssample.telegramhack.NotificationCenter

class SampleFragment : BaseFragment(R.layout.fragment_sample) {

    override fun inject() {
        // TODO: set anything
    }

    lateinit var editText: EditText
    lateinit var sendNotificationButton: Button
    lateinit var sendNotificationButton2: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editText = view.findViewById(R.id.edit_text)
        sendNotificationButton = view.findViewById(R.id.send_notification_button)
        sendNotificationButton2 = view.findViewById(R.id.send_notification_button_2)

        sendNotificationButton.setOnClickListener {
            NotificationCenter.getInstance()
                .post(NotificationCenter.event1, "Введённый в фрагменте текст: ${editText.text}")
        }


        sendNotificationButton2.setOnClickListener {
            NotificationCenter.getInstance()
                .post(NotificationCenter.event2, "EditText пустой", editText.text.isNullOrEmpty())
        }
    }



}