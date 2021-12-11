package xyz.cuya.qgb.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cuya.qgb.serializer.IntAsOPCodeSerializer

@Serializable
open class Operation(
    @Serializable(with = IntAsOPCodeSerializer::class)
    @SerialName("op") val opcode: OPCode
)
