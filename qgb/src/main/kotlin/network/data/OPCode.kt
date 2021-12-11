package xyz.cuya.qgb.network.data

@Suppress("unused")

enum class OPCode(val code: Int) {
    Dispatch(0),
    Heartbeat(1),
    Identify(2),
    Resume(6),
    Reconnect(7),
    InvalidSession(9),
    Hello(10),
    HeartbeatACK(11);

    companion object {
        fun from(code: Int): OPCode {
            return values().first { it.code == code }
        }
    }
}