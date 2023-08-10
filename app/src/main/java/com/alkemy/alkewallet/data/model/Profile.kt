package com.alkemy.alkewallet.data.model

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("first_name")
    var name: String,
    @SerializedName("last_name")
    var lastName: String,
    var email: String,
    var password: String,
    var roleId: Int,
    var points: Int
)

data class User(val displayName: String, val email: String)