package xyz.cuya.qgb.network.data

/**
 * 接口域名配置，分为[正式环境](https://api.sgroup.qq.com)和[沙箱环境](https://sandbox.api.sgroup.qq.com)。
 *
 * 沙箱环境：沙箱环境只会收到测试频道的事件，且调用openapi仅能操作测试频道
 */
enum class APIDomain(val domain: String) {
    Official("https://api.sgroup.qq.com"),
    Sandbox("https://sandbox.api.sgroup.qq.com")
}