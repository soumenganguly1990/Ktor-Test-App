package com.soumen.ktortestapp.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    @SerialName("id") val id: Int
    , @SerialName("email") val email: String
    , @SerialName("first_name") val first_name: String
    , @SerialName("last_name") val last_name: String
    , @SerialName("avatar") val avatar: String
) {
    override fun toString(): String {
        return "$id - $first_name - $last_name - $email"
    }
}