package com.example.notesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.database.NoteData
import com.example.notesapp.repository.NoteRepository
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NoteListViewModel(private val repository: NoteRepository): ViewModel() {
    val allNotes: LiveData<List<NoteData>> = repository.allNotes.asLiveData()

    fun insert(noteData: NoteData) = viewModelScope.launch {
        repository.insert(noteData)
    }
}

class NoteListViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
