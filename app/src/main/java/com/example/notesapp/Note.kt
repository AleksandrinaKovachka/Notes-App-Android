package com.example.notesapp

import java.io.Serializable

@kotlinx.serialization.Serializable
data class Note(val id: Int = 0, val title: String, val description: String, val date: String) : Serializable