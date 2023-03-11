package com.notes.notes.model

import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM note_table ORDER BY code DESC")
    suspend fun getAllNotes(): List<Note>
    
    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}