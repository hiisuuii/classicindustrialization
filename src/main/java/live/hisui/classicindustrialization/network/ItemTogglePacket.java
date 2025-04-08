package live.hisui.classicindustrialization.network;

import io.netty.buffer.ByteBuf;
import live.hisui.classicindustrialization.ClassicIndustrialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ItemTogglePacket(int slot) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ItemTogglePacket> TYPE = new CustomPacketPayload.Type<>(ClassicIndustrialization.modLoc("item_toggle"));

    public static final StreamCodec<ByteBuf, ItemTogglePacket> CODEC = StreamCodec.composite(ByteBufCodecs.INT,
            ItemTogglePacket::slot, ItemTogglePacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
