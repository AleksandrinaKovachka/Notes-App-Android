package com.example.notesapp.repository

import androidx.annotation.WorkerThread
import com.example.notesapp.database.NoteDao
import com.example.notesapp.database.NoteData
import kotlinx.coroutines.flow.Flow

class NoteRepository (private val noteDao: NoteDao) {

    val allNotes: Flow<List<NoteData>> = noteDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(noteData: NoteData) {
        noteDao.insertToRoomDatabase(noteData)
    }
}
