package com.example.final_project.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.final_project.room.NoteDao
import com.example.final_project.room.NoteEntity

@Database(entities = arrayOf(NoteEntity::class),version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    companion object {
        private var database: AppDatabase? = null

        private const val ROOM_DB = "note.db"

        fun getDatabase(context: Context): AppDatabase {
            if(database == null){
                database = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    ROOM_DB
                ).fallbackToDestructiveMigration().build()
            }
            return database!!
        }
    }

}