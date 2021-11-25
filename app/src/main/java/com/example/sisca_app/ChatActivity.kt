package com.example.sisca_app

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.widget.EditText
import com.example.sisca_app.Models.ChatsModel
import com.example.sisca_app.Adapters.ChatAdapter
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import com.example.sisca_app.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.example.sisca_app.Models.UsersModel
import com.bumptech.glide.Glide
import android.text.TextWatcher
import android.text.Editable
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import de.hdodenhof.circleimageview.CircleImageView
import java.util.ArrayList
import java.util.HashMap

class ChatActivity : AppCompatActivity() {
    lateinit var friendId: String
    lateinit var message: String
    lateinit var myId: String
    lateinit var imageViewOnToolbar: CircleImageView
    lateinit var usernameOnToolbar: TextView
    lateinit var toolbar: Toolbar
    var fStore: FirebaseFirestore? = null
    var fAuth: FirebaseAuth? = null
    var fUser: FirebaseUser? = null
    lateinit var et_messages: EditText
    lateinit var sendButton: Button
    lateinit var chatList: MutableList<ChatsModel>
    lateinit var cAdapter: ChatAdapter
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        toolbar = findViewById(R.id.toolbar_message)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        imageViewOnToolbar = findViewById(R.id.profile_image_toolbar_message)
        usernameOnToolbar = findViewById(R.id.username_ontoolbar_message)
        recyclerView = findViewById(R.id.recyclerview_chat)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        recyclerView.setLayoutManager(layoutManager)
        et_messages = findViewById(R.id.edit_message_text)
        sendButton = findViewById(R.id.send_button_chat)
        fStore = FirebaseFirestore.getInstance()
        fUser = FirebaseAuth.getInstance().currentUser
        myId = fUser!!.uid
        friendId = intent.getStringExtra("friendId").toString()
        fUser = FirebaseAuth.getInstance().currentUser
        val reference = fStore!!.collection("users").document(
            friendId!!
        )
        reference.addSnapshotListener(this) { value, error ->
            val user = value!!.toObject(UsersModel::class.java)
            usernameOnToolbar.setText(user!!.getnName())
            if (user.imageURL == "default") {
                imageViewOnToolbar.setImageResource(R.drawable.ic_baseline_person_24)
            } else {
                Glide.with(applicationContext).load(user.imageURL).into(imageViewOnToolbar)
            }
            readMessage(myId!!, friendId)
        }
        et_messages.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (s.toString().length > 0) {
                    sendButton.setEnabled(true)
                } else {
                    sendButton.setEnabled(false)
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val text = et_messages.getText().toString()
                if (!text.startsWith(" ")) {
                    et_messages.getText().insert(0, " ")
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        sendButton.setOnClickListener(View.OnClickListener {
            message = et_messages.getText().toString()
            sendMessage(myId!!, friendId, message!!)
            et_messages.setText(" ")
        })
    }

    private fun readMessage(myId: String, friendId: String?) {
        chatList = ArrayList()
        val reference = FirebaseDatabase.getInstance().getReference("Chats")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for (ds in snapshot.children) {
                    val chats = ds.getValue(ChatsModel::class.java)
                    if (chats!!.sender == myId && chats.receiver == friendId ||
                        chats.sender == friendId && chats.receiver == myId
                    ) {
                        chatList.add(chats)
                    }
                    cAdapter = ChatAdapter(this@ChatActivity, chatList)
                    recyclerView!!.adapter = cAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun sendMessage(myId: String, friendId: String?, message: String) {

        // Using Realtime Database
        val reference = FirebaseDatabase.getInstance().reference
        val hashMap = HashMap<String, Any?>()
        hashMap["sender"] = myId
        hashMap["receiver"] = friendId
        hashMap["message"] = message
        reference.child("Chats").push().setValue(hashMap)
    }
}