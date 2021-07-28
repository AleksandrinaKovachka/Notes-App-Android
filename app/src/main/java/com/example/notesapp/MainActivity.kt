package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.Adapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class MainActivity : AppCompatActivity(), CellClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        //recyclerView.adapter = Adapter(this, fetchList())

        //getting recyclerview from xml
        //val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        //adding a layoutmanager
        //recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false) //applicationContext


        //crating an arraylist to store users using the data class user
        val notes = ArrayList<Note>()

        //adding some dummy data to the list
        notes.add(Note("Test1", "Test again 1 ghsjdnkjbjbkjsbhjdcbhnlsxcn hsdjnvhjdlkjjkkd uhdbsbedjbkd", "01.01.21, 12:34"))
        notes.add(Note("Test2", "Test again 2", "01.01.21, 12:34"))
        notes.add(Note("Test3", "Test again 3", "01.01.21, 12:34"))
        notes.add(Note("Test4", "Test again 4", "01.01.21, 12:34"))

        //creating our adapter
        val adapter = CustomAdapter(this, notes, this)

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
    }

    override fun onCellClickListener(data: Note) {
        val intent = Intent(this, NoteDataActivity::class.java).apply {
            putExtra("note_data", data as Serializable)
        }

        startActivity(intent)
    }
}

interface CellClickListener {
    fun onCellClickListener(data: Note)
}