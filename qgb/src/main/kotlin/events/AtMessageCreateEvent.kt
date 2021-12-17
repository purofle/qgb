package xyz.cuya.qgb.events

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cuya.qgb.context.QGBContext
import xyz.cuya.qgb.entity.Author
import xyz.cuya.qgb.entity.Member
import xyz.cuya.qgb.entity.message.MessageChain
import xyz.cuya.qgb.entity.message.MessageEmbed
import xyz.cuya.qgb.utils.JsonUtils.objectToJsonString
import xyz.cuya.qgb.utils.Log

@Serializable
data class AtMessageCreateEvent(
    @SerialName("author")
    val author: Author,
    @SerialName("channel_id")
    val channel_id: String,
    @SerialName("content")
    val content: String,
    @SerialName("guild_id")
    val guild_id: String,
    @SerialName("id")
    val id: String,
    @SerialName("member")
    val member: Member,
    @SerialName("mentions")
    val mentions: List<Author>,
    @SerialName("timestamp")
    val timestamp: String
) {
    fun messageContent() = content.replace(Regex("<@!\\d+>"), "")
    fun reply(
        content: String? = null,
        embed: MessageEmbed? = null,
        image: String? = null,
        msg_id: String? = null,
    ) {
        runBlocking {
            val message = MessageChain(content, embed, image, msg_id).message
            Log.logger.info("[$channel_id]发送消息：${message}")
            QGBContext.api.post<String>("/channels/${channel_id}/messages", message.objectToJsonString())
        }
    }
}