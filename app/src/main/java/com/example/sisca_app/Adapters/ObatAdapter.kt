package com.example.sisca_app.Adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sisca_app.R

class ObatAdapter (private val listobat: ArrayList<DataPenyakit>):
    RecyclerView.Adapter<ObatAdapter.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var obatName: TextView = itemView.findViewById(R.id.tv_obat_name)
        var jenisPenyakit: TextView = itemView.findViewById(R.id.tv_jenis_penyakit)
        var btnCari: Button = itemView.findViewById(R.id.btnCari)

        override fun onClick(p0: View?) {
            val intentUri = Uri.parse("geo:-7.9797,112.6304?q=pharmacy")
            val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            itemView.context.startActivity(mapIntent)
        }

    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data:DataPenyakit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_obat, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val disease = listobat[position]
        Glide.with(holder.itemView.context)
//            .load(Disease.photo)
//            .apply(RequestOptions().override(350,350))
//            .into(holder.tvGambarPenyakit)
        holder.obatName.text = disease.jenispengobatan
        holder.jenisPenyakit.text = disease.namapenyakit
        holder.btnCari.setOnClickListener {
            onItemClickCallback.onItemClicked(listobat[holder.adapterPosition])
        }
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listobat[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listobat.size
    }
}