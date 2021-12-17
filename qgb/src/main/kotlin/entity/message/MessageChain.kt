package xyz.cuya.qgb.entity.message


import kotlinx.serialization.Serializable

class MessageChain(
    content: String? = null,
    embed: MessageEmbed? = null,
    image: String? = null,
    msg_id: String? = null,
) {

    val message = Message(content, embed, image, msg_id)

    init {
        if (content.isNullOrEmpty() && embed == null && image == null && msg_id == null) {
            throw NullPointerException("message body is null")
        }
    }

    @Serializable
    data class Message(
        val content: String? = null,
        val embed: MessageEmbed? = null,
        val image: String? = null,
        val msg_id: String? = null,
    )
}