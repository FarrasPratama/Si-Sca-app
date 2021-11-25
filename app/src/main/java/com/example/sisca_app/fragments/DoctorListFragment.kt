package com.example.sisca_app.fragments

import androidx.recyclerview.widget.RecyclerView
import com.example.sisca_app.Models.UsersModel
import com.google.firebase.auth.FirebaseUser
import com.example.sisca_app.Adapters.UserAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.sisca_app.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.CollectionReference
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.DocumentSnapshot
import java.util.ArrayList

class DoctorListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var usersList: MutableList<UsersModel>
    private val fUser: FirebaseUser? = null
    private var uAdapter: UserAdapter? = null
    private var fAuth: FirebaseAuth? = null
    private var fStore: FirebaseFirestore? = null
    private var userId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_doctor__list, container, false)
        recyclerView = view.findViewById(R.id.recyclerview_doctors)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        userId = fAuth!!.currentUser!!.uid
        displayUsers()
        return view
    }

    private fun displayUsers() {
        usersList = ArrayList()
        val reference = fStore!!.collection("users")
        reference.get().addOnCompleteListener { task ->
            usersList.clear()
            if (task.isSuccessful) {
                Log.d("myDebug", " reference success")
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