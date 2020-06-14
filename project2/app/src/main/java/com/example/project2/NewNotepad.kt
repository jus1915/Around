package com.example.project2

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.project2.*
import com.example.project2.DatabaseHelper
import kotlinx.android.synthetic.main.activity_new_notepad.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class NewNotepad : AppCompatActivity() {
    var dbHelper: DatabaseHelper? = null
    var editText_title: EditText? = null
    var editText_content: EditText? = null
    var editText_location: EditText? = null
    var database: SQLiteDatabase? = null
    var tableName: String? = null

    var locationManager : LocationManager? = null
    private val REQUEST_CODE_LOCATION : Int = 2
    var currentLocation : String = "위치정보 없음"
    var latitude : Double? = null
    var longitude : Double? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_notepad)
        tableName = "noteData"
        dbHelper = DatabaseHelper(this)
        database = dbHelper!!.writableDatabase
        editText_title = findViewById(R.id.etxtv_title_new)
        editText_content = findViewById(R.id.etxtv_content_new)
        editText_location = findViewById(R.id.etxtv_location_new)
        // 내용 입력 에디트 텍스트에 포커스를 할당하고
        // 입력 키패드가 보이게 함
        etxtv_content_new.requestFocus()
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
        val button_loc = findViewById<Button>(R.id.btn_location_new)
        button_loc.setOnClickListener {

            etxtv_location_new.setText(currentLocation)
        }
        val button = findViewById<Button>(R.id.btn_save_new)
        button.setOnClickListener(View.OnClickListener {
            val adapter = NoteAdapter()
            adapter.addItem(
                NoteList(
                    etxtv_title_new.getText().toString(),
                    etxtv_content_new.getText().toString()
                )
            )
            //                어댑터에 아이템을 추가함.

//                 제목과 내용을 가져옴
            val input_title = etxtv_title_new.getText().toString()
            val input_content = etxtv_content_new.getText().toString()
            val input_location = etxtv_location_new.getText().toString()
//                저장
            try {
                val contentValues = ContentValues()

//                    입력된 내용이 아무것도 없을경우 취소.
                if (TextUtils.isEmpty(input_title) && TextUtils.isEmpty(input_content)) {
                    Toast.makeText(this@NewNotepad, R.string.Toast_not_save, Toast.LENGTH_LONG)
                        .show()
                    finish()
                    val intent = Intent(this@NewNotepad, MainActivity::class.java)
                    startActivity(intent)
                    return@OnClickListener
                }

//                    시간설정


                val now = System.currentTimeMillis()
                val mDate = Date(now)
                val sdfNow =
                    SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                val formatDate = sdfNow.format(mDate)
//                   위치설정
                contentValues.put("title", input_title)
                contentValues.put("content", input_content)
                contentValues.put("location",input_location)
                contentValues.put("time", formatDate)
                Toast.makeText(this@NewNotepad, R.string.Toast_save, Toast.LENGTH_SHORT).show()
                //                    저장
                database!!.insert(tableName, null, contentValues)
            } catch (e: Exception) {
                Log.e("ERROR", e.toString())
            }
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(
                InputMethodManager.HIDE_IMPLICIT_ONLY,
                0
            )
            finish()
            val intent = Intent(this@NewNotepad, MainActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onBackPressed() {
        finish()
        val intent = Intent(this@NewNotepad, MainActivity::class.java)
        startActivity(intent)
        //        뒤로가기 버튼 터치시 닫고 메인액티비티를 새로 띄움
//        스택이 쌓이기 때문임
//        백스택 방지 API문서 - https://developer.android.com/guide/components/tasks-and-back-stack?hl=ko
    }
}