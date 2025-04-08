package live.hisui.classicindustrialization.network;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = ClassicIndustrialization.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Payloads {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(ItemTogglePacket.TYPE, ItemTogglePacket.CODEC,
                ServerPayloadHandler::handleItemTogglePacket);
        registrar.playToServer(UpdateInputPacket.TYPE, UpdateInputPacket.CODEC,
                ServerPayloadHandler::handleUpdateInputPacket);
    }
}
