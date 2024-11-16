package com.app.rewarddaddy.myreward


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2


class Avatar : BaseClass()  {
    private lateinit var letsGoButton:TextView
    private lateinit var userName:EditText
    private lateinit var profileSelectedPic:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_avatar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        letsGoButton = findViewById(R.id.lets_go)
        userName = findViewById(R.id.user_name)
        profileSelectedPic = findViewById(R.id.card_avatar_profile_pic)
        val sharedPreference = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val userNamePrev: String = sharedPreference.getString("user_name","").toString()
        if(userNamePrev.isNotEmpty()){
            userName.hint = userNamePrev
        }
        val avatarList = arrayOf(
            R.drawable.avatar_0,
            R.drawable.avatar_1,
            R.drawable.avatar_2,
            R.drawable.avatar_3,
            R.drawable.avatar_5,
            R.drawable.avatar_6,
            R.drawable.avatar_7,
            R.drawable.avatar_8,
            R.drawable.avatar_9
        )
        val avatarLargeList = arrayOf(*avatarList,*avatarList)
        val avatarContainer = findViewById<GridView>(R.id.avatar_list_grid)
        avatarContainer.adapter = AvatarGridAdapter(this,avatarLargeList)
        letsGoButton.setOnClickListener{
            if(TextUtils.isEmpty(userName.text)) {
                Toast.makeText(this, "Please type an username", Toast.LENGTH_SHORT).show()
            }else if(sharedPreference.getInt("user_avatar",0) == 0) {
                Toast.makeText(this, "Please Choose an avatar", Toast.LENGTH_SHORT).show()
            }else {
                val sharedPreferenceEditor = sharedPreference.edit()
                sharedPreferenceEditor.putString("user_name", userName.text.toString())
                sharedPreferenceEditor.apply()
                startActivity(Intent(application, Home::class.java))
                finish()
            }
        }


    }

    override fun updateUI(picId: Int) {
        this.profileSelectedPic.setImageResource(picId)
    }
}