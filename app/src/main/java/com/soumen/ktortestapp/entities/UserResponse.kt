package com.soumen.ktortestapp.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("page") val page: Int
    , @SerialName("per_page") val per_page: Int
    , @SerialName("total") val total: Int
    , @SerialName("total_pages") val total_pages: Int
    , @SerialName("data") val users: List<UserEntity>
    , @SerialName("support") val support: SupportEntity
)