package com.example.notesapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.viewmodels.NoteListViewModel
import com.example.notesapp.viewmodels.NoteListViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.Serializable


class MainActivity : AppCompatActivity() {
    private var isListMode: Boolean = true
    private lateinit var recyclerView: RecyclerView
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private val noteViewModel: NoteListViewModel by viewModels {
        NoteListViewModelFactory((application as NoteApplication).database.noteDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        loadRecyclerViewMode()

        setupButtons()

        setupAdapter()
    }

    private fun setupAdapter() {
        val adapter = NoteAdapter {
            val note = Note(it.id, it.noteTitle, it.noteDescription, it.noteDate)
            setupAdaptorConstructor(note)
        }

        recyclerView.adapter = adapter

        lifecycle.coroutineScope.launch {
            noteViewModel.allNotes.collect {
                adapter.submitList(it)
            }
        }

        resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val note = data?.getSerializableExtra("new_note") as Note

                noteViewModel.insert(note.title, note.description, note.date)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupAdaptorConstructor(note: Note) {
        val intent = Intent(this, NoteDataModifiedActivity::class.java).apply {
            putExtra("note_data", note as Serializable)
        }

        startActivity(intent)
    }

    private fun setupButtons() {
        val createBtn = findViewById<FloatingActionButton>(R.id.floatingActionButtonCreate)
        createBtn.setOnClickListener { onCreateButtonClicked() }

        val changeModeBtn = findViewById<ImageButton>(R.id.changeModeButton)
        changeModeBtn.setOnClickListener { listMode(changeModeBtn) }
    }

    private fun onCreateButtonClicked() {
        val intent = Intent(this, NoteCreationActivity::class.java)
        resultLauncher.launch(intent)
    }

    private fun listMode(changeModeBtn: ImageButton) {
        if (isListMode) {
            isListMode = false
            recyclerView.layoutManager = GridLayoutManager(this, 2)

            changeModeBtn.setImageResource(R.drawable.ic_baseline_view_list_24)
        } else {
            isListMode = true
            recyclerView.layoutManager = GridLayoutManager(this, 1)

            changeModeBtn.setImageResource(R.drawable.ic_baseline_grid_on_24)
        }
    }

    private fun firstListMode() {
        val changeModeBtn = findViewById<ImageButton>(R.id.changeModeButton)
        if (isListMode) {
            recyclerView.layoutManager = GridLayoutManager(this, 1)

            changeModeBtn.setImageResource(R.drawable.ic_baseline_grid_on_24)
        } else {
            recyclerView.layoutManager = GridLayoutManager(this, 2)

            changeModeBtn.setImageResource(R.drawable.ic_baseline_view_list_24)
        }
    }

    private fun loadRecyclerViewMode() {
        val preferences = this.getSharedPreferences("RecyclerView_mode", Context.MODE_PRIVATE)
        isListMode = preferences.getBoolean("MODE_STATE", true)

        firstListMode()
    }

    private fun saveRecyclerViewMode() {
        val preferences = this.getSharedPreferences("RecyclerView_mode", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.apply {
            putBoolean("MODE_STATE", isListMode)
        }.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveRecyclerViewMode()
    }
}