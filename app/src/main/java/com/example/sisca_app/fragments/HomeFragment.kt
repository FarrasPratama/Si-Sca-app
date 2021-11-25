package com.example.sisca_app.fragments

import android.content.Intent
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sisca_app.Adapters.DataPenyakit
import com.example.sisca_app.Adapters.ListPenyakitAdapter
import com.example.sisca_app.Models.DetailPenyakit
import com.example.sisca_app.Models.DetailPenyakit.Companion.EXTRA_DISEASE
import com.example.sisca_app.Models.diseasedata
import com.example.sisca_app.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException

class HomeFragment : Fragment() {
    private lateinit var rvdisease:RecyclerView
    private var list: ArrayList<DataPenyakit> = arrayListOf()
    private var patientNickName: TextView? = null
    private var bpmValue: TextView? = null
    private var conditionValue: TextView? = null
    private var fAuth: FirebaseAuth? = null
    private var fStore: FirebaseFirestore? = null
    private var userId: String? = null
    private var bpm: String? = null
    private var relayButton: Button? = null
    private var relayState = "OFF"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (container == null) return null
        val view = inflater.inflate(R.layout.fragment_home, container, false) as RelativeLayout
        patientNickName = view.findViewById<View>(R.id.patient_home_name) as TextView
        rvdisease = view.findViewById(R.id.rv_penyakit)
        rvdisease.setHasFixedSize(true)
        list.addAll(diseasedata.listdata)
        showRecyclerList()
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        userId = fAuth!!.currentUser!!.uid
        val documentReference = fStore!!.collection("users").document(
            userId!!
        )
        documentReference.addSnapshotListener(requireActivity()) { value, error ->
            patientNickName!!.text = value!!.getString("nName")
        }
        val reference = FirebaseDatabase.getInstance().reference.child("Sensor")


        return view


    }

    private fun showSelectedDisease(data: DataPenyakit) {
        Toast.makeText(requireActivity(),"Anda Memilih "+data.namapenyakit,Toast.LENGTH_SHORT).show()
    }

    private fun showRecyclerList() {
        rvdisease.layoutManager = LinearLayoutManager(requireActivity())
        val listpenyakitadapter = ListPenyakitAdapter(list)
        rvdisease.adapter = listpenyakitadapter

        listpenyakitadapter.setOnItemClickCallback(object : ListPenyakitAdapter.OnItemClickCallback{
            override fun onItemClicked(data: DataPenyakit) {
                val diseaseParcel = DataPenyakit(
                    data.namapenyakit,
                    data.detail,
                    data.jenispengobatan,
                    data.tingkatbahaya,
                    data.photo
                )
                val moveintent = Intent(this@HomeFragment.requireContext(), DetailPenyakit::class.java)
                    moveintent.putExtra(EXTRA_DISEASE,diseaseParcel)
                startActivity(moveintent)

                showSelectedDisease(data)
            }
        })
    }


}