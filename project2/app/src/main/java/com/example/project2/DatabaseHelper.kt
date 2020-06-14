package com.example.project2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(
    context,
    NAME,
    null,
    VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        val sql = ("create table if not exists noteData("
                + " _id integer PRIMARY KEY autoincrement, "
                + " title text, "
                + " content text, "
                + " location text, "
                + " time text)")
        db.execSQL(sql)
    }

    override fun onOpen(db: SQLiteDatabase) {}
    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        if (newVersion > 1) {
            db.execSQL("DROP TABLE IF EXISTS noteData")
        }
    }

    companion object {
        var NAME = "notepad.db"
        var VERSION = 1
    }
}