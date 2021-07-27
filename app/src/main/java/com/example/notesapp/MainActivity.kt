package com.example.notesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //getting recyclerview from xml
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        //crating an arraylist to store users using the data class user
        val notes = ArrayList<Note>()

        //adding some dummy data to the list
        notes.add(Note("Test1", "Test again 1"))
        notes.add(Note("Test2", "Test again 2"))
        notes.add(Note("Test3", "Test again 3"))
        notes.add(Note("Test4", "Test again 4"))

        //creating our adapter
        val adapter = CustomAdapter(notes)

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
    }
}