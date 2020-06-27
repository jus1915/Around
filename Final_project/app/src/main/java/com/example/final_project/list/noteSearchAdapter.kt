package com.example.final_project.list

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.room.NoteEntity
import kotlinx.android.synthetic.main.list_item_note.view.*

class noteSearchAdapter internal constructor(intent: Intent) :
    RecyclerView.Adapter<noteSearchAdapter.ItemViewHolder>(){
    var notes: List<NoteEntity> = emptyList()
    private var search: String? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_note, parent, false)
        return ItemViewHolder(itemView)    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItems(notes[position])}
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItems(note: NoteEntity) {
            itemView.item_txt_title.text = note.noteTitle
            itemView.item_txt_content.text = note.noteContent
            note.noteImage?.let {
                itemView.item_profile_image.visibility = View.VISIBLE
                Log.d("TAG", it)
                itemView.item_profile_image.setImageURI(Uri.parse(it))
            } ?: kotlin.run {
                itemView.item_profile_image.visibility = View.GONE
            }
            itemView.setOnClickListener() {
                Navigation.findNavController(itemView).navigate(
                    R.id.action_listFragment_to_detailFragment,
                    Bundle().apply {
                        putLong("NOTE_ID", note.noteIdx!!)
                    })
            }
        }
    }
    init {
        search = intent.extras!!.getString("search")
    }
}