package com.example.sisca_app

import android.app.Dialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sisca_app.R
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private var imuStatus: String? = null
    private lateinit var startDefib: ImageView
    private lateinit var cancelDefib: ImageView
    private var dialog: Dialog? = null
    private var relayState = "OFF"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dialog = Dialog(this)
        val relayRef = FirebaseDatabase.getInstance().reference.child("Relay")
        relayRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                relayState = snapshot.child("relayState").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        val reference = FirebaseDatabase.getInstance().reference.child("Sensor")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                imuStatus = snapshot.child("imuSensorStatus").value.toString()
                if (imuStatus!!.trim { it <= ' ' } == "FALL") {
                    Toast.makeText(this@MainActivity, "Patient Falling", Toast.LENGTH_SHORT).show()
                    openAlertDialog()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation)
        val navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    private fun openAlertDialog() {
        dialog!!.setContentView(R.layout.alert_layout)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        startDefib = dialog!!.findViewById(R.id.defib_start_button)
        cancelDefib = dialog!!.findViewById(R.id.defib_cancel_button)
        startDefib.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@MainActivity, "Defibrilator triggered", Toast.LENGTH_SHORT).show()
            if (relayState.trim { it <= ' ' } == "OFF") {
                relayState = "ON"
                sendRelayCommand(relayState)
                dialog!!.dismiss()
            }
        })
        cancelDefib.setOnClickListener(View.OnClickListener {
            relayState = "OFF"
            sendRelayCommand(relayState)
            dialog!!.dismiss()
            Toast.makeText(this@MainActivity, "Patient is safe", Toast.LENGTH_SHORT).show()
        })
        dialog!!.show()
    }

    private fun sendRelayCommand(relayState: String) {
        val reference = FirebaseDatabase.getInstance().reference.child("Relay")
        reference.child("relayState").setValue(relayState)
    }
}