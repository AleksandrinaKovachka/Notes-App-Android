package com.example.notesapp

import android.app.Application
import com.example.notesapp.database.AppDatabase
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.SupervisorJob

class NoteApplication : Application() {
    //private val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(this) }
}
