package com.example.notesapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.database.NoteData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
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

            val note = Note(title, description, currentDate) //Note(title, description, currentDate)

            //save data
            //saveNote(note)

            intent.putExtra("new_note", note as Serializable)
            setResult(Activity.RESULT_OK, intent)

            finish()
        }
    }

    private fun saveNote(note: Note) {
        //get all saved data
        val notes = readData()

        //add new note
        notes.add(note)

        //save all data
        val jsonList = Json.encodeToString(notes)

        this.openFileOutput("SavedUserNotes", Context.MODE_PRIVATE).use {
            it.write(jsonList.toByteArray())
        }
    }
    private fun readData(): MutableList<Note> {
        val readFile = File(this.filesDir, "SavedUserNotes")
        if (readFile.exists()) {
            val bytesData = readFile.readBytes()
            val stringData = String(bytesData)

            return Json.decodeFromString(stringData)
        }

        return mutableListOf()
    }
}