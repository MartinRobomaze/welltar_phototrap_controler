package com.example.phototrap_controller

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.telephony.PhoneNumberUtils
import android.telephony.SmsManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


private const val SENT = "SMS_SENT"
private const val DELIVERED = "SMS_DELIVERED"
var phoneNumber = ""
private const val SEND_SUCCESS_MESSAGE = "MMS commands executed successfully!"

class MainActivity : AppCompatActivity() {

    var status = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        val requestReceiveSMS = 2

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS),
                                                                    requestReceiveSMS)
        }

        val statusText = findViewById<TextView>(R.id.state)

        val phoneNumberInput = findViewById<EditText>(R.id.phone_number)
        val sendPhoneNumberButton = findViewById<Button>(R.id.send_phone_number)
        sendPhoneNumberButton.setOnClickListener {
            phoneNumber = PhoneNumberUtils.formatNumber(phoneNumberInput.text.toString())
            statusText.text = phoneNumber
        }

        val photoVideoSwitch = findViewById<Switch>(R.id.photo_video)
        photoVideoSwitch.setOnCheckedChangeListener { _, isChecked ->
            statusText.text = "Sprava odoslana"

            val data: String

            when (isChecked) {
                true -> data = "02"
                false -> data = "01"
            }

            sendSMS("01", data)
        }

        val numberPhotos = findViewById<EditText>(R.id.photos_number)
        val sendNumberPhotosButton = findViewById<Button>(R.id.send_photos_number)
        sendNumberPhotosButton.setOnClickListener {
            statusText.text = "Sprava odoslana"

            sendSMS("03",numberPhotos.text.toString())
        }

        val PIRSensitivity = findViewById<EditText>(R.id.PIR_sensitivity)
        val sendPIRSensitivityButton = findViewById<Button>(R.id.send_PIR_sensitivity)
        sendPIRSensitivityButton.setOnClickListener {
            statusText.text = "Sprava odoslana"

            sendSMS("05", PIRSensitivity.text.toString())
        }


        val delaySwitch = findViewById<Switch>(R.id.delay)
        delaySwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!(isChecked)) {
                statusText.text = "Sprava odoslana"

                sendSMS("10", "")
            }
        }

        val delayTimeH = findViewById<EditText>(R.id.time_h)
        val delayTimeM = findViewById<EditText>(R.id.time_m)
        val delaySendButton = findViewById<Button>(R.id.send_time)
        delaySendButton.setOnClickListener {
            statusText.text = "Sprava odoslana"

            delaySwitch.setChecked(true)
            val delayH: String
            val delayM: String

            if (delayTimeH.text.toString().toInt() < 10) {
                delayH = "0" + delayTimeH.text.toString()
            }
            else {
                delayH = delayTimeH.text.toString()
            }

            if (delayTimeM.text.toString().toInt() < 10) {
                delayM = "0" + delayTimeM.text.toString()
            }
            else {
                delayM = delayTimeM.text.toString()
            }
            sendSMS("10", delayH + delayM + "00")
        }

        val videoLength = findViewById<EditText>(R.id.video_length)
        val sendVideoLengthButton = findViewById<Button>(R.id.send_video_length)
        sendVideoLengthButton.setOnClickListener {
            statusText.text = "Sprava odoslana"

            sendSMS("18", videoLength.text.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
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
