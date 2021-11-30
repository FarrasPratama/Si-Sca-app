package com.example.sisca_app.Models

import com.example.sisca_app.Adapters.DataPenyakit
import com.example.sisca_app.R

object diseasedata {
    private val namaPenyakit = arrayOf("Batu Ginjal",
    "COVID-19",
    "Diare",
    "Hipertensi",
    "Malaria",
    "Migrain",
    "Pneumonia",
    "Stroke",
    "Tuberculosis",
    "Anemia")

    private val detailPenyakit = arrayOf("Batu ginjal adalah pembentukan materi keras menyerupai batu yang berasal dari mineral dan garam di ginjal",
        "COVID-19 merupakan kelompok virus yang dapat menyebabkan penyakit pada hewan atau manusia.Beberapa jenis corona virus diketahui menyebabkan infeksi saluran nafas pada manusia mulai dari batuk pilek hingga yang lebih serius seperti MERS",
    "Diare adalah penyakit yang membuat penderitanya menjadi sering buang air besar dengan kondisi tinja yang encer atau berair.Diare umumnya terjadi akibat mengonsumsi makanan dan minuman yang terkontaminasi virus atau bakteri",
    "Hipertensi atau tekanan darah tinggi adalah kondisi ketika tekanan darah di 130/80 mmHg atau lebih.Jika tidak segera ditangani,hipertensi bisa menyebabkan munculnya penyakit-penyakit serius yang mengancam nyawa",
    "Malaria adalah penyakit infeksi menular yang menyebar melalui gigitan nyamuk.Penderita malaria akan mengeluhkan gejala demam dan menggigil",
    "Migrain adalah sakit kepala yang terasa berdenyut, dan biasanya terjadi pada satu sisi kepala saja.Migrain merupakan jenis penyakit saraf",
    "Pneumonia adalah peradangan paru-paru yang disebabkan oleh infeksi.Pneumonia bisa menimbulkan gejala yang ringan hingga berat",
    "Stroke adalah kondisi yang terjadi ketika pasokan darah ke otak terganggu atau berkurang akibat penyumbatan atau pecahnya pembuluh darah",
    "Tuberkulosis adalah penyakit paru-paru akibat kuman Mycobacterium tuberculosis.TBC akan menimbulkan gejala berupa batuk yang berlangsung lama",
    "Anemia adalah kondisi ketika tubuh kekurangan sel darah merah yang sehat atau ketika sel darah merah tidak berfungsi dengan baik.Akibatnya,organ tubuh tidak mendapat cukup oksigen")

    private val jenisObat = arrayOf("Allupurinol",
        "Belum tersedia",
    "Loperamide",
    "Amlodipine",
    "Artemisinin-based combination therapies",
    "Aspirin",
    "Obat jenis antibiotik",
    "Obat jenis antiplatelet",
    "Pyrazinamide",
    "Green World Spirulina Plus")

    private val bahaya = arrayOf("Relatif tergantung kondisi penderita",
    "Fatal",
    "Menengah",
    "Fatal",
    "Fatal",
    "Ringan",
    "Menengah",
    "Fatal",
    "Fatal",
    "Relatif pada jenisnya")


private val gambarpenyakit = intArrayOf(R.drawable.batuginjal,
R.drawable.covid,
R.drawable.diare,
R.drawable.hipertensi,
R.drawable.malaria,
R.drawable.migrain,
R.drawable.pneumonia,
R.drawable.stroke,
R.drawable.tbc,
R.drawable.anemia)

    val listdata: ArrayList<DataPenyakit>
    get() {
        val list= arrayListOf<DataPenyakit>()
        for (position in namaPenyakit.indices){
        val Penyakit = DataPenyakit()
            Penyakit.namapenyakit = namaPenyakit[position]
            Penyakit.detail = detailPenyakit[position]
            Penyakit.jenispengobatan = jenisObat[position]
            Penyakit.tingkatbahaya = bahaya[position]
            Penyakit.photo = gambarpenyakit[position]
            list.add(Penyakit)
        }
return list
    }
}