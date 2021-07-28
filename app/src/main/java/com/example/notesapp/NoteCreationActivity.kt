package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

class NoteCreationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_creation)
    }

    fun createNote(view: View) {
        val editTitle = findViewById<EditText>(R.id.editTitle)
        val title = editTitle.text.toString()

        val editDescription = findViewById<EditText>(R.id.editDescription)
        val description = editDescription.text.toString()

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        val note = Note(title, description, currentDate)

        //save data

    }

    fun cancelNote(view: View) {
        finish()
    }
}