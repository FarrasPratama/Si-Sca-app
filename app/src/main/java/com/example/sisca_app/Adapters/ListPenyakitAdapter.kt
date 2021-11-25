package com.example.sisca_app.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sisca_app.R

class ListPenyakitAdapter(private val listpenyakit: ArrayList<DataPenyakit>) : RecyclerView.Adapter<ListPenyakitAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_penyakit,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val Disease = listpenyakit[position]
        Glide.with(holder.itemView.context)
            .load(Disease.photo)
            .apply(RequestOptions().override(350,350))
            .into(holder.tvGambarPenyakit)
            holder.tvDiseaseName.text = Disease.namapenyakit
            holder.tvDiseaseDetail.text = Disease.detail
            holder.tvDiseaseCure.text = Disease.jenispengobatan
            holder.tvDiseaseLevel.text = Disease.tingkatbahaya
            holder.btndetails.setOnClickListener{
                onItemClickCallback.onItemClicked(listpenyakit[holder.adapterPosition])
                //Toast.makeText(holder.itemView.context, "Anda Memilih " + listpenyakit[holder.adapterPosition].namapenyakit,Toast.LENGTH_SHORT).show()
            }
            holder.itemView.setOnClickListener{
                onItemClickCallback.onItemClicked(listpenyakit[holder.adapterPosition])
            }
    }

    override fun getItemCount(): Int {
        return listpenyakit.size
    }

    interface OnItemClickCallback{
        fun onItemClicked(data:DataPenyakit)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDiseaseName: TextView = itemView.findViewById(R.id.tv_disease_name)
        var tvDiseaseDetail: TextView = itemView.findViewById(R.id.tv_disease_detail)
        var tvDiseaseCure: TextView = itemView.findViewById(R.id.tv_disease_cure)
        var tvDiseaseLevel: TextView = itemView.findViewById(R.id.tv_disease_level)
        var tvGambarPenyakit: ImageView = itemView.findViewById(R.id.gambar_penyakit)
        var btndetails: Button = itemView.findViewById(R.id.btn_see_details)


    }


}