package xyz.cuya.qgb.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Heartbeat(
    @SerialName("d")
    val count: Long? = null
) : Operation(OPCode.Heartbeat)