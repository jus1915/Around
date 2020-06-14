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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.project2.MainActivity
import com.example.project2.ViewNotepad
import com.example.project2.DatabaseHelper
import com.example.project2.R
import kotlinx.android.synthetic.main.activity_edit_notepad.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class Edit_Notepad : AppCompatActivity() {
    var dbHelper: DatabaseHelper? = null
    var database: SQLiteDatabase? = null
    var tableName: String? = null
    var dbName: String? = null
    var editText_title: EditText? = null
    var editText_content: EditText? = null
    var textView_location: TextView? = null
    var textView_time: TextView? = null
    var title: String? = null
    var content: String? = null
    var location: String? = null
    var time: String? = null
    var position = 0

    var locationManager : LocationManager? = null
    private val REQUEST_CODE_LOCATION : Int = 2
    var currentLocation : String = ""
    var latitude : Double? = null
    var longitude : Double? = null

    private fun getCurrentLoc(){
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        var userLocation: Location = getLatLng()
        if (userLocation !=null){
            latitude = userLocation.latitude
            longitude = userLocation.longitude
            Log.d("CheckCurrentLocation","현재 내 위치 값 : $latitude, $longitude")

            var mGeocoder = Geocoder(applicationContext,Locale.KOREAN)
            var mResultList: List<Address>? = null
            try{
                mResultList = mGeocoder.getFromLocation(
                    latitude!!,longitude!!,1
                )
            } catch (e: IOException){
                e.printStackTrace()
            }
            if (mResultList != null){
                Log.d("CheckCurrentLocation", mResultList[0].getAddressLine(0))
                currentLocation = mResultList[0].getAddressLine(0)
                currentLocation = currentLocation.substring(11)
            }
        }
    }

    private fun getLatLng() : Location {
        var currentLatLng: Location? = null
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),this.REQUEST_CODE_LOCATION)
            getLatLng()
        }else{
            val locationProvider = LocationManager.GPS_PROVIDER
            currentLatLng = locationManager?.getLastKnownLocation(locationProvider)
        }
        return currentLatLng!!
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_notepad)
        dbHelper = DatabaseHelper(this)
        database = dbHelper!!.writableDatabase
        dbName = "notepad.db"
        tableName = "noteData"
        editText_title = findViewById(R.id.etxtv_title_edit)
        editText_content = findViewById(R.id.etxtv_content_edit)
        textView_location=findViewById(R.id.txtv_location_edit)
        textView_time = findViewById(R.id.txtv_time_edit)


        val intent = intent
        //        인텐트 정보를 받음.
        title = intent.extras!!.getString("title")
        content = intent.extras!!.getString("content")
        location = intent.extras!!.getString("location")
        time = intent.extras!!.getString("time")
        position = intent.extras!!.getInt("position")
        //        키 값
        etxtv_title_edit.setText(title)
        etxtv_content_edit.setText(content)
        val txt_location = getString(R.string.notepad_last_edit_location)
        txtv_location_edit.setText("$txt_location $location")
        val txt_time = getString(R.string.notepad_last_edit_time)
        txtv_time_edit.setText("$txt_time $time")
        val button_save =
            findViewById<Button>(R.id.btn_save_edit)
        button_save.setOnClickListener(View.OnClickListener {
            val edit_title = etxtv_title_edit.getText().toString()
            val edit_content = etxtv_content_edit.getText().toString()
            val contentValues = ContentValues()
            if (TextUtils.isEmpty(edit_title) && TextUtils.isEmpty(edit_content)) {
//                    제목과 내용이 모두 비어있으면 삭제함.
                Toast.makeText(this@Edit_Notepad, R.string.Toast_not_save, Toast.LENGTH_LONG)
                    .show()
                database!!.delete(
                    "noteData",
                    "_id=?",
                    arrayOf(position.toString())
                )
                //                    데이터베이스에서 삭제.
                finish()
                //                    액티비티 닫기
                val intent1 = Intent(this@Edit_Notepad, MainActivity::class.java)
                startActivity(intent1)
                //                    메인 액티비티 호출
                return@OnClickListener
            }
            val now = System.currentTimeMillis()
            val mDate = Date(now)
            val sdfNow =
                SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            val formatDate = sdfNow.format(mDate)
            //                현재 시간을 담기위해 호출
            getLatLng()
            getCurrentLoc()
            val loc = currentLocation
//                불러온 값과 수정한 값이 같을경우 취소하고 메인액티비티 호출
            if (title == edit_title && content == edit_content) {
                val intent1 = Intent(this@Edit_Notepad, MainActivity::class.java)
                Toast.makeText(this@Edit_Notepad, R.string.Toast_not_save, Toast.LENGTH_SHORT)
                    .show()
                startActivity(intent1)
                return@OnClickListener
            }
            contentValues.put("title", edit_title)
            contentValues.put("content", edit_content)
            contentValues.put("location",loc)
            contentValues.put("time", formatDate)
            Toast.makeText(this@Edit_Notepad, R.string.Toast_edit, Toast.LENGTH_SHORT).show()
            database!!.update(
                "noteData",
                contentValues,
                "_id=?",
                arrayOf(position.toString())
            )
            //                업데이트문 (수정)
            finish()
            val intent1 = Intent(this@Edit_Notepad, MainActivity::class.java)
            startActivity(intent1)
        })
        val button_delete =
            findViewById<Button>(R.id.btn_delete_edit)
        //        삭제
        button_delete.setOnClickListener {
            Toast.makeText(this@Edit_Notepad, R.string.Toast_delete, Toast.LENGTH_SHORT).show()
            database!!.delete("noteData", "_id=?", arrayOf(position.toString()))
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(
                InputMethodManager.HIDE_IMPLICIT_ONLY,
                0
            )
            finish()
            val intent1 = Intent(this@Edit_Notepad, MainActivity::class.java)
            startActivity(intent1)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@Edit_Notepad, ViewNotepad::class.java)
        intent.putExtra("title", title)
        intent.putExtra("content", content)
        intent.putExtra("location", location)
        intent.putExtra("time", time)
        intent.putExtra("position", position)
        startActivity(intent)
        finish()
        //        백 버튼 터치하면 닫고 메인액티비티 띄움.
    }
}