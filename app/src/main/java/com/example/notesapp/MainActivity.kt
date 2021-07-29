package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.Serializable

class MainActivity : AppCompatActivity(), CellClickListener {
    private var notes = ArrayList<Note>()
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createBtn = findViewById<FloatingActionButton>(R.id.floatingActionButtonCreate)
        createBtn.setOnClickListener {
            //Toast.makeText(this@MainActivity, "FAB is clicked...", Toast.LENGTH_LONG).show()
            val intent = Intent(this, NoteCreationActivity::class.java)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        //recyclerView.adapter = Adapter(this, fetchList())

        //getting recyclerview from xml
        //val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        //adding a layoutmanager
        //recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false) //applicationContext


        //crating an arraylist to store users using the data class user
        //val notes = ArrayList<Note>()

        //adding some dummy data to the list
        Log.i("Activity", "main")
        notes = readData() as ArrayList<Note>
        //notes = arrayListOf()

        adapter = CustomAdapter(this, notes, this)

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i("Activity", "new note")
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            notes.add(data?.getSerializableExtra("new_note") as Note)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCellClickListener(data: Note) {
        val intent = Intent(this, NoteDataActivity::class.java).apply {
            putExtra("note_data", data as Serializable)
        }

        startActivity(intent)
    }

    private fun readData(): MutableList<Note> {
        val readFile: File = File(this.filesDir, "SavedUserNotes")
        if (readFile.exists()) {
            val bytesData = readFile.readBytes()
            val stringData = String(bytesData)

            Log.i("Activity", stringData)
            return Json.decodeFromString(stringData)
        }

        print("Not exist file")
        return mutableListOf()
    }

}

interface CellClickListener {
    fun onCellClickListener(data: Note)
}