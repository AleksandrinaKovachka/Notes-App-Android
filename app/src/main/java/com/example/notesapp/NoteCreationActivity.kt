package com.example.notesapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
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
    }

    fun createNote(view: View) {
        Log.i("Activity", "create note button")
        val editTitle = findViewById<EditText>(R.id.editTitle)
        val title = editTitle.text.toString()

        val editDescription = findViewById<EditText>(R.id.editDescription)
        val description = editDescription.text.toString()

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        val note = Note(title, description, currentDate)

        //save data
        saveNote(note)

        intent.putExtra("new_note", note as Serializable)
        setResult(1, intent)

        finish()
    }

    fun cancelNote(view: View) {
        finish()
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