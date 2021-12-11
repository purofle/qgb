# qq-guild-bot
> **目前极其不完善**

一个简单的QQ频道机器人的Kotlin实现。
已经实现的功能：
```
AT消息处理
```
使用：
```kotlin
import xyz.cuya.qgb.QGB

suspend fun main() {
    val qgb = QGB(你的appid, 你的token)
    qgb.startWebSocket({
        if (it.messageContent() == " test") { // it.messageContent()可获取到消息
            it.reply("hello world!")
        }
    })
}
```
如果您已申请不验证语料，那么在群内发送`@机器人名称 test`则会回复`Hello World`.
