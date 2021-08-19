package com.example.notesapp

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.database.NoteData
import com.example.notesapp.viewmodels.NoteListViewModel
import com.example.notesapp.viewmodels.NoteListViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class NoteDataModifiedActivity : AppCompatActivity() {
    private lateinit var noteData: Note
    private val noteViewModel: NoteListViewModel by viewModels {
        NoteListViewModelFactory((application as NoteApplication).database.noteDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_data_modified)

        noteData = intent.getSerializableExtra("note_data") as Note

        setupTextViews()
        setupButtons()
    }

    private fun setupButtons() {
        val titleText = findViewById<EditText>(R.id.titleEditText)
        val descriptionText = findViewById<EditText>(R.id.descriptionEditText)

        val confirmBtn: ImageButton = findViewById(R.id.confirmImageButton)
        confirmBtn.setOnClickListener { onConfirmBtnClicked(titleText.text.toString(), descriptionText.text.toString()) }

        val deleteBtn: ImageButton = findViewById(R.id.deleteImageButton)
        deleteBtn.setOnClickListener { onDeleteBtnClicked() }

        val editBtn: ImageButton = findViewById(R.id.editImageButton)
        editBtn.setOnClickListener { onEditBtnClicked(titleText, descriptionText) }
    }

    private fun onConfirmBtnClicked(title: String, description: String) {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        noteViewModel.update(noteData.id, title, description, currentDate)
        finish()
    }

    private fun onDeleteBtnClicked() {
        noteViewModel.delete(noteData.id, noteData.title, noteData.description, noteData.date)

        finish()
    }

    private fun onEditBtnClicked(titleText: EditText, descriptionText: EditText) {
        findViewById<ImageButton>(R.id.confirmImageButton).visibility = View.VISIBLE
        makeEditTextEditable(titleText)
        makeEditTextEditable(descriptionText)
    }

    private fun setupTextViews() {
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
    }

    private fun makeEditTextNotEditable(editText: EditText) {
        editText.isFocusable = false
        editText.isFocusableInTouchMode = false
        editText.isClickable = false
        //editText.setBackground(null)
    }

    private fun makeEditTextEditable(editText: EditText) {
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.isClickable = true
    }


}