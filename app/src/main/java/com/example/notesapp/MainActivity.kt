package com.example.notesapp

import android.app.Activity
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

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private val noteViewModel: NoteListViewModel by viewModels {
        NoteListViewModelFactory((application as NoteApplication).database.noteDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        val adapter = NoteAdapter {
            val note = Note(it.id, it.noteTitle, it.noteDescription, it.noteDate)

            val intent = Intent(this, NoteDataModifiedActivity::class.java).apply {
                putExtra("note_data", note as Serializable)
            }

            startActivity(intent)
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

        val createBtn = findViewById<FloatingActionButton>(R.id.floatingActionButtonCreate)
        createBtn.setOnClickListener {
            val intent = Intent(this, NoteCreationActivity::class.java)
            resultLauncher.launch(intent)
        }

        val changeModeBtn = findViewById<ImageButton>(R.id.changeModeButton)
        changeModeBtn.setOnClickListener {
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
    }
}