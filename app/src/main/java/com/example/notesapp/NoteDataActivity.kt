package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.TextView

class NoteDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_data)

        val noteData = intent.getSerializableExtra("note_data") as Note

        val textViewTitle = findViewById<TextView>(R.id.textViewTitle).apply {
            text = noteData.title
        }

        val textViewDescription = findViewById<TextView>(R.id.textViewDescription).apply {
            text = noteData.description
        }

        val textViewDate = findViewById<TextView>(R.id.textViewDate).apply {
            text = noteData.date
        }
    }
}