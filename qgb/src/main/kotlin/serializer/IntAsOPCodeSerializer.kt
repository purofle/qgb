package xyz.cuya.qgb.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import xyz.cuya.qgb.network.data.OPCode

object IntAsOPCodeSerializer : KSerializer<OPCode> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("opcode", PrimitiveKind.INT)
    override fun deserialize(decoder: Decoder) = OPCode.from(decoder.decodeInt())

    override fun serialize(encoder: Encoder, value: OPCode) {
        encoder.encodeInt(value.code)
    }

}