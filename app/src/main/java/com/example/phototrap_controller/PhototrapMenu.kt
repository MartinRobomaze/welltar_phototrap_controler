package com.example.phototrap_controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PhototrapMenu : AppCompatActivity() {

    val db = DatabaseHelper(this)
    // Initializing an empty ArrayList to be filled with animals
    val phototraps: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phototrap_menu)

        val add = findViewById<FloatingActionButton>(R.id.add)
        add.setOnClickListener {
            startActivity(Intent(this, AddPhototrap::class.java))
        }

        // Creates a vertical Layout Manager
        getDbNames()
        val phototrapsList = findViewById<RecyclerView>(R.id.phototraps_list)
        phototrapsList.layoutManager = LinearLayoutManager(this)

        // Access the RecyclerView Adapter and load the data into it
        phototrapsList.adapter = PhototrapsDataAdapter(phototraps, this)

        val inputButton = findViewById<Button>(R.id.phototrap_item)
        inputButton.setOnLongClickListener {
            intent = Intent(this, EditPhototrapActivity::class.java)
            intent.putExtra("name", inputButton.text.toString())
            return@setOnLongClickListener true
        }
    }

    fun getDbNames() {
        val res = db.allNames

        while (res.moveToNext()) {
            phototraps.add(res.getString(0))
        }
    }
}
