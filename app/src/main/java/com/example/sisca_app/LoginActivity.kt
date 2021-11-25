package com.example.sisca_app

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.os.Bundle
import com.example.sisca_app.R
import android.content.Intent
import com.example.sisca_app.RegisterActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.example.sisca_app.MainActivity

class LoginActivity : AppCompatActivity() {
    lateinit var registerHere: TextView
    lateinit var lEmail: EditText
    lateinit var lPassword: EditText
    lateinit var lButton: Button
    lateinit var lProgressBar: ProgressBar
    var fAuth: FirebaseAuth? = null
    var fUser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        registerHere = findViewById(R.id.register_here)
        lEmail = findViewById(R.id.email_login)
        lPassword = findViewById(R.id.password_login)
        lButton = findViewById(R.id.login_button)
        lProgressBar = findViewById(R.id.progress_bar_login)
        fAuth = FirebaseAuth.getInstance()
        registerHere.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
            finish()
        })
        lButton.setOnClickListener(View.OnClickListener {
            val email = lEmail.getText().toString().trim { it <= ' ' }
            val password = lPassword.getText().toString().trim { it <= ' ' }
            val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(lPassword.getApplicationWindowToken(), 0)
            Log.d("login_debug", email)
            Log.d("login_debug", password)
            if (TextUtils.isEmpty(email)) {
                lEmail.setError("Insert your email")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                lPassword.setError("Insert your password")
                return@OnClickListener
            }
            lProgressBar.setVisibility(View.VISIBLE)
            fAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Error ! " + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    lProgressBar.setVisibility(View.INVISIBLE)
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        fUser = FirebaseAuth.getInstance().currentUser
        if (fUser != null) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }
}