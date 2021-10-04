package com.example.loadmoredemo.ApiModel

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Image(
    val file_index: Int,
    val image: String?,
    val product_id: Int,
    val static_file_id: Int
) : Serializable