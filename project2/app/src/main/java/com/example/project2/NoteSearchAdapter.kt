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
import com.example.project2.ViewNotepad
import com.example.project2.NoteList
import java.util.*

class NoteSearchAdapter internal constructor(val intent: Intent) :
    RecyclerView.Adapter<NoteSearchAdapter.ViewHolder>() {
    //    NoteSearchAdapter는 NoteAdapter와 기본 구조는 같음.
    var noteitems = ArrayList<NoteList>()
    private val sql: String?
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
                dbHelper = DatabaseHelper(v.context)
                database = dbHelper.writableDatabase
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    Log.d("tag", position.toString() + "클릭")
                    val cursor = database.rawQuery(sql, null)
                    //                        전달받은 쿼리문을 저장한 문자열 변수 sql을 사용
                    cursor.move(position + 1)
                    val intent = Intent(v.context, ViewNotepad::class.java)
                    intent.putExtra("title", cursor.getString(1))
                    intent.putExtra("content", cursor.getString(2))
                    intent.putExtra("location",cursor.getString(3))
                    intent.putExtra("time", cursor.getString(4))
                    intent.putExtra("position", cursor.getInt(0))
                    val context = v.context
                    context.startActivity(intent)
                    (context as Activity).finish()
                }
            }
        }
    }

    //    인텐트 정보 받고 문자형 변수 sql 초기화.
    init {
        sql = intent.extras!!.getString("sql")
    }
}