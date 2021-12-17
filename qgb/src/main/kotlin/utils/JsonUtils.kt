package xyz.cuya.qgb.utils

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object JsonUtils {
    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        explicitNulls = false
    }

    inline fun <reified T> String.toJsonObject(): T {
        return json.decodeFromString(this)
    }

    inline fun <reified T : Any> T.objectToJsonString(): String {
        return json.encodeToString(this)
    }
}