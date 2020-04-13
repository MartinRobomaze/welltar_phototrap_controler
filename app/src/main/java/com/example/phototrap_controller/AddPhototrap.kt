package com.example.phototrap_controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class AddPhototrap : AppCompatActivity() {
    private val db = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_phototrap)

        val nameInput = findViewById<EditText>(R.id.phototrap_name)
        val phoneNumberInput = findViewById<EditText>(R.id.phototrap_phone_number)

        val addButton = findViewById<Button>(R.id.save_button)
        addButton.setOnClickListener {
            if (nameInput != null || phoneNumberInput != null) {
                db.insertData(nameInput.text.toString(), phoneNumberInput.text.toString())
                this.finish()
            }
        }
    }
}
