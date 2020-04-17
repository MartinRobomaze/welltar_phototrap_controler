package com.example.phototrap_controller

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

data class PhototrapsData(var phototrapsNames: ArrayList<String>, val phototrapsPhoneNumbers: ArrayList<String>)

class PhototrapMenu : AppCompatActivity(), OnItemClickListener {

    val db = DatabaseHelper(this)
    // Initializing an empty ArrayList to be filled with animals=

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phototrap_menu)

        val add = findViewById<FloatingActionButton>(R.id.add)
        add.setOnClickListener {
            startActivity(Intent(this, AddPhototrap::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        val phototrapsData = getDbData()
        val phototrapsList = findViewById<RecyclerView>(R.id.phototraps_list)
        phototrapsList.layoutManager = LinearLayoutManager(this)

        phototrapsList.adapter = PhototrapsDataAdapter(phototrapsData.phototrapsNames, phototrapsData.phototrapsPhoneNumbers, this, this)
    }

    override fun onEditButtonClick(name: String, phoneNumber: String) {
        intent = Intent(this, EditPhototrapActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("phone_number", phoneNumber)

        startActivity(intent)
    }

    override fun onChooseButtonClick(name: String, phoneNumber: String) {
        val data = Intent()
        data.putExtra("name", name)
        data.putExtra("phone_number", phoneNumber)

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun getDbData(): PhototrapsData {
        val res = db.allData
        val phototrapsData = PhototrapsData(ArrayList(), ArrayList())
        while (res.moveToNext()) {
            phototrapsData.phototrapsNames.add(res.getString(1))
            phototrapsData.phototrapsPhoneNumbers.add(res.getString(2))
        }

        return phototrapsData
    }
}
