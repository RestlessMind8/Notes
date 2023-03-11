package com.notes.notes.data

import com.notes.notes.model.Note

object NoteSingleton {
    private val notes = mutableListOf<Note>()
    private var code: Int = 0


    fun init(data: List<Note>){
        this.notes.addAll(data.asReversed())
        code = data.size
    }

    fun addNote(title: String, text: String, lastUpdate: String, color: String){
        this.notes.add(code, Note(code++, title, text, lastUpdate, color))
    }

    fun getNotes(): MutableList<Note>{
        return this.notes
    }

    fun updateNote(code: Int, title: String, text: String, lastUpdate: String, color: String){
        this.notes[code].title = title
        this.notes[code].text = text
        this.notes[code].lastUpdate = lastUpdate
        this.notes[code].color = color
    }

    fun getNote(code: Int): Note{
        return this.notes[code]
    }

    fun getLast(): Note{
        return this.notes.last()
    }

}