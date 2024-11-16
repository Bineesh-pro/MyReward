package com.app.rewarddaddy.myreward

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.adjoe.sdk.Playtime
import io.adjoe.sdk.PlaytimeExtensions
import io.adjoe.sdk.PlaytimeGender
import io.adjoe.sdk.PlaytimeInitialisationListener
import io.adjoe.sdk.PlaytimeNotInitializedException
import io.adjoe.sdk.PlaytimeOptions
import io.adjoe.sdk.PlaytimeParams
import io.adjoe.sdk.PlaytimeUserProfile
import java.util.Calendar

class Home : AppCompatActivity() {

    private lateinit var frameLayout:FrameLayout
    private lateinit var progressBar:ProgressBar
    private lateinit var bottomNav:BottomNavigationView
    private lateinit var profilePic:ImageView
    private lateinit var helloUser:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        frameLayout = findViewById(R.id.frame_layout)
        progressBar = findViewById(R.id.progress_bar_center)
        bottomNav = findViewById(R.id.bottom_nav)
        profilePic = findViewById(R.id.profile_avatar)
        helloUser = findViewById(R.id.hello_user)
        val pic = getSharedPreferences("user_data", Context.MODE_PRIVATE).getInt("user_avatar",0)
        val uname = getSharedPreferences("user_data", Context.MODE_PRIVATE).getString("user_name","")
        val name = "Hello, $uname!"
        helloUser.text = name
        profilePic.setImageResource(pic)

        if(Playtime.isInitialized()){
            progressBar.visibility = View.GONE
            frameLayout.visibility = View.VISIBLE
        }else{
            initializeAdjoeSDK()
        }

        profilePic.setOnClickListener{
            startActivity(Intent(this,Avatar::class.java))
        }
        bottomNav.setOnItemSelectedListener { i ->
            when (i.title.toString()) {
                "Home" -> setFrame(HomeFragment())
                "Discover" -> setFrame(DiscoverFragment())
                "Leaderboard" -> setFrame(LeaderboardFragment())
                "Achievement" -> setFrame(AchievementFragment())
                else -> setFrame(HomeFragment())
            }
        }

    }

    private fun initializeAdjoeSDK() {

        val userId = "f0b9d695-405d-4b13-8a79-5fe21dc901e6"

        val playtimeParams = PlaytimeParams.Builder()
            .setUaNetwork("tiktok")
            .setUaChannel("video")
            .setUaSubPublisherCleartext("Example: Game 2")
            .setUaSubPublisherEncrypted("8bb1e7911818be32449f6726ff7ecd102ba1862b")
            .setPlacement("Main Screen")
            .build()

        // these values are additional options that you can pass-in to identify users. These data will be sent back in the S2S URL. Example data below.
        val playtimeExtensions = PlaytimeExtensions.Builder()
            .setSubId1("Target Group 1")
            .setSubId2("Target Group 2")
            .build()

        //Set the options
        val options = PlaytimeOptions()
            .setUserId(userId)
            .setParams(playtimeParams)
            .setUserProfile(getUserProfile())
            .setExtensions(playtimeExtensions)

        // Initialize the adjoe Playtime SDK, passing in the Playtime Options
        try {
            Playtime.init(this, "da38a1b86c7ed4c41c9f6eaaabe5becb", options, object : PlaytimeInitialisationListener {
                override fun onInitialisationFinished() {
                    Toast.makeText(applicationContext,"Initialized Succesfully",Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                    frameLayout.visibility = View.VISIBLE
                    setFrame(HomeFragment())
                }

                override fun onInitialisationError(exception: Exception?) {
                    Toast.makeText(applicationContext,"Error in adjoe part",Toast.LENGTH_LONG).show()
                }
            })
        } catch (e: PlaytimeNotInitializedException) {
            Toast.makeText(applicationContext,"Error in adjoe part",Toast.LENGTH_LONG).show()
        }

    }

    private fun getUserProfile(): PlaytimeUserProfile? {
        val birthday = Calendar.getInstance()
        birthday.set(Calendar.YEAR, 1995)
        birthday.set(Calendar.MONTH, Calendar.JANUARY)
        birthday.set(Calendar.DAY_OF_MONTH, 30)
        return PlaytimeUserProfile(PlaytimeGender.MALE,birthday.time)
    }

    private fun setFrame(frame:Fragment) : Boolean{
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout,frame).commit()
        return true
    }
}