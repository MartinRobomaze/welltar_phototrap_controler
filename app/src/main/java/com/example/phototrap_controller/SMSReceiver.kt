package com.example.phototrap_controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.telephony.SmsMessage
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

public data class SMS(val message: String, val phoneNumber: String)

class SmsReceiver : BroadcastReceiver() {
    var timestamp = LocalDateTime.MIN
    var number = ""
    var message = ""

    var newMessage = false

    override fun onReceive(context: Context?, intent: Intent?) {
        val extras = intent?.extras

        if (extras != null) {
            val sms = extras.get("pdus") as Array<Any>

            for (i in sms.indices) {
                val format = extras.getString("format")

                val smsMessage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    SmsMessage.createFromPdu(sms[i] as ByteArray, format)
                } else {
                    SmsMessage.createFromPdu(sms[i] as ByteArray)
                }
                timestamp = LocalDateTime.now()

                number = smsMessage.originatingAddress.toString()
                message = smsMessage.messageBody
                newMessage = true
            }
        }
    }

    fun getSMS(): SMS {
        newMessage = false

        return SMS(message = message, phoneNumber = number)
    }
}
