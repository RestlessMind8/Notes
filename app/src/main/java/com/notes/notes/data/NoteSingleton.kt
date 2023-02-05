package com.notes.notes.data

import com.notes.notes.model.Note

object NoteSingleton {
    private val notes = mutableListOf<Note>()

    fun addNote(note: Note){
        this.notes.add(this.notes.size, note)
    }

}