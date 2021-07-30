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
        holder.bindItems(noteList[position])

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(noteList[position])
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(note: Note) {
            val textViewTitle = itemView.findViewById(R.id.textViewTitle) as TextView
            val textViewDescription  = itemView.findViewById(R.id.textViewDescription) as TextView
            val textViewDate = itemView.findViewById(R.id.textViewDate) as TextView
            textViewTitle.text = note.title
            textViewDescription.text = note.description
            textViewDate.text = note.date
        }
    }
}