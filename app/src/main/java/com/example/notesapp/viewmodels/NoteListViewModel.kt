package com.example.notesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.database.NoteData
import com.example.notesapp.repository.NoteRepository
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapp.database.NoteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteListViewModel(private val noteDao: NoteDao): ViewModel() {
    val allNotes: Flow<List<NoteData>> = noteDao.getAll()

    fun insert(title: String, description: String, date: String) = viewModelScope.launch {
        noteDao.insertToRoomDatabase(NoteData(noteTitle = title, noteDescription = description, noteDate = date))
    }
}

class NoteListViewModelFactory(private val noteDao: NoteDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteListViewModel(noteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
