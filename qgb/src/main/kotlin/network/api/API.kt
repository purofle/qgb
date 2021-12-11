package xyz.cuya.qgb.network.api

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocketListener
import xyz.cuya.qgb.context.QGBContext
import xyz.cuya.qgb.network.data.APIDomain

class API(apiDomain: APIDomain) {
    private val baseURL: String = apiDomain.domain
    private val client = HttpClient(OkHttp)
    private val okhttpClient = OkHttpClient.Builder().build()
    private lateinit var wss: String

    suspend fun get(api: String): String {
        return client.get("$baseURL$api") {
            headers {
                append(HttpHeaders.Authorization, QGBContext.config.bot_token)
            }
        }
    }

    suspend fun getWSS() {
        Json.decodeFromString<JsonObject>(get("/gateway"))["url"]!!.jsonPrimitive.content.also {
            wss = it
        }
    }

    suspend fun newWebsocket(
        scope: CoroutineScope = CoroutineScope(Dispatchers.IO + QGBContext.websocketJob),
        listener: WebSocketListener
    ) {
        getWSS() // 获取wss接入点
        val request = Request.Builder()
            .url(wss.replace("wss://", "https://"))
            .build()
        scope.launch {
            okhttpClient.newWebSocket(request, listener)
        }
    }
}