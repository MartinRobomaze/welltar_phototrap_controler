package com.example.phototrap_controller

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


private const val SENT = "SMS_SENT"
private const val DELIVERED = "SMS_DELIVERED"
private const val SEND_SUCCESS_MESSAGE = "MMS commands executed successfully!"
private const val RESULT_CODE = 3

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val requestReceiveSMS = 2

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS),
                                                                    requestReceiveSMS)
        }

        val changePhototrap = findViewById<Button>(R.id.choose_phototrap)
        changePhototrap.setOnClickListener {
            startActivityForResult(Intent(this, PhototrapMenu::class.java), RESULT_CODE)
        }


    }

    override fun onResume() {
        super.onResume()
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




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == RESULT_CODE) {
            if (data != null) {
                if (data.hasExtra("name") && data.hasExtra("phone_number")) {
                    data.getStringExtra("name")?.let {
                        name = it
                    }
                    data.getStringExtra("phone_number")?.let {
                        phoneNumber = it
                    }
                }
            }
        }
    }
}
