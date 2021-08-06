package com.example.notesapp

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class NoteCreationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_creation)

        val cancelBtn: Button = findViewById(R.id.buttonCancel)
        cancelBtn.setOnClickListener {
            finish()
        }

        val createNoteBtn: Button = findViewById(R.id.buttonCreate)
        createNoteBtn.setOnClickListener {
            val editTitle = findViewById<EditText>(R.id.editTitle)
            val title = editTitle.text.toString()

            val editDescription = findViewById<EditText>(R.id.editDescription)
            val description = editDescription.text.toString()

            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())

            val note = Note(title = title, description = description, date = currentDate)

            intent.putExtra("new_note", note as Serializable)
            setResult(Activity.RESULT_OK, intent)

            finish()
        }
    }
}