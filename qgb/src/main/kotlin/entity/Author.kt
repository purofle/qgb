package xyz.cuya.qgb.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Author(
    @SerialName("avatar")
    val avatar: String,
    @SerialName("bot")
    val bot: Boolean,
    @SerialName("id")
    val id: String,
    @SerialName("username")
    val username: String
)