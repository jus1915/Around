package com.example.project2

import com.example.project2.EnterPassword
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.project2.R

class Splash : AppCompatActivity() {
    var timer: Byte = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        timer = 0
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (timer < 3) {
                    timer++
                    handler.postDelayed(this, 300)
                } else {
                    finish()
                    val intent = Intent(this@Splash, EnterPassword::class.java)
                    startActivity(intent)
                }
            }
        }, 0)
    }
}