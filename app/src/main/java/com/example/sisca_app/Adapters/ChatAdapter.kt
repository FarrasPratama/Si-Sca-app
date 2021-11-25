package com.example.sisca_app.Adapters

import android.content.Context
import com.example.sisca_app.Models.ChatsModel
import androidx.recyclerview.widget.RecyclerView
import com.example.sisca_app.Adapters.ChatAdapter.MyViewHolder
import android.view.ViewGroup
import com.example.sisca_app.Adapters.ChatAdapter
import android.view.LayoutInflater
import android.view.View
import com.example.sisca_app.R
import android.widget.TextView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth

class ChatAdapter(var context: Context, var chatsList: List<ChatsModel>) :
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return if (viewType == MESSAGE_RIGHT) {
            val view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false)
            MyViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false)
            MyViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val chats = chatsList[position]
        holder.messageText.text = chats.message
    }

    override fun getItemCount(): Int {
        return chatsList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var messageText: TextView

        init {
            messageText = itemView.findViewById(R.id.text_message_body)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val user = FirebaseAuth.getInstance().currentUser!!
        return if (chatsList[position].sender == user.uid) {
            MESSAGE_RIGHT
        } else {
            MESSAGE_LEFT
        }
    }

    companion object {
        const val MESSAGE_RIGHT = 0 // For Me
        const val MESSAGE_LEFT = 1 // For Friend
    }
}