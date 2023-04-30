package com.example.coroutinesexample.API.DataModel

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImgFlipResponse (
    @SerializedName("success")
    @Expose
    var success: Boolean? = null,

    @SerializedName("data")
    @Expose
    var data: Data? = null

): Parcelable