package live.hisui.classicindustrialization.network;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.item.IToggleClick;
import live.hisui.classicindustrialization.util.client.InputHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {

    public static void handleUpdateInputPacket(final UpdateInputPacket packet, IPayloadContext ctx){
        ctx.enqueueWork(() -> {
            InputHandler.update(ctx.player(), packet.motion());
        });
    }

    public static void handleItemTogglePacket(final ItemTogglePacket packet, IPayloadContext ctx){
        ctx.enqueueWork(() -> {
            ClassicIndustrialization.LOGGER.debug("Slot idx: {}", packet.slot());
            ClassicIndustrialization.LOGGER.debug("Handling Item Toggle Packet");
            Player player = ctx.player();
            if(player.containerMenu == null) return;

            int slots = player.containerMenu.slots.size();
            if(packet.slot() > slots) return;
            Slot slotObj = player.containerMenu.getSlot(packet.slot());
            if(slotObj != null && slotObj.hasItem()){
                ClassicIndustrialization.LOGGER.debug("Slot not empty or null");
                ItemStack stack = slotObj.getItem();
                if(stack.getItem() instanceof IToggleClick click){
                    ClassicIndustrialization.LOGGER.debug("Item is IToggleClick");
                    click.onToggle(stack, player);
                }
            } else {
                ClassicIndustrialization.LOGGER.debug("Slot is null: {}", slotObj == null);
                ClassicIndustrialization.LOGGER.debug("Slot is empty: {}", slotObj != null && !slotObj.hasItem());
                ClassicIndustrialization.LOGGER.debug("Slot is null or empty");
            }
        });


    }
}
