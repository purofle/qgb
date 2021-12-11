package xyz.cuya.qgb.events

abstract class BotEvent {
    open suspend fun onReady(data: ReadyEvent) {}
    open suspend fun onAtMessage(data: AtMessageCreateEvent) {}
}