package com.example.phototrap_controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText

class EditPhototrapActivity : AppCompatActivity() {
    private val db = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val name = intent.getStringExtra("name")
        val phoneNumber = intent.getStringExtra("phone_number")

        val nameInput = findViewById<EditText>(R.id.phototrap_name)
        val phoneNumberInput = findViewById<EditText>(R.id.phototrap_phone_number)

        name?.let {
            nameInput.setText(it)
        }
        phoneNumber?.let {
            phoneNumberInput.setText(it)
        }

        val saveButton = findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener {
            if (nameInput != null || phoneNumberInput != null) {
                if (name != null) {
                    save(name, nameInput.text.toString(), phoneNumberInput.text.toString())
                }
            }
        }

        val deleteButton = findViewById<Button>(R.id.delete_button)
        deleteButton.setOnClickListener {
            if (nameInput != null || phoneNumberInput != null) {
                if (name != null) {
                    db.deleteData(name)
                    this.finish()
                }
            }
        }
    }

    fun save(previousName: String, name: String, phoneNumber: String) {
        db.updateData(previousName, name, phoneNumber)
        this.finish()
    }
}
