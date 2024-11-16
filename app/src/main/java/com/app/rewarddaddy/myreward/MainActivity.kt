package com.app.rewarddaddy.myreward

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
         Handler(Looper.getMainLooper()).postDelayed({
            val userName = getSharedPreferences("user_data", Context.MODE_PRIVATE).getString("user_name","").toString()
            if(userName != ""){
                startActivity(Intent(applicationContext,Home::class.java))
                finish()
            }else {
                startActivity(Intent(applicationContext, Onboard::class.java))
                finish()
            }
        },4000)
    }
}