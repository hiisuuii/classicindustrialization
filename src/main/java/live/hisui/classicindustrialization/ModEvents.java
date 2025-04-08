package live.hisui.classicindustrialization;

import live.hisui.classicindustrialization.capability.CustomComponentEnergyStorage;
import live.hisui.classicindustrialization.component.ModDataComponents;
import live.hisui.classicindustrialization.entity.client.LaserEntityModel;
import live.hisui.classicindustrialization.item.EnergyStoringItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

@EventBusSubscriber(modid = ClassicIndustrialization.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void registerCapabilitiesEvent(RegisterCapabilitiesEvent event) {
        for(DeferredHolder<Item, ? extends Item> holder : ModItems.ITEMS.getEntries()) {
            if(holder.get() instanceof EnergyStoringItem electricToolItem) {
                event.registerItem(Capabilities.EnergyStorage.ITEM,(stack, context) ->
                    new CustomComponentEnergyStorage(stack, ModDataComponents.ENERGY_STORAGE.get(),
                            electricToolItem.maxCapacity, electricToolItem.transferRate), electricToolItem);
            }
        }
    }
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LaserEntityModel.LAYER_LOCATION, LaserEntityModel::createBodyLayer);
    }

}
