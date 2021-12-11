package xyz.cuya.qgb.context

import kotlinx.coroutines.Job
import xyz.cuya.qgb.QGBConfig
import xyz.cuya.qgb.network.api.API

object QGBContext {
    lateinit var api: API
    lateinit var config: QGBConfig
    val websocketJob = Job()
}