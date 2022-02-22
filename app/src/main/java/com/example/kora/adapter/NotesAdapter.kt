package com.example.kora.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.kora.R
import com.example.kora.entities.Notes
import kotlinx.android.synthetic.main.note_item.view.*

class NotesAdapter() :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    var arrayList = ArrayList<Notes>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        )
    }
    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.itemView.tvTitle.text = arrayList[position].title
        holder.itemView.tvDesc.text = arrayList[position].noteText
        holder.itemView.tvDateTime.text = arrayList[position].dateTime
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
    fun setData(arrNotesList: List<Notes>){
        arrayList = arrNotesList as ArrayList<Notes>
    }
    class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view){
    }
}