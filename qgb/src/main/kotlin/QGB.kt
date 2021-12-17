package xyz.cuya.qgb

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import xyz.cuya.qgb.context.QGBContext
import xyz.cuya.qgb.events.AtMessageCreateEvent
import xyz.cuya.qgb.events.BotEvent
import xyz.cuya.qgb.events.ReadyEvent
import xyz.cuya.qgb.network.api.API
import xyz.cuya.qgb.network.data.APIDomain

/**
 * 一切的起点。
 * @param appId 申请机器人得到的appid
 * @param appToken 申请机器人得到的appToken
 * @param apiDomain 运行环境，可选[沙盒][APIDomain.Sandbox]或[正式环境][APIDomain.Official]。默认为沙盒环境。
 * @see QGB.startWebSocket
 */
class QGB(
    appId: Int,
    appToken: String,
    apiDomain: APIDomain = APIDomain.Sandbox
) {
    init {
        QGBContext.config = QGBConfig(appId, appToken)
        val api = API(apiDomain)
        QGBContext.api = api
    }

    /**
     * 启动websocket。
     * @param onAtMessageEvent 被AT时触发
     * @param onReadyEvent 准备好时触发
     */
    suspend fun startWebSocket(
        onAtMessageEvent: (AtMessageCreateEvent) -> Unit = {},
        onReadyEvent: (ReadyEvent) -> Unit = {},
        scope: CoroutineScope = CoroutineScope(Dispatchers.IO + QGBContext.websocketJob)
    ) {
        websocket(officialEvents = listOf(object : BotEvent() {
            override suspend fun onAtMessage(data: AtMessageCreateEvent) {
                onAtMessageEvent(data)
            }

            override suspend fun onReady(data: ReadyEvent) {
                onReadyEvent(data)
            }
        }), scope = scope)
    }
}