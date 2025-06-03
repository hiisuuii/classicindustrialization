package live.hisui.classicindustrialization;

import live.hisui.classicindustrialization.capability.CustomComponentEnergyStorage;
import live.hisui.classicindustrialization.component.ModDataComponents;
import live.hisui.classicindustrialization.entity.client.LaserEntityModel;
import live.hisui.classicindustrialization.item.EnergyStoringItem;
import live.hisui.classicindustrialization.menu.GeneratorMenu;
import live.hisui.classicindustrialization.screen.GeneratorScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStackSimple;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

@EventBusSubscriber(modid = ClassicIndustrialization.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event){
        event.register(ClassicIndustrialization.GENERATOR_MENU.get(),
                (MenuScreens.ScreenConstructor<GeneratorMenu, GeneratorScreen<GeneratorMenu>>)GeneratorScreen::new);
    }

    @SubscribeEvent
    public static void registerCapabilitiesEvent(RegisterCapabilitiesEvent event) {
        for(DeferredHolder<Item, ? extends Item> holder : ModItems.ITEMS.getEntries()) {
            if(holder.get() instanceof EnergyStoringItem electricToolItem) {
                event.registerItem(Capabilities.EnergyStorage.ITEM,(stack, context) ->
                    new CustomComponentEnergyStorage(stack, ModDataComponents.ENERGY_STORAGE.get(),
                            electricToolItem.maxCapacity, electricToolItem.transferRate), electricToolItem);
            }
        }
        event.registerItem(Capabilities.FluidHandler.ITEM,
                (stack, ctx) -> {
                    return new FluidHandlerItemStackSimple(ModDataComponents.FLUID_STORAGE, stack, 1000);
                }, ModItems.FLUID_CELL.get());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ClassicIndustrialization.GENERATOR_BE.get(),
                (be, direction) -> be.getEnergyStorage()
        );
    }
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LaserEntityModel.LAYER_LOCATION, LaserEntityModel::createBodyLayer);
        event.registerLayerDefinition(ClassicIndustrialization.ClassicIndustrializationClient.OUTER_EQUIPMENT_LAYER,
                () -> ClassicIndustrialization.ClassicIndustrializationClient.OUTER_EQUIPMENT_LAYER_DEF);
        event.registerLayerDefinition(ClassicIndustrialization.ClassicIndustrializationClient.INNER_EQUIPMENT_LAYER,
                () -> ClassicIndustrialization.ClassicIndustrializationClient.INNER_EQUIPMENT_LAYER_DEF);
    }


}
