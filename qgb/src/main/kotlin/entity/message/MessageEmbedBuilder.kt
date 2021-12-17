package xyz.cuya.qgb.entity.message

fun messageEmbed(block: MessageEmbedBuilder.() -> Unit): MessageEmbed {
    val builder = MessageEmbedBuilder()
    builder.block()
    return builder.messageEmbed()
}

@MessageEmbedDslMarker
class MessageEmbedBuilder {
    var title: String = ""
    var prompt: String = ""
    var thumbnail: String = ""

    private lateinit var fieldsArray: ArrayList<MessageEmbedField>
    fun fields(block: Fields.() -> Unit) {
        val fields = Fields()
        fields.block()
        fieldsArray = fields.messageEmbedField()
    }

    fun messageEmbed(): MessageEmbed {
        return MessageEmbed(
            title,
            prompt = prompt.ifEmpty { title },
            thumbnail = if (thumbnail.isNotEmpty()) { MessageEmbedThumbnail(thumbnail) } else { null },
            fieldsArray
        )
    }

}

class Field {
    var content = ""
    fun messageEmbedField() = MessageEmbedField(content)
}

@MessageEmbedDslMarker
class Fields {
    private val children = ArrayList<Field>()
    fun field(block: Field.() -> String) {
        val field = Field()
        field.content = field.block()
        children.add(field)
    }

    fun messageEmbedField(): ArrayList<MessageEmbedField> {
        val fields = ArrayList<MessageEmbedField>()
        children.forEach {
            fields.add(it.messageEmbedField())
        }
        return fields
    }
}

@DslMarker
annotation class MessageEmbedDslMarker