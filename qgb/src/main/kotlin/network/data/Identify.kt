package xyz.cuya.qgb.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Identify(
    val d: IdentifyData
) : Operation(OPCode.Identify)

@Serializable
data class IdentifyData(
    val token: String,
    val intents: Long = 1610612739L, // all
    val shard: List<Int> = listOf(0, 1), //代表分为1个片，当前链接是第0片
    val properties: Properties = Properties()
)

@Serializable
data class Properties(
    @SerialName("\$browser")
    val browser: String = "okhttp",
    @SerialName("\$device")
    val device: String = "qgb",
    @SerialName("\$os")
    val os: String = System.getProperty("os.name").split(" ").firstOrNull() ?: "unknown"
)