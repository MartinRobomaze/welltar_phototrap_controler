package com.example.phototrap_controller

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase


class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME (ID INTEGER PRIMARY KEY " +
                "AUTOINCREMENT,NAME TEXT,PHONE_NUMBER TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(name: String, phoneNumber: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, phoneNumber)
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun updateData(previousName: String, name: String, phoneNumber: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, phoneNumber)
        db.update(TABLE_NAME, contentValues, "NAME = ?", arrayOf(previousName))
        return true
    }

    fun deleteData(name : String) : Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME,"NAME = ?", arrayOf(name))
    }

    fun getPhoneNumber(name: String) : Cursor {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT $COL_3 FROM $TABLE_NAME WHERE $COL_2 = $name", null)
        return res
    }

    fun getID(name: String) : Cursor {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT $COL_1 FROM $TABLE_NAME WHERE $COL_2 = $name", null)
        return res
    }

    val allData : Cursor
        get() {
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
            return res
        }

    val allNames : Cursor
    get() {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT $COL_2 FROM $TABLE_NAME", null)
        return res
    }


    companion object {
        val DATABASE_NAME = "phototraps.db"
        val TABLE_NAME = "photoraps_table"
        val COL_1 = "ID"
        val COL_2 = "NAME"
        val COL_3 = "PHONE_NUMBER"
    }
}
