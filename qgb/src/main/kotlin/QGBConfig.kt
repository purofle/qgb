package xyz.cuya.qgb

data class QGBConfig(
    val app_id: Int,
    val token: String,
    val bot_token: String = "Bot $app_id.$token"
)
