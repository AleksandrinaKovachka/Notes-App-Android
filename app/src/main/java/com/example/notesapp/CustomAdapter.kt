package com.example.notesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val context: Context, private val noteList: ArrayList<Note>, private val cellClickListener: CellClickListener) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]
        holder.titleTextView.text = note.title
        holder.descriptionTextView.text = note.description
        holder.dateTextView.text = note.date

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(noteList[position])
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTextView : TextView = itemView.findViewById(R.id.textViewTitle)
        val descriptionTextView : TextView = itemView.findViewById(R.id.textViewDescription)
        val dateTextView : TextView = itemView.findViewById(R.id.textViewDate)
    }
}