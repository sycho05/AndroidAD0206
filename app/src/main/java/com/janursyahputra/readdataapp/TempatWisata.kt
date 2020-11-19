package com.janursyahputra.readdataapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TempatWisata(
    var id: Int,
    var nama: String,
    var alamat: String,
    var deskripsi: String,
    var gambar: String
): Parcelable