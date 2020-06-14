package com.example.project2

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project2.R
import kotlinx.android.synthetic.main.activity_view_notepad.*

class ViewNotepad : AppCompatActivity() {
    var textView_title: TextView? = null
    var textView_content: TextView? = null
    var textView_location: TextView? = null
    var textView_time: TextView? = null
    var title: String? = null
    var content: String? = null
    var location: String? = null
    var time: String? = null
    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_notepad)
        textView_title = findViewById(R.id.txtv_title_View)
        textView_content = findViewById(R.id.txtv_content_View)
        textView_location = findViewById(R.id.txtv_location_View)
        textView_time = findViewById(R.id.txtv_time_View)
        val intent = intent
        //        인텐트 정보를 받음.
        title = intent.extras!!.getString("title")
        content = intent.extras!!.getString("content")
        location = intent.extras!!.getString("location")
        time = intent.extras!!.getString("time")
        position = intent.extras!!.getInt("position")
        txtv_title_View.setText(title)
        txtv_content_View.setText(content)
        val txt_location = getString(R.string.notepad_last_edit_location)
        txtv_location_View.setText("$txt_location $location")
        val txt_time = getString(R.string.notepad_last_edit_time)
        txtv_time_View.setText("$txt_time $time")
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                val intent = Intent(this, Edit_Notepad::class.java)
                intent.putExtra("title", title)
                intent.putExtra("content", content)
                intent.putExtra("location", location)
                intent.putExtra("time", time)
                intent.putExtra("position", position)
                startActivity(intent)
                finish()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onBackPressed() {
        val intent = Intent(this@ViewNotepad, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}