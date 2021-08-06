package com.example.notesapp

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.viewmodels.NoteListViewModel
import com.example.notesapp.viewmodels.NoteListViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class NoteDataModifiedActivity : AppCompatActivity() {
    private val noteViewModel: NoteListViewModel by viewModels {
        NoteListViewModelFactory((application as NoteApplication).database.noteDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_data_modified)

        val noteData = intent.getSerializableExtra("note_data") as Note

        val titleText = findViewById<EditText>(R.id.titleEditText).apply {
            text = Editable.Factory.getInstance().newEditable(noteData.title)
        }
        makeEditTextNotEditable(titleText)

        val descriptionText = findViewById<EditText>(R.id.descriptionEditText).apply {
            text = Editable.Factory.getInstance().newEditable(noteData.description)
        }
        makeEditTextNotEditable(descriptionText)

        findViewById<TextView>(R.id.dateTextView).apply {
            text = Editable.Factory.getInstance().newEditable(noteData.date)
        }

        val confirmBtn: ImageButton = findViewById(R.id.confirmImageButton)
        confirmBtn.setOnClickListener {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())

            noteViewModel.update(noteData.id, titleText.text.toString(), descriptionText.text.toString(), currentDate)
            finish()
        }

        val deleteBtn: ImageButton = findViewById(R.id.deleteImageButton)
        deleteBtn.setOnClickListener {
            noteViewModel.delete(noteData.id, noteData.title, noteData.description, noteData.date)

            finish()
        }

        val editBtn: ImageButton = findViewById(R.id.editImageButton)
        editBtn.setOnClickListener {
            confirmBtn.visibility = View.VISIBLE
            makeEditTextEditable(titleText)
            makeEditTextEditable(descriptionText)
        }
    }

    private fun makeEditTextNotEditable(editText: EditText) {
        editText.setFocusable(false)
        editText.setFocusableInTouchMode(false)
        editText.setClickable(false)
        //editText.setBackground(null)
    }

    private fun makeEditTextEditable(editText: EditText) {
        editText.setFocusable(true)
        editText.setFocusableInTouchMode(true)
        editText.setClickable(true)
    }


}