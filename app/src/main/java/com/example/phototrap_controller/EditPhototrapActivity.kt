package com.example.phototrap_controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EditPhototrapActivity : AppCompatActivity() {
    private val db = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val name = intent.getStringExtra("name")

        val nameInput = findViewById<EditText>(R.id.phototrap_name)
        val phoneNumberInput = findViewById<EditText>(R.id.phototrap_phone_number)

        val saveButton = findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener {
            if (nameInput != null || phoneNumberInput != null) {
                if (name != null) {
                    db.updateData(name, nameInput.text.toString(), phoneNumberInput.text.toString())
                    this.finish()
                }
            }
        }

        val deleteButton = findViewById<Button>(R.id.save_button)
        deleteButton.setOnClickListener {
            if (nameInput != null || phoneNumberInput != null) {
                if (name != null) {
                    db.deleteData(name)
                    this.finish()
                }
            }
        }
    }
}
