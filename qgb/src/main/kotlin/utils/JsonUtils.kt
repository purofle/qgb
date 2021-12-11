package xyz.cuya.qgb.utils

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object JsonUtils {
    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    inline fun <reified T> String.toJsonObject(): T {
        return json.decodeFromString(this)
    }

    inline fun <reified T : Any> T.objectToJsonString(): String {
        return json.encodeToString(this)
    }
}