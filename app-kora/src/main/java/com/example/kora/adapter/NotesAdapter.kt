package com.example.kora.adapter

import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kora.R
import com.example.kora.databinding.NoteItemBinding
import com.example.kora.entities.Notes


class NotesAdapter() :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    var listener:OnItemClickListener? = null
    var arrayList = ArrayList<Notes>()
    private var noteItemBinding: NoteItemBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        noteItemBinding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(noteItemBinding!!)
    }
    fun setOnClickListener(listener1: OnItemClickListener){
        listener = listener1
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.itemBinding.tvTitle.text = arrayList[position].title
        holder.itemBinding.tvDesc.text = arrayList[position].noteText
        holder.itemBinding.tvDateTime.text = arrayList[position].dateTime

        if (arrayList[position].color != null){
            holder.itemBinding.cardView.setCardBackgroundColor(Color.parseColor(arrayList[position].color))
        }else{

            holder.itemBinding.cardView.setCardBackgroundColor(Color.parseColor(R.color.buttonyellow.toString()))
        }

        if (arrayList[position].imgPath != null){
            holder.itemBinding.imgNote.setImageBitmap(BitmapFactory.decodeFile(arrayList[position].imgPath))
            holder.itemBinding.imgNote.visibility = View.VISIBLE
        }else{
            holder.itemBinding.imgNote.visibility = View.GONE
        }

        if (arrayList[position].webLink != ""){
            holder.itemBinding.tvWebLink.text = arrayList[position].webLink
            holder.itemBinding.tvWebLink.visibility = View.VISIBLE
        }else{
            holder.itemBinding.tvWebLink.visibility = View.GONE
        }
        holder.itemBinding.cardView.setOnClickListener {
            listener!!.onClicked(arrayList[position].id!!)
        }

    }
    override fun getItemCount(): Int = arrayList.size

    fun setData(arrNotesList: List<Notes>) {
        arrayList = arrNotesList as ArrayList<Notes>
    }



    class NotesViewHolder(var itemBinding: NoteItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


    }



    interface OnItemClickListener {
        fun onClicked(noteId: Int)
    }

}