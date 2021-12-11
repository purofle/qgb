package xyz.cuya.qgb.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cuya.qgb.entity.Author
import xyz.cuya.qgb.entity.Member

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
}