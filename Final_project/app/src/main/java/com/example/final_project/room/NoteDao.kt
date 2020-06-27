package com.example.final_project.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.*
import com.example.final_project.room.NoteEntity

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(vararg notes: NoteEntity)

    @Query("SELECT * FROM NoteEntity WHERE noteIdx = :noteIdx ORDER BY noteTime DESC")
    fun selectNote(noteIdx: Long): NoteEntity?

    @Query("SELECT * FROM NoteEntity ORDER BY noteTime DESC")
    fun selectNotes(): LiveData<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHERE noteIdx = :noteIdx ORDER BY noteTime DESC")
    fun selectLiveNote(noteIdx: Long): LiveData<NoteEntity>

    @Update
    fun updateNote(vararg notes: NoteEntity)

    @Delete
    fun deleteNotes(vararg note: NoteEntity)

    @Query("DELETE FROM NoteEntity WHERE noteIdx = :noteIdx")
    fun deleteNote(noteIdx: Long)

    @Query("SELECT * FROM NoteEntity WHERE (noteTitle LIKE :search) || (noteContent LIKE :search) || (noteLocation LIKE :search) ORDER BY noteTime DESC")
    fun searchNotes(search: String):LiveData<List<NoteEntity>>


}