package com.example.project2

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project2.MainActivity
import com.example.project2.R

class EnterPassword : AppCompatActivity() {
    var len: Byte = 0
    var input_password = ""
    var etxtv_password_1: EditText? = null
    var etxtv_password_2: EditText? = null
    var etxtv_password_3: EditText? = null
    var etxtv_password_4: EditText? = null
    var pw: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_password)
        etxtv_password_1 = findViewById(R.id.etxtv_password_1)
        etxtv_password_2 = findViewById(R.id.etxtv_password_2)
        etxtv_password_3 = findViewById(R.id.etxtv_password_3)
        etxtv_password_4 = findViewById(R.id.etxtv_password_4)
        etxtv_password_1!!.setTextColor(Color.parseColor("#FFFFB74D"))
        etxtv_password_2!!.setTextColor(Color.parseColor("#FFFFB74D"))
        etxtv_password_3!!.setTextColor(Color.parseColor("#FFFFB74D"))
        etxtv_password_4!!.setTextColor(Color.parseColor("#FFFFB74D"))
        val sp =
            getSharedPreferences("Password", Context.MODE_PRIVATE)
        pw = sp.getString("pw", "0000")
        //        SharedPreferences 를 사용해 비밀번호를 저장하였음.
//        name값은 Password이고 키값은 pw임.
//        저장된 값이 없을경우 0000으로 가져옴.
    }

    fun onClickBtnPassword(view: View) {
        when (view.id) {
            R.id.btn_0 -> if (len.toInt() == 0) {
                len++
                input_password = "0"
                etxtv_password_1!!.setText("●")
            } else if (len.toInt() == 1) {
                len++
                input_password += "0"
                etxtv_password_2!!.setText("●")
            } else if (len.toInt() == 2) {
                len++
                input_password += "0"
                etxtv_password_3!!.setText("●")
            } else if (len.toInt() == 3) {
                len++
                input_password += "0"
                etxtv_password_4!!.setText("●")
            }
            R.id.btn_1 -> if (len.toInt() == 0) {
                len++
                input_password = "1"
                etxtv_password_1!!.setText("●")
            } else if (len.toInt() == 1) {
                len++
                input_password += "1"
                etxtv_password_2!!.setText("●")
            } else if (len.toInt() == 2) {
                len++
                input_password += "1"
                etxtv_password_3!!.setText("●")
            } else if (len.toInt() == 3) {
                len++
                input_password += "1"
                etxtv_password_4!!.setText("●")
            }
            R.id.btn_2 -> if (len.toInt() == 0) {
                len++
                input_password = "2"
                etxtv_password_1!!.setText("●")
            } else if (len.toInt() == 1) {
                len++
                input_password += "2"
                etxtv_password_2!!.setText("●")
            } else if (len.toInt() == 2) {
                len++
                input_password += "2"
                etxtv_password_3!!.setText("●")
            } else if (len.toInt() == 3) {
                len++
                input_password += "2"
                etxtv_password_4!!.setText("●")
            }
            R.id.btn_3 -> if (len.toInt() == 0) {
                len++
                input_password = "3"
                etxtv_password_1!!.setText("●")
            } else if (len.toInt() == 1) {
                len++
                input_password += "3"
                etxtv_password_2!!.setText("●")
            } else if (len.toInt() == 2) {
                len++
                input_password += "3"
                etxtv_password_3!!.setText("●")
            } else if (len.toInt() == 3) {
                len++
                input_password += "3"
                etxtv_password_4!!.setText("●")
            }
            R.id.btn_4 -> if (len.toInt() == 0) {
                len++
                input_password = "4"
                etxtv_password_1!!.setText("●")
            } else if (len.toInt() == 1) {
                len++
                input_password += "4"
                etxtv_password_2!!.setText("●")
            } else if (len.toInt() == 2) {
                len++
                input_password += "4"
                etxtv_password_3!!.setText("●")
            } else if (len.toInt() == 3) {
                len++
                input_password += "4"
                etxtv_password_4!!.setText("●")
            }
            R.id.btn_5 -> if (len.toInt() == 0) {
                len++
                input_password = "5"
                etxtv_password_1!!.setText("●")
            } else if (len.toInt() == 1) {
                len++
                input_password += "5"
                etxtv_password_2!!.setText("●")
            } else if (len.toInt() == 2) {
                len++
                input_password += "5"
                etxtv_password_3!!.setText("●")
            } else if (len.toInt() == 3) {
                len++
                input_password += "5"
                etxtv_password_4!!.setText("●")
            }
            R.id.btn_6 -> if (len.toInt() == 0) {
                len++
                input_password = "6"
                etxtv_password_1!!.setText("●")
            } else if (len.toInt() == 1) {
                len++
                input_password += "6"
                etxtv_password_2!!.setText("●")
            } else if (len.toInt() == 2) {
                len++
                input_password += "6"
                etxtv_password_3!!.setText("●")
            } else if (len.toInt() == 3) {
                len++
                input_password += "6"
                etxtv_password_4!!.setText("●")
            }
            R.id.btn_7 -> if (len.toInt() == 0) {
                len++
                input_password = "7"
                etxtv_password_1!!.setText("●")
            } else if (len.toInt() == 1) {
                len++
                input_password += "7"
                etxtv_password_2!!.setText("●")
            } else if (len.toInt() == 2) {
                len++
                input_password += "7"
                etxtv_password_3!!.setText("●")
            } else if (len.toInt() == 3) {
                len++
                input_password += "7"
                etxtv_password_4!!.setText("●")
            }
            R.id.btn_8 -> if (len.toInt() == 0) {
                len++
                input_password = "8"
                etxtv_password_1!!.setText("●")
            } else if (len.toInt() == 1) {
                len++
                input_password += "8"
                etxtv_password_2!!.setText("●")
            } else if (len.toInt() == 2) {
                len++
                input_password += "8"
                etxtv_password_3!!.setText("●")
            } else if (len.toInt() == 3) {
                len++
                input_password += "8"
                etxtv_password_4!!.setText("●")
            }
            R.id.btn_9 -> if (len.toInt() == 0) {
                len++
                input_password = "9"
                etxtv_password_1!!.setText("●")
            } else if (len.toInt() == 1) {
                len++
                input_password += "9"
                etxtv_password_2!!.setText("●")
            } else if (len.toInt() == 2) {
                len++
                input_password += "9"
                etxtv_password_3!!.setText("●")
            } else if (len.toInt() == 3) {
                len++
                input_password += "9"
                etxtv_password_4!!.setText("●")
            }
            R.id.imgbtn_back -> //                지우기 버튼을 눌렀을때 작동함.
                if (len.toInt() == 1) {
                    len--
                    input_password = ""
                    etxtv_password_1!!.setText("")
                } else if (len.toInt() == 2) {
                    len--
                    input_password = input_password.substring(0, 1)
                    //                    input_password를 input_password의 0부터 1까지만 남김
                    etxtv_password_2!!.setText("")
                } else if (len.toInt() == 3) {
                    len--
                    input_password = input_password.substring(0, 2)
                    etxtv_password_3!!.setText("")
                }
        }
        if (len.toInt() == 4) {
            if (input_password == pw) {
                val intent = Intent(this@EnterPassword, MainActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, R.string.Toast_connect, Toast.LENGTH_SHORT).show()
            } else {
                val vibrator =
                    getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(300)
                //                패스워드 틀릴시 진동
                Toast.makeText(this, R.string.Toast_denied, Toast.LENGTH_SHORT).show()
                len = 0
                input_password = ""
                etxtv_password_1!!.setText("")
                etxtv_password_2!!.setText("")
                etxtv_password_3!!.setText("")
                etxtv_password_4!!.setText("")
            }
        }
    }
}