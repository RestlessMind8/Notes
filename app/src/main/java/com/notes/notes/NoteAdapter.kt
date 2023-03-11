package com.notes.notes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.notes.notes.model.Note

class NoteAdapter (
    private var items: MutableList<Note>,
    private val onItemClicked: ((Note) -> Unit)
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_cardview, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is NoteViewHolder ->{
                holder.bind(items[position], onItemClicked)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class NoteViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        private val title = itemView.findViewById<TextView>(R.id.cardTitle)
        private val lastUpdate = itemView.findViewById<TextView>(R.id.cardDate)
        private val color = itemView.findViewById<FrameLayout>(R.id.cardColor)
        private val card = itemView.findViewById<androidx.cardview.widget.CardView>(R.id.card)

        fun bind(note: Note, onItemClicked: (Note) -> Unit){
            title.text = note.title
            lastUpdate.text = note.lastUpdate
            color.setBackgroundColor(Color.parseColor(note.color))
            if(note.color.length == 7)
                card.setCardBackgroundColor(Color.parseColor("#A1" + note.color.substring(1, note.color.length)))
            else
                card.setCardBackgroundColor(Color.parseColor("#A1" + note.color.substring(3, note.color.length)))
            itemView.setOnClickListener{
                onItemClicked(note)
            }
        }
    }
}