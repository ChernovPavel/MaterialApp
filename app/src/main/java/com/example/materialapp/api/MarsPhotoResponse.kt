package com.example.materialapp.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarsPhotoResponse(
    @SerializedName("photos")
    val photos: List<PhotoDTO>?
) : Parcelable

@Parcelize
data class PhotoDTO(
    @SerializedName("id")
    var id: Int?,

    @SerializedName("sol")
    var sol: Int?,

    @SerializedName("camera")
    var camera: Camera?,

    @SerializedName("img_src")
    var imgSrc: String?,

    @SerializedName("earth_date")
    var earthDate: String?,

    @SerializedName("rover")
    var rover: Rover?
) : Parcelable


@Parcelize
data class Camera(
    @SerializedName("id")
    var id: Int?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("rover_id")
    var roverId: Int?,

    @SerializedName("full_name")
    var fullName: String?
) : Parcelable


@Parcelize
data class Rover(
    @SerializedName("id")
    var id: Int?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("landing_date")
    var landingDate: String?,

    @SerializedName("launch_date")
    var launchDate: String?,

    @SerializedName("status")
    var status: String?
) :
    Parcelable