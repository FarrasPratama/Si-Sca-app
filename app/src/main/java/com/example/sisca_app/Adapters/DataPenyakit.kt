package com.example.sisca_app.Adapters
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class DataPenyakit(var namapenyakit: String = "",
                        var detail: String = "",
                        var jenispengobatan : String = "",
                        var tingkatbahaya : String = "",
                        var photo: Int = 0):Parcelable {
}