package com.app.rewarddaddy.myreward

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class Onboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewpager = findViewById<ViewPager2>(R.id.view_pager)
        val continueButton = findViewById<TextView>(R.id.continue_button)
        val skip = findViewById<LinearLayout>(R.id.skip_button)
        val modelList = arrayListOf(R.drawable.chemistry,R.drawable.maths)

        val slideIndicatorLayout = findViewById<ListView>(R.id.slide_indicator_layout)
        slideIndicatorLayout.adapter = ArrayAdapter<View>(applicationContext,R.layout.onboard_view)

        viewpager.adapter = OnboardSlideAdapter(applicationContext,modelList)

        skip.setOnClickListener {
            moveToLogin()
        }
        continueButton.setOnClickListener{
            if(viewpager.currentItem >= 1){
                moveToLogin()
            }else{
                viewpager.currentItem += 1
            }
        }

    }

    private fun moveToLogin(){
        val intent = Intent(applicationContext, Login::class.java)
        startActivity(intent)
        finish()
    }
}