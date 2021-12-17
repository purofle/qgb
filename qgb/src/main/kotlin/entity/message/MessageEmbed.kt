package xyz.cuya.qgb.entity.message

import kotlinx.serialization.Serializable

/**
 * Embed是消息支持的一种特殊的格式，支持部分富文本格式。
 *
 * - [MessageEmbed.thumbnail] 为选填，没有缩略图的可以不填。
 * - [MessageEmbed.fields]为消息内容，一个内容是一行。其中 [MessageEmbedThumbnail] 是文本。
 * @param title 标题
 * @param prompt 消息弹窗内容
 * @param thumbnail 缩略图
 * @param fields 具体内容
 */
@Serializable
data class MessageEmbed(
    val title: String,
    val prompt: String,
    val thumbnail: MessageEmbedThumbnail? = null,
    val fields: ArrayList<MessageEmbedField>
)

/**
 * 缩略图
 * @param url 图片地址
 */
@Serializable
data class MessageEmbedThumbnail(
    val url: String
    )

/**
 * 内容数组，一个[MessageEmbedThumbnail]是一行
 * @param name 文本
 */
@Serializable
data class MessageEmbedField(
    val name: String,
)