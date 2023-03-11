package com.notes.notes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey
    val code: Int,
    var title: String,
    var text: String,
    var lastUpdate: String,
    var color: String
)
