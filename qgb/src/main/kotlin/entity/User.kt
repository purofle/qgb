package xyz.cuya.qgb.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("bot")
    val bot: Boolean,
    @SerialName("id")
    val id: String,
    @SerialName("username")
    val username: String,
    @SerialName("avatar")
    val avatar: String? = null,
    @SerialName("union_openid")
    val union_openid: String? = null,
    @SerialName("union_user_account")
    val union_user_account: String? = null
)