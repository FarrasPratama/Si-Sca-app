package com.example.sisca_app

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.os.Bundle
import com.example.sisca_app.R
import android.content.Intent
import com.example.sisca_app.LoginActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.OnFailureListener
import java.util.HashMap

class RegisterActivity : AppCompatActivity() {
    private lateinit var rFullName: EditText
    private lateinit var rNickName: EditText
    private lateinit var rDoctorName: EditText
    private lateinit var rAddress: EditText
    private lateinit var rAge: EditText
    private lateinit var rEmailAddress: EditText
    private lateinit var rPassword: EditText
    private lateinit var rButton: Button
    private lateinit var rLogin: TextView
    private lateinit var rProgressBar: ProgressBar
    private var fAuth: FirebaseAuth? = null
    private var fStore: FirebaseFirestore? = null
    private var rUserID: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        rFullName = findViewById(R.id.fullname_register)
        rNickName = findViewById(R.id.nickname_register)
        rDoctorName = findViewById(R.id.doctor_name_register)
        rAddress = findViewById(R.id.address_register)
        rAge = findViewById(R.id.age_register)
        rEmailAddress = findViewById(R.id.email_register)
        rPassword = findViewById(R.id.password_register)
        rLogin = findViewById(R.id.already_registered)
        rButton = findViewById(R.id.register_button)
        rProgressBar = findViewById(R.id.progress_bar_register)
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        if (fAuth!!.currentUser != null) {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }
        rButton.setOnClickListener(View.OnClickListener {
            val email = rEmailAddress.getText().toString().trim { it <= ' ' }
            val password = rPassword.getText().toString().trim { it <= ' ' }
            val fullName = rFullName.getText().toString().trim { it <= ' ' }
            val nickName = rNickName.getText().toString().trim { it <= ' ' }
            val doctorName = rDoctorName.getText().toString().trim { it <= ' ' }
            val address = rAddress.getText().toString().trim { it <= ' ' }
            val age = rAge.getText().toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(email)) {
                rEmailAddress.setError("Email is required")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                rPassword.setError("Password is required")
                return@OnClickListener
            }
            if (password.length < 8) {
                rPassword.setError("Password must be at least 8 character")
                return@OnClickListener
            }
            rProgressBar.setVisibility(View.VISIBLE)

            // Register using Firebase
            fAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration Success",
                        Toast.LENGTH_SHORT
                    ).show()
                    rUserID = fAuth!!.currentUser!!.uid
                    val documentReference = fStore!!.collection("users").document(
                        rUserID!!
                    )
                    val user: MutableMap<String, Any> = HashMap()
                    user["fName"] = fullName
                    user["nName"] = nickName
                    user["dName"] = doctorName
                    user["addr"] = address
                    user["age"] = age
                    user["emailAddr"] = email
                    user["uid"] = rUserID!!
                    user["imageURL"] = "default"
                    documentReference.set(user).addOnSuccessListener {
                        Log.d(
                            "myDebug",
                            "onSuccess : user profile is created for $rUserID"
                        )
                    }
                        .addOnFailureListener { e -> Log.d("myDebug", "onFailure : $e") }
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Error ! " + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    rProgressBar.setVisibility(View.INVISIBLE)
                }
            }
        })
        rLogin.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        })
    }
}