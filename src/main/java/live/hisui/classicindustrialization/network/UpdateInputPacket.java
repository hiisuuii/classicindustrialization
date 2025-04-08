package live.hisui.classicindustrialization.network;

import io.netty.buffer.ByteBuf;
import live.hisui.classicindustrialization.ClassicIndustrialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public record UpdateInputPacket(Vector3f motion) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<UpdateInputPacket> TYPE = new CustomPacketPayload.Type<>(ClassicIndustrialization.modLoc("update_input"));

    public static final StreamCodec<ByteBuf, UpdateInputPacket> CODEC = StreamCodec.composite(ByteBufCodecs.VECTOR3F,
            UpdateInputPacket::motion, UpdateInputPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
