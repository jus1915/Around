package com.example.final_project.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NoteEntity")
data class NoteEntity (
    @PrimaryKey(autoGenerate = true)
    var noteIdx: Long? = null,
    var noteTitle: String,
    var noteContent: String,
    var noteImage: String?,
    var noteLocation: String,
    var noteTime: String
)