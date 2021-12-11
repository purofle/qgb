package xyz.cuya.qgb.network.data

import kotlinx.serialization.SerialName

import kotlinx.serialization.Serializable

@Serializable
open class Dispatch<T>(
    @SerialName("s")
    val seq: Long,
    @SerialName("t")
    val type: DispatchEnums,
    @SerialName("d")
    val d: T
) : Operation(OPCode.Dispatch)

@Serializable
data class DispatchType(
    @SerialName("t")
    val type: DispatchEnums,
    @SerialName("s")
    val seq: Long,
)