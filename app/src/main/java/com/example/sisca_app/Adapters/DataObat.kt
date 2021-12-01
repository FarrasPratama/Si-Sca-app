package com.example.sisca_app.Adapters

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataObat(var namaobat : String=  "",
            var jenisobat : String = "",
            var ketersediaanobat : String = "",
            var deskripsiobat : String = "",
            var photoobat : Int=0):Parcelable {


}