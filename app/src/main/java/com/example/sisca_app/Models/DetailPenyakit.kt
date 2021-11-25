package com.example.sisca_app.Models

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.sisca_app.Adapters.DataPenyakit
import com.example.sisca_app.R

class DetailPenyakit : AppCompatActivity(), View.OnClickListener {

    private lateinit var gambardetailpenyakit : ImageView
    private lateinit var tvnamapenyakit : TextView
    private lateinit var tvdetailpenyakit : TextView
    private lateinit var tvjenisobat : TextView
    private lateinit var tvbahayapenyakit: TextView

companion object {
const val EXTRA_DISEASE ="extra_disease"

}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_penyakit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val btnmaps: Button = findViewById(R.id.btn_maps)
        btnmaps.setOnClickListener(this)
        gambardetailpenyakit = findViewById(R.id.gambar_detail_penyakit)
        tvnamapenyakit = findViewById(R.id.detil_nama_penyakit)
        tvjenisobat = findViewById(R.id.detil_obat_penyakit)
        tvbahayapenyakit = findViewById(R.id.detil_bahaya_penyakit)
        tvdetailpenyakit = findViewById(R.id.biografi_penyakit)

        val intent = intent.getParcelableExtra<DataPenyakit>(EXTRA_DISEASE)

            Glide.with(this).load(intent?.photo).into(gambardetailpenyakit)
            tvnamapenyakit.text = intent?.namapenyakit
            tvjenisobat.text = intent?.jenispengobatan
            tvbahayapenyakit.text = intent?.tingkatbahaya
            tvdetailpenyakit.text = intent?.detail


    }

    override fun onClick(p0: View?) {
            when(p0?.id) {
                R.id.btn_maps -> {
                val IntentUri = Uri.parse("geo:-7.9797,112.6304?q=dokter")

                val mapIntent = Intent(Intent.ACTION_VIEW,IntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)
                }
            }
    }
}