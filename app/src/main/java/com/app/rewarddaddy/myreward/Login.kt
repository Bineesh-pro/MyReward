package com.app.rewarddaddy.myreward

import android.app.Activity
import android.app.ComponentCaller
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.DialogCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var googleButton:TextView
    private lateinit var emailButton:TextView
    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = Firebase.auth

        googleButton = findViewById(R.id.google_login_button)
        emailButton = findViewById(R.id.email_login_button)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        val googleSigInClient = GoogleSignIn.getClient(this,googleSignInOptions)

        googleButton.setOnClickListener {
            resultLauncher.launch(googleSigInClient.signInIntent)
        }

        emailButton.setOnClickListener{
            Toast.makeText(this, "Use Google SignIn", Toast.LENGTH_LONG).show()
        }

    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

        if(result.resultCode == Activity.RESULT_OK){
            val signInTask:Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                if (signInTask.isSuccessful) {
                    val googleSignInAccount = signInTask.getResult(ApiException::class.java)
                    if (googleSignInAccount != null) {
                        val authCredentials =
                            GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
                        auth.signInWithCredential(authCredentials)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "SignedIn Successfully", Toast.LENGTH_LONG).show()
                                    startActivity(Intent(this, Avatar::class.java))
                                    finish()
                                } else {
                                    Toast.makeText(this, "Error Signing In", Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "SignedIn Account null", Toast.LENGTH_LONG).show()
                    }
                }
            }catch (e : Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }

    }
}