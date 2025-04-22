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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.energy.IEnergyStorage;
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
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ClassicIndustrialization.GENERATOR_BE.get(),
                (be, context) -> {
                    return new IEnergyStorage() {
                        @Override
                        public int receiveEnergy(int toReceive, boolean simulate) {
                            return 0;
                        }

                        @Override
                        public int extractEnergy(int toExtract, boolean simulate) {
                            return 0;
                        }

                        @Override
                        public int getEnergyStored() {
                            return 0;
                        }

                        @Override
                        public int getMaxEnergyStored() {
                            return 0;
                        }

                        @Override
                        public boolean canExtract() {
                            return false;
                        }

                        @Override
                        public boolean canReceive() {
                            return false;
                        }
                    };
                }
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
