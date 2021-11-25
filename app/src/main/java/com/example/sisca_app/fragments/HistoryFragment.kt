package com.example.sisca_app.fragments

import android.widget.TextView
import android.app.DatePickerDialog.OnDateSetListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.widget.RelativeLayout
import com.example.sisca_app.R
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import android.app.DatePickerDialog
import android.view.View
import androidx.fragment.app.Fragment
import java.util.*

class HistoryFragment : Fragment() {
    private var patientNickName: TextView? = null
    private var fAuth: FirebaseAuth? = null
    private var fStore: FirebaseFirestore? = null
    private var userId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (container == null) return null
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false) as RelativeLayout

        patientNickName = view.findViewById<View>(R.id.patient_history_name) as TextView

        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        userId = fAuth!!.currentUser!!.uid
        val documentReference = fStore!!.collection("users").document(
            userId!!
        )
        documentReference.addSnapshotListener(requireActivity()) { value, error ->
            patientNickName!!.text = value!!.getString("nName")
        }
        return view
    }
}