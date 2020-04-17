package com.example.phototrap_controller

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_layout.view.*
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class PhototrapsDataAdapter(val names : ArrayList<String>, val phoneNumbers: java.util.ArrayList<String>,
                            val context: Context, val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return names.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.phototrapName.text = names[position]
        holder.phototrapPhoneNumber.text = phoneNumbers[position]
        holder.bind(itemClickListener)
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val phototrapName: TextView = view.name
    val phototrapPhoneNumber: TextView = view.phone_number
    val editButton: Button = view.edit
    val setButton: Button = view.set

    fun bind(clickListener: OnItemClickListener) {
        editButton.setOnClickListener {
            clickListener.onEditButtonClick(phototrapName.text.toString(), phototrapPhoneNumber.text.toString())
        }

        setButton.setOnClickListener {
            clickListener.onChooseButtonClick(phototrapName.text.toString(), phototrapPhoneNumber.text.toString())
        }
    }
}

interface OnItemClickListener {
    fun onEditButtonClick(name: String, phoneNumber: String)
    fun onChooseButtonClick(name: String, phoneNumber: String)
}