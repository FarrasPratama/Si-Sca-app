package com.example.sisca_app.fragments

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.widget.RelativeLayout
import com.example.sisca_app.R
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import android.graphics.Bitmap
import com.google.zxing.WriterException
import android.content.Intent
import android.graphics.Color
import com.example.sisca_app.LoginActivity
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {
    private var patientFullName: TextView? = null
    private var patientNickName: TextView? = null
    private var patientAddress: TextView? = null
    private var patientAge: TextView? = null
    private var patientDoctorName: TextView? = null
    private var showQrButton: Button? = null
    private var logoutButton: Button? = null
    private var qrDialog: Dialog? = null
    private val qrOutput: ImageView? = null
    private var fAuth: FirebaseAuth? = null
    private var fStore: FirebaseFirestore? = null
    private var userId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        if (container == null) return null
        val view = inflater.inflate(R.layout.fragment_profile, container, false) as RelativeLayout
        showQrButton = view.findViewById<View>(R.id.show_qr_button) as Button
        logoutButton = view.findViewById<View>(R.id.logout_button) as Button
        patientFullName = view.findViewById<View>(R.id.patient_name) as TextView
        patientNickName = view.findViewById<View>(R.id.patient_nickname) as TextView
        patientAddress = view.findViewById<View>(R.id.address) as TextView
        patientAge = view.findViewById<View>(R.id.age) as TextView
        patientDoctorName = view.findViewById<View>(R.id.doctor_name) as TextView
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        userId = fAuth!!.currentUser!!.uid
        qrDialog = Dialog(requireContext())
        val documentReference = fStore!!.collection("users").document(
            userId!!
        )
        documentReference.addSnapshotListener(requireActivity()) { value, error ->
            patientFullName!!.text = value!!.getString("fName")
            patientNickName!!.text = value.getString("nName")
            patientAddress!!.text = value.getString("addr")
            patientAge!!.text = value.getString("age")
            patientDoctorName!!.text = value.getString("dName")
        }
        showQrButton!!.setOnClickListener {
            val data =
                patientFullName!!.text.toString() + "," + patientAge!!.text.toString() + "," + "patient" + "," + patientAddress!!.text.toString() + "," + patientDoctorName!!.text.toString()
            val writer = MultiFormatWriter()
            try {
                val matrix = writer.encode(data, BarcodeFormat.QR_CODE, 200, 200)
                val encoder = BarcodeEncoder()
                val bitmap = encoder.createBitmap(matrix)
                loadPhoto(bitmap, 600, 600)
            } catch (e: WriterException) {
                e.printStackTrace()
            }
        }
        logoutButton!!.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            requireActivity().finish()
        }
        return view
    }

    private fun loadPhoto(bitmap: Bitmap, width: Int, height: Int) {
        val builder = AlertDialog.Builder(
            requireContext()
        )
        val imageDialog = builder.create()
        val inflater =
            requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(
            R.layout.popup_qr,
            requireActivity().findViewById<View>(R.id.qr_container) as ViewGroup
        )
        val image = layout.findViewById<View>(R.id.qr_output) as ImageView
        image.setImageBitmap(bitmap)
        imageDialog.setView(layout)
        imageDialog.show()
        imageDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        imageDialog.window!!.setLayout(width, height)
    }
}