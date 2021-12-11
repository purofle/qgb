package xyz.cuya.qgb.entity

import kotlinx.serialization.Serializable

@Serializable
data class Member(
    val joined_at: String? = null,
    val roles: ArrayList<String>? = arrayListOf()
)
