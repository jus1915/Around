package com.example.project2

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project2.DatabaseHelper
import com.example.project2.R
import com.example.project2.NoteList
import com.example.project2.ViewNotepad
import java.util.*

class NoteAdapter :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    //    어댑터에 대한 정보는 검색해보기 바람.
    var noteitems = ArrayList<NoteList>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =
            inflater.inflate(R.layout.activity_noteadapter, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = noteitems[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return noteitems.size
    }

    fun addItem(item: NoteList) {
        noteitems.add(item)
    }

    fun setItems(items: ArrayList<NoteList>) {
        this.noteitems = items
    }

    fun getItem(position: Int): NoteList {
        return noteitems[position]
    }

    fun setItem(position: Int, item: NoteList) {
        noteitems[position] = item
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var textView_title: TextView
        var textView_content: TextView
        fun setItem(item: NoteList) {
            textView_title.text = item.title
            textView_content.text = item.content
        }

        init {
            textView_title = itemView.findViewById(R.id.txtv_title)
            textView_content = itemView.findViewById(R.id.txtv_content)
            itemView.setOnClickListener { v ->
                val dbHelper: DatabaseHelper
                val database: SQLiteDatabase
                val context = v.context
                dbHelper = DatabaseHelper(v.context)
                database = dbHelper.writableDatabase
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    Log.d("tag", position.toString() + "클릭")
                    val cursor = database.rawQuery(
                        "select _id, title, content, location, time from noteData order by time DESC",
                        null
                    )
                    cursor.move(position + 1)
                    val intent = Intent(v.context, ViewNotepad::class.java)
                    intent.putExtra("title", cursor.getString(1))
                    intent.putExtra("content", cursor.getString(2))
                    intent.putExtra("location",cursor.getString(3))
                    intent.putExtra("time", cursor.getString(4))
                    intent.putExtra("position", cursor.getInt(0))
                    context.startActivity(intent)
                    (context as Activity).finish()
                }
            }

        }
    }
}