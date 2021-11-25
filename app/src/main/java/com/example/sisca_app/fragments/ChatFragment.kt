package com.example.sisca_app.fragments

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sisca_app.Models.UsersModel
import com.example.sisca_app.Adapters.UserAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.example.sisca_app.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.CollectionReference
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.QuerySnapshot
import java.util.ArrayList

class ChatFragment : Fragment() {
    private lateinit var patientNickName: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var usersList: MutableList<UsersModel>
    private var uAdapter: UserAdapter? = null
    private var fAuth: FirebaseAuth? = null
    private var fStore: FirebaseFirestore? = null
    private var userId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (container == null) return null
        val view = inflater.inflate(R.layout.fragment_chat, container, false) as RelativeLayout
        patientNickName = view.findViewById<View>(R.id.patient_chat_name) as TextView
        recyclerView = view.findViewById(R.id.recyclerview_users)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        userId = fAuth!!.currentUser!!.uid
        val documentReference = fStore!!.collection("users").document(
            userId!!
        )
        documentReference.addSnapshotListener(requireActivity()) { value, error ->
            patientNickName!!.text = value!!.getString("nName")
        }
        displayUsers()
        return view
    }

    private fun displayUsers() {
        usersList = ArrayList()
        val reference = fStore!!.collection("users")
        reference.get().addOnCompleteListener { task ->
            usersList.clear()
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    if (document.exists()) {
                        val user = document.toObject(UsersModel::class.java)
                        if (user.uid != userId) {
                            usersList.add(user)
                        }
                        uAdapter = UserAdapter(requireContext(), usersList)
                        recyclerView!!.adapter = uAdapter
                    }
                }
            }
        }
    }
}