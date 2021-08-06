package com.example.notesapp

import android.app.Application
import com.example.notesapp.database.AppDatabase

class NoteApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
}
