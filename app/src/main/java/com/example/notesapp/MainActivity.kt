package com.example.notesapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.Serializable


class MainActivity : AppCompatActivity(), CellClickListener {
    private var notes = ArrayList<Note>()
    private lateinit var adapter: CustomAdapter
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                notes.add(data?.getSerializableExtra("new_note") as Note)
                adapter.notifyDataSetChanged()
            }
        }

        val createBtn = findViewById<FloatingActionButton>(R.id.floatingActionButtonCreate)
        createBtn.setOnClickListener {
            //Toast.makeText(this@MainActivity, "FAB is clicked...", Toast.LENGTH_LONG).show()
            val intent = Intent(this, NoteCreationActivity::class.java)
            resultLauncher.launch(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        //recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false) //applicationContext

        notes = readData() as ArrayList<Note>

        adapter = CustomAdapter(this, notes, this)

        recyclerView.adapter = adapter
    }

    override fun onCellClickListener(data: Note) {
        val intent = Intent(this, NoteDataActivity::class.java).apply {
            putExtra("note_data", data as Serializable)
        }

        startActivity(intent)
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

interface CellClickListener {
    fun onCellClickListener(data: Note)
}