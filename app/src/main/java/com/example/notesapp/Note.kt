package com.example.notesapp

import java.io.Serializable

data class Note(val title: String, val description: String, val date: String) : Serializable