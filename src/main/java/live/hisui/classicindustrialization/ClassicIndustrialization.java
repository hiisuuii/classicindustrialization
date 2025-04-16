package live.hisui.classicindustrialization;

import com.mojang.logging.LogUtils;
import live.hisui.classicindustrialization.component.ModDataComponents;
import live.hisui.classicindustrialization.entity.client.LaserEntityRenderer;
import live.hisui.classicindustrialization.item.EnergyStoringItem;
import live.hisui.classicindustrialization.item.IToggleClick;
import live.hisui.classicindustrialization.network.ItemTogglePacket;
import live.hisui.classicindustrialization.network.UpdateInputPacket;
import live.hisui.classicindustrialization.render.EquipmentModel;
import live.hisui.classicindustrialization.render.EquipmentRenderLayer;
import live.hisui.classicindustrialization.util.client.InputHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

import java.util.Optional;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ClassicIndustrialization.MODID)
public class ClassicIndustrialization
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "classicindustrialization";
    // Directly reference a slf4j logger
    public  static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public ClassicIndustrialization(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ClassicIndustrialization) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModEntities.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    public static ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.SMALL_BATTERY.get());
            event.accept(ModItems.CHAINSAW.get());
            event.accept(ModItems.DRILL.get());
            event.accept(ModItems.DIAMOND_DRILL.get());
            event.accept(ModItems.ADV_CHAINSAW.get());
            event.accept(ModItems.ADV_DRILL.get());
            event.accept(ModItems.NANO_CHAINSAW.get());
            event.accept(ModItems.NANO_DRILL.get());
            event.accept(ModItems.QUANTUM_DRILL.get());
            event.accept(ModItems.QUANTUM_CHAINSAW.get());
            event.accept(ModItems.VAJRA.get());
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @OnlyIn(Dist.CLIENT)
    public static class ClassicIndustrializationClient {
        public static final LayerDefinition OUTER_EQUIPMENT_LAYER_DEF = LayerDefinition.create(HumanoidArmorModel.createBodyLayer(
                LayerDefinitions.OUTER_ARMOR_DEFORMATION), 64, 32);
        public static final LayerDefinition INNER_EQUIPMENT_LAYER_DEF = LayerDefinition.create(HumanoidArmorModel.createBodyLayer(
                LayerDefinitions.INNER_ARMOR_DEFORMATION), 64, 32);
        public static final ModelLayerLocation OUTER_EQUIPMENT_LAYER = new ModelLayerLocation(ClassicIndustrialization.modLoc("equipment_layer_outer"), "outer_gear");
        public static final ModelLayerLocation INNER_EQUIPMENT_LAYER = new ModelLayerLocation(ClassicIndustrialization.modLoc("equipment_layer_inner"), "inner_gear");

    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
    public static class ClientGameEvents {


        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Pre event){
            Minecraft mc = Minecraft.getInstance();
            var settings = mc.options;

            if(mc.getConnection() == null) return;

            boolean up = settings.keyJump.isDown();
            boolean down = settings.keyShift.isDown();
            boolean forward = settings.keyUp.isDown();
            boolean back = settings.keyDown.isDown();
            boolean left = settings.keyLeft.isDown();
            boolean right = settings.keyRight.isDown();

            Vector3f motionVector = new Vector3f(0,0,0);
            if(up) motionVector.add(0,1,0);
            if(down) motionVector.add(0,-1,0);
            if(forward) motionVector.add(0,0,1);
            if(back) motionVector.add(0,0,-1);
            if(left) motionVector.add(1,0,0);
            if(right) motionVector.add(-1,0,0);

            PacketDistributor.sendToServer(new UpdateInputPacket(motionVector));
            InputHandler.update(mc.player, motionVector);
        }

        @SubscribeEvent
        public static void onMouseEvent(ScreenEvent.MouseButtonPressed.Pre event){
            Minecraft mc = Minecraft.getInstance();
            if(event.getScreen() == null || !(event.getScreen() instanceof AbstractContainerScreen<?> screen)) {
                ClassicIndustrialization.LOGGER.debug("Screen is container: {}", event.getScreen() instanceof AbstractContainerScreen<?>);
                ClassicIndustrialization.LOGGER.debug("Screen null: {}", event.getScreen() == null);
                return;
            }
            boolean rightClicked = event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT;
            try {
                if(rightClicked && screen.getSlotUnderMouse() != null){
                    Slot slot = screen.getSlotUnderMouse();
                    ClassicIndustrialization.LOGGER.debug("Slot idx: {}", slot.index);
                    ItemStack stack = slot.getItem();
                    if(stack.getItem() instanceof IToggleClick){
                        ClassicIndustrialization.LOGGER.debug("Item can toggle click");
                        PacketDistributor.sendToServer(new ItemTogglePacket(slot.index));
                        ClassicIndustrialization.LOGGER.debug("Toggle packet sent");
                        event.setCanceled(true);
                    }
                } else if(!rightClicked) {
                    ClassicIndustrialization.LOGGER.debug("Not right click");
                } else if(screen.getSlotUnderMouse() == null) {
                    ClassicIndustrialization.LOGGER.debug("Slot is null");
                }
            } catch (Exception e){
                ClassicIndustrialization.LOGGER.error("Click error: ", e);
            }
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.AddLayers event){
            var context = event.getContext();
            PlayerRenderer renderer = event.getSkin(PlayerSkin.Model.WIDE);
            renderer.addLayer(new EquipmentRenderLayer<>(renderer,
                    new EquipmentModel<>(context.bakeLayer(ClassicIndustrializationClient.INNER_EQUIPMENT_LAYER)),
                    new EquipmentModel<>(context.bakeLayer(ClassicIndustrializationClient.OUTER_EQUIPMENT_LAYER)),
                    context.getModelManager()
                    ));
            renderer = event.getSkin(PlayerSkin.Model.SLIM);
            renderer.addLayer(new EquipmentRenderLayer<>(renderer,
                    new EquipmentModel<>(context.bakeLayer(ClassicIndustrializationClient.INNER_EQUIPMENT_LAYER)),
                    new EquipmentModel<>(context.bakeLayer(ClassicIndustrializationClient.OUTER_EQUIPMENT_LAYER)),
                    context.getModelManager()
            ));
        }

        @SubscribeEvent
        public static void registerItemColors(RegisterColorHandlersEvent.Item event){
            event.register(
                    (stack, tintIndex) -> {
                        boolean enabled = Optional.ofNullable(stack.get(ModDataComponents.ITEM_ENABLED)).orElse(false);
                        if(enabled){
                            if(tintIndex == 1){
                                return 0xFFFFFFFF;
                            } else if(tintIndex == 2){
                                double SCALAR = 1.5f;
                                double hue = ((440f * (1.0d/SCALAR)) + (System.currentTimeMillis() % (360 * (1.0d/SCALAR))));
                                return Mth.hsvToRgb((float) (( hue / 360.0f) * (0.4f * SCALAR)), 0.55F, 1.0F) | 0xFF000000;
                            }
                        } else {
                            if(tintIndex == 1){
                                return 0x00000000;
                            } else if(tintIndex == 2){
                                return 0x00000000;
                            }
                        }
                        return -1;
                    }, ModItems.ELECTRIC_SWORD.get());
            event.register((stack, idx) -> {
                if(stack.getItem() instanceof EnergyStoringItem energyStoringItem){
                    return 0xFF000000 | Mth.hsvToRgb(0.0f,
                            Math.max(0.3f, ((float)energyStoringItem.getEnergyStored(stack) / energyStoringItem.getMaxEnergyStored(stack))), 1.0f);
                }
                return -1;
            }, ModItems.HUGE_BATTERY.get());
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            EntityRenderers.register(ModEntities.LASER.get(), LaserEntityRenderer::new);
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
