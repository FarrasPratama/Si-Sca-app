package com.example.sisca_app.Adapters

import android.content.Context
import com.example.sisca_app.Models.UsersModel
import androidx.recyclerview.widget.RecyclerView
import com.example.sisca_app.Adapters.UserAdapter.MyHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.sisca_app.R
import com.bumptech.glide.Glide
import android.widget.TextView
import android.content.Intent
import android.view.View
import com.example.sisca_app.ChatActivity
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(var context: Context, var usersList: List<UsersModel>) :
    RecyclerView.Adapter<MyHolder>() {
    var friendId: String? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_of_users, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val user = usersList[position]
        friendId = user.uid
        holder.nickname.text = user.getnName()
        if (user.imageURL == "default") {
            holder.imageView.setImageResource(R.drawable.ic_baseline_person_24)
        } else {
            Glide.with(context).load(user.imageURL).into(holder.imageView)
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var nickname: TextView
        var imageView: CircleImageView
        override fun onClick(v: View) {
            val user = usersList[adapterPosition]
            friendId = user.uid
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("friendId", friendId)
            context.startActivity(intent)
        }

        init {
            nickname = itemView.findViewById(R.id.username_userfrag)
            imageView = itemView.findViewById(R.id.image_user_userfrag)
            itemView.setOnClickListener(this)
        }
    }
}