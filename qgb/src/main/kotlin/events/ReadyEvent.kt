package xyz.cuya.qgb.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cuya.qgb.entity.User

@Serializable
data class ReadyEvent(
    @SerialName("session_id")
    val session_id: String,
    @SerialName("shard")
    val shard: List<Int>,
    @SerialName("user")
    val user: User,
    @SerialName("version")
    val version: Int
)