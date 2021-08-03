package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class NoteDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_data)

        Log.i("Activity:", "note data")

        val noteData = intent.getSerializableExtra("note_data") as Note

        findViewById<TextView>(R.id.textViewTitle).apply {
            text = noteData.title
        }

        findViewById<TextView>(R.id.textViewDescription).apply {
            text = noteData.description
        }

        findViewById<TextView>(R.id.textViewDate).apply {
            text = noteData.date
        }
    }
}