package xyz.cuya.qgb

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import xyz.cuya.qgb.context.QGBContext
import xyz.cuya.qgb.events.AtMessageCreateEvent
import xyz.cuya.qgb.events.BotEvent
import xyz.cuya.qgb.events.ReadyEvent
import xyz.cuya.qgb.network.data.*
import xyz.cuya.qgb.network.data.DispatchEnums.AT_MESSAGE_CREATE
import xyz.cuya.qgb.network.data.DispatchEnums.READY
import xyz.cuya.qgb.utils.JsonUtils.objectToJsonString
import xyz.cuya.qgb.utils.JsonUtils.toJsonObject
import xyz.cuya.qgb.utils.ScheduleUtils
import java.util.*
import java.util.concurrent.atomic.AtomicLong

suspend fun websocket(
    scope: CoroutineScope = CoroutineScope(Dispatchers.IO + QGBContext.websocketJob),
    officialEvents: List<BotEvent> = emptyList()
) {
    QGBContext.api.newWebsocket(scope, listener = object : WebSocketListener() {
        private var hbTimer: Timer? = null
        private val messageSeq by lazy { AtomicLong(0) }
        private val lastReceivedHeartBeat = AtomicLong(0)
        private val heartbeatDelay: Long = 41250
        private val reconnectTimeout: Long = 60000
        private var isFirstConnect = false
        private var sessionId = ""

        override fun onOpen(webSocket: WebSocket, response: Response) {
            println("websocket连接已启动")
            isFirstConnect = true
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            val n = text.toJsonObject<Operation>()
            println("RAW：${text}")

            when (n.opcode) {
                OPCode.HeartbeatACK -> {
                    println("收到ack")
                    lastReceivedHeartBeat.getAndSet(System.currentTimeMillis())
                }
                OPCode.Hello -> {
                    println("收到hello包")
                    initWebsocket(webSocket)
                }
                OPCode.Dispatch -> {
                    val dispatch = text.toJsonObject<DispatchType>()
                    println("收到Dispatch包：$dispatch")
                    successConnect(dispatch)
                    when (dispatch.type) {
                        READY -> {
                            val data = text.toJsonObject<Dispatch<ReadyEvent>>().d
                            makeHeartBeat(webSocket)
                            sessionId = data.session_id
                            officialEvents.forEach {
                                runBlocking {
                                    it.onReady(data)
                                }
                            }
                        }
                        AT_MESSAGE_CREATE -> {
                            val data = text.toJsonObject<Dispatch<AtMessageCreateEvent>>().d
                            officialEvents.forEach {
                                runBlocking {
                                    it.onAtMessage(data)
                                }
                            }
                        }
                        else -> TODO()
                    }
                }
                else -> {
                    println("出现未知事件 ${n.opcode} op: ${n.opcode.code}")
                    QGBContext.websocketJob.complete()
                    hbTimer?.cancel()
                    println("协程已完成")
                }
            }
        }

        private fun initWebsocket(webSocket: WebSocket) {
            val identify = Identify(IdentifyData(QGBContext.config.bot_token)).objectToJsonString()
            webSocket.send(identify)
            println("发送鉴权包")
        }

        private fun makeHeartBeat(webSocket: WebSocket) {
            println("心跳开始")
            lastReceivedHeartBeat.getAndSet(System.currentTimeMillis())
            val processor = createHeartBeatProcessor(webSocket)
            //  先取消以前的定时器
            hbTimer?.cancel()
            // 启动新的心跳
            hbTimer = ScheduleUtils.loopEvent(processor, Date(), heartbeatDelay)
        }

        private fun createHeartBeatProcessor(webSocket: WebSocket): suspend () -> Unit {
            return suspend {
                val last = lastReceivedHeartBeat.get()
                val now = System.currentTimeMillis()
                if (now - last > reconnectTimeout) {
                    println("心跳超时")
                    QGBContext.websocketJob.cancel("超时")
                    hbTimer?.cancel()
                    println("协程已完成")
                } else {
                    val hb = Heartbeat(messageSeq.get()).objectToJsonString()
//                    val hb = Operation(OPCode.Heartbeat).objectToJsonString()
                    println(webSocket.send(hb))
                    println("发送心跳 $hb")
                }
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            println(t.localizedMessage)
            hbTimer?.cancel()
            QGBContext.websocketJob.cancel(t.localizedMessage)

        }

        private fun successConnect(dispatchDto: DispatchType) {
            messageSeq.getAndSet(dispatchDto.seq)
        }

    })
    QGBContext.websocketJob.join()
}