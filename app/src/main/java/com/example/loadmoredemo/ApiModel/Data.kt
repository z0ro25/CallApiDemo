package com.example.loadmoredemo.ApiModel

import android.os.Parcel
import android.os.Parcelable

data class Data(
    val content: List<Content>? = null,
    val page: Int ? = null,
    val size: Int? = null,
    val total_elements: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        TODO("content"),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}