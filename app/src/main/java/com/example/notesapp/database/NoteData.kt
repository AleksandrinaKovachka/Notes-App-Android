package com.example.notesapp.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_data")
data class NoteData (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @NonNull @ColumnInfo(name = "note_title") val noteTitle: String,
    @NonNull @ColumnInfo(name = "note_description") val noteDescription: String,
    @NonNull @ColumnInfo(name = "note_date") val noteDate: String
)