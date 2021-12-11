package xyz.cuya.qgb.entity

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val content: String,
    val msg_id: String
)
