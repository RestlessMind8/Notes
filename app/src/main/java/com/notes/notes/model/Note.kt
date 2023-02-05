package com.notes.notes.model

data class Note(
    val code: Long,
    var title: String,
    val text: String,
    var lastUpdate: String,
    var color: String
)
