package com.example.phototrap_controller

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import java.time.LocalTime
import kotlin.IllegalArgumentException

private const val SENT = "SMS_SENT"
private const val DELIVERED = "SMS_DELIVERED"

class PhototrapInterface(name: String, phoneNumber: String, applicationContext: Context) {
    val applicationContext = applicationContext
    val phoneNumber = phoneNumber

    public fun setCaptureMode(mode: String) {
        if (mode == "photo") {
            sendSMS("01", "1")
        }
        else if (mode == "video") {
            sendSMS("01", "2")
        }
    }

    public fun setPhotoResolution(resolution: Int) {
        if (resolution == 12) {
            sendSMS("02", "1")
        }
        else if (resolution == 8) {
            sendSMS("02", "2")
        }
        else if (resolution == 5) {
            sendSMS("02", "3")
        }

        else {
            throw IllegalArgumentException("Photo resolution has to be 5, 8, or 12 MPx.")
        }
    }

    public fun setNumberPhotos(n: Int) {
        if (n in 1..7) {
            sendSMS("03", n.toString())
        }
        else {
            throw IllegalArgumentException("Number of photos has to be from 1 to 7.")
        }
    }

    public fun setContinualPhoto(time: LocalTime) {
        val data = time.hour.toString() + time.minute.toString() + time.second.toString()

        sendSMS("04", data)
    }

    public fun setPIRSensitivity(sensitivity: Int) {
        if (sensitivity in 1..3) {
            sendSMS("05", sensitivity.toString())
        }
        else {
            throw IllegalArgumentException("PIR sensitivity has to be from 1 to 3.")
        }
    }

    public fun setDigitalZoom(zoom: Int) {
       if (zoom in 1..4) {
           sendSMS("06", zoom.toString())
       }
        else {
           throw IllegalArgumentException("Zoom has to be between 1 and 4.")
       }
    }

    public fun setPhoneNumber(index: Int, phoneNumber: String) {
        val indexAscii = 92 + index
        val indexLetter: Char

        indexLetter = indexAscii.toChar()
        sendSMS("07$indexLetter", phoneNumber)
    }

    public fun setEmail(index: Int, email: String) {
        val indexAscii = 92 + index
        val indexLetter: Char

        indexLetter = indexAscii.toChar()
        sendSMS("08$indexLetter", email)
    }

    public fun setMaximaNumberPhotos(n: Int) {
        sendSMS("09", n.toString())
    }

    public fun setDelay(delay: LocalTime?) {
        var data = ""

        if (delay != null) {
            data = delay.hour.toString() + delay.minute.toString() + delay.second.toString()
        }

        sendSMS("10", data)
    }

    public fun setTimer(tStart: LocalTime?, tEnd: LocalTime?) {
        var data = ""

        if (tStart != null && tEnd != null) {
            data = "${tStart.hour}${tStart.minute}${tStart.second}-${tEnd.hour}${tEnd.minute}${tEnd.second}"
        }

        sendSMS("11", data)
    }

    public fun makePhoto() {
        sendSMS("12", "")
    }

    public fun setGPRSMode(mode: String) {
        if (mode == "standby") {
            sendSMS("13", "1")
        }
        else if (mode == "DC") {
            sendSMS("13", "2")
        }
        else {
            throw IllegalArgumentException("Only suppported modes are standby or DC power")
        }
    }

    public fun setTXTCard(on: Boolean) {
        if (on) {
            sendSMS("14", "")
        }
        else {
            sendSMS("14", "1")
        }
    }

    public fun sendTXTCard() {
        sendSMS("15", "1")
    }

    public fun setName(name: String) {
        if (name.length == 4) {
            sendSMS("16", name)
        }
        else {
            throw IllegalArgumentException("Name has to be 4 characters long")
        }
    }

    public fun setVideoQuality(quality: String) {
        var data = ""
        if (quality == "720P") {
            data = "1"
        }
        else if (quality == "D1") {
            data = "2"
        }
        else if (quality == "CIF") {
            data = "3"
        }
        else {
            throw IllegalArgumentException("Supported options of video quality are 720P, D1 and CIF")
        }

        sendSMS("17", data)
    }

    public fun setVideoLength(length: Int) {
        if (length in 5..60) {
            sendSMS("18", length.toString())
        }
        else {
            throw IllegalArgumentException("Supported video length is from 5 to 60 seconds")
        }
    }

    private fun sendSMS(command: String, data: String) {
        val message = "$command*$data#"

        val piSent = PendingIntent.getBroadcast(
            applicationContext,
            0, Intent(SENT), 0
        )
        val piDelivered = PendingIntent.getBroadcast(
            applicationContext,
            0, Intent(DELIVERED), 0
        )
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(
            phoneNumber, null, message,
            piSent, piDelivered
        )
    }
}