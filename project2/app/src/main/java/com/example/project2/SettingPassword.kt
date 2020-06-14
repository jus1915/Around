package com.example.project2

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setting_password.*

class SettingPassword : AppCompatActivity() {
    //    암호 변경
    var editText: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_password)
        editText = findViewById(R.id.etxtv_settingpassword)

//        editText에 포커스 주고 입력자판 올림
        etxtv_settingpassword.requestFocus()
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )

//        취소 버튼
        val imageButton_cancel =
            findViewById<ImageButton>(R.id.imgbtn_cancel_setting_password)
        imageButton_cancel.setOnClickListener {
            Toast.makeText(
                this@SettingPassword,
                R.string.Toast_password_cancel,
                Toast.LENGTH_SHORT
            ).show()
            //                입력자판 내림
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(
                InputMethodManager.HIDE_IMPLICIT_ONLY,
                0
            )
            finish()
        }

//        확인 버튼
        val imageButton_ok = findViewById<ImageButton>(R.id.imgbtn_ok_setting_password)
        imageButton_ok.setOnClickListener(View.OnClickListener {
            val setPW = etxtv_settingpassword.getText().toString()
            //                입력한 숫자가 4개가 안될경우
            if (setPW.length < 4) {
                Toast.makeText(
                    this@SettingPassword,
                    R.string.Toast_password_limit,
                    Toast.LENGTH_SHORT
                ).show()
                //                    토스트메시지 출력 후 리턴.
                return@OnClickListener
            }

//                비밀번호를 저장
            val sp =
                getSharedPreferences("Password", Context.MODE_PRIVATE)
            val checker = sp.getString("pw", "0000")
            //                이전 비밀번호와 동일할 경우
            if (setPW == checker) {
                Toast.makeText(
                    this@SettingPassword,
                    R.string.Toast_password_not_change,
                    Toast.LENGTH_LONG
                ).show()
                etxtv_settingpassword.setText("")
            } else {
//                      여기서부터
                val editor = sp.edit()
                editor.putString("pw", setPW)
                editor.apply()
                //                      여기까지 저장

//                    토스트 내용이 바뀌는 경우 다음 주석이 있는 부분까지 아래와 같이 처리
                val resources = resources
                val tmp =
                    String.format(resources.getString(R.string.Toast_password_ok), setPW)
                //                    자세한 내용은 values/string.xml 확인
//                바뀐 비밀번호 Toast메시지로 띄움.
                Toast.makeText(this@SettingPassword, tmp, Toast.LENGTH_LONG).show()

//                입력자판 내림
                val inputMethodManager =
                    getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.toggleSoftInput(
                    InputMethodManager.HIDE_IMPLICIT_ONLY,
                    0
                )
                finish()
            }
        })
    }

    // 액티비티 바깥 영역 터치시 닫히지 않게 설정
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (event.action == MotionEvent.ACTION_OUTSIDE) {
            false
        } else true
    }

    //    뒤로가기 버튼
    override fun onBackPressed() {
        Toast.makeText(this@SettingPassword, R.string.Toast_password_cancel, Toast.LENGTH_SHORT)
            .show()
        //                입력자판 내림
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(
            InputMethodManager.HIDE_IMPLICIT_ONLY,
            0
        )
        finish()
    }
}