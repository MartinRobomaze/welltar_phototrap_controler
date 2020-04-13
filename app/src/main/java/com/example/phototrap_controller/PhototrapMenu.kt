package com.example.phototrap_controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PhototrapMenu : AppCompatActivity() {

    val db = DatabaseHelper(this)
    // Initializing an empty ArrayList to be filled with animals
    val phototraps: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phototrap_menu)
        db.insertData("mopslik godzila", "+222")
        db.insertData("mopslik chosila", "+222555")

        // Creates a vertical Layout Manager
        getDbNames()
        val phototrapsList = findViewById<RecyclerView>(R.id.phototraps_list)
        phototrapsList.layoutManager = LinearLayoutManager(this)

        // Access the RecyclerView Adapter and load the data into it
        phototrapsList.adapter = PhototrapsDataAdapter(phototraps, this)
    }

    fun getDbNames() {
        val res = db.allNames

        while (res.moveToNext()) {
            phototraps.add(res.getString(0))
        }
    }
}
