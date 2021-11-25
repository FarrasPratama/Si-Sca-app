package com.example.sisca_app.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sisca_app.R

class ListPenyakitAdapter(private val listpenyakit: ArrayList<DataPenyakit>) : RecyclerView.Adapter<ListPenyakitAdapter.ListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_penyakit,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val Disease = listpenyakit[position]
        Glide.with(holder.itemView.context)
            .load(Disease.photo)
            .apply(RequestOptions().override(55,55))
            .into(holder.tvGambarPenyakit)
            holder.tvDiseaseName.text = Disease.namapenyakit
            holder.tvDiseaseDetail.text = Disease.detail
            holder.tvDiseaseCure.text = Disease.jenispengobatan
            holder.tvDiseaseLevel.text = Disease.tingkatbahaya
    }

    override fun getItemCount(): Int {
        return listpenyakit.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDiseaseName: TextView = itemView.findViewById(R.id.tv_disease_name)
        var tvDiseaseDetail: TextView = itemView.findViewById(R.id.tv_disease_detail)
        var tvDiseaseCure: TextView = itemView.findViewById(R.id.tv_disease_cure)
        var tvDiseaseLevel: TextView = itemView.findViewById(R.id.tv_disease_level)
        var tvGambarPenyakit: ImageView = itemView.findViewById(R.id.img_item_photo)


    }


}