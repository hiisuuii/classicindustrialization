package live.hisui.classicindustrialization;

import live.hisui.classicindustrialization.component.ModDataComponents;
import live.hisui.classicindustrialization.item.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class ModItems {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, ClassicIndustrialization.MODID);
    public static final Supplier<CreativeModeTab> TAB = TABS.register("tab", () ->
            CreativeModeTab.builder().title(Component.translatable("itemgroup." + ClassicIndustrialization.MODID))
                    .icon(() -> new ItemStack(ModItems.BASIC_CIRCUIT.get()))
                    .displayItems((params, output) -> {
                        output.accept(ModItems.ELECTRIC_HOE);
                        output.accept(ModItems.DRILL);
                        output.accept(ModItems.DIAMOND_DRILL);
                        output.accept(ModItems.ADV_DRILL);
                        output.accept(ModItems.NANO_DRILL);
                        output.accept(ModItems.QUANTUM_DRILL);
                        output.accept(ModItems.CHAINSAW);
                        output.accept(ModItems.ADV_CHAINSAW);
                        output.accept(ModItems.NANO_CHAINSAW);
                        output.accept(ModItems.QUANTUM_CHAINSAW);
                        output.accept(ModItems.VAJRA);
                        output.accept(ModItems.SMALL_BATTERY);
                        output.accept(ModItems.BASIC_BATTERY);
                        output.accept(ModItems.EVEN_BIGGER_BATTERY);
                        output.accept(ModItems.HUGE_BATTERY);
                        output.accept(ModItems.BASIC_CIRCUIT);
                        output.accept(ModItems.ADV_CIRCUIT);
                        output.accept(ModItems.BASIC_SUBSTRATE);
                        output.accept(ModItems.ADV_SUBSTRATE);
                        output.accept(ModItems.ADV_ALLOY);
                        output.accept(ModItems.UNINSULATED_COPPER_CABLE);
                        output.accept(ModItems.INSULATED_COPPER_CABLE);
                        output.accept(ModItems.UNINSULATED_TIN_CABLE);
                        output.accept(ModItems.INSULATED_TIN_CABLE);
                        output.accept(ModItems.UNINSULATED_GOLD_CABLE);
                        output.accept(ModItems.INSULATED_GOLD_CABLE);
                        output.accept(ModItems.LASER_RIFLE);
                    }).build());

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ClassicIndustrialization.MODID);


    //region EQUIPMENT
    public static final DeferredItem<Item> ELECTRIC_HOE = register("electric_hoe",
            () -> new ElectricHoeItem(Tiers.IRON, new Item.Properties(), BlockTags.MINEABLE_WITH_HOE, 12, 2*32000));
    public static final DeferredItem<Item> CHAINSAW = register("chainsaw",
            () -> new ChainsawItem(Tiers.IRON, new Item.Properties()
                    .attributes(AxeItem.createAttributes(Tiers.IRON, 7, -2.4f)), 12, 2*32000));
    public static final DeferredItem<Item> DRILL = register("drill",
            () -> new DrillItem(Tiers.IRON, new Item.Properties(), 12, 2*32000));
    public static final DeferredItem<Item> DIAMOND_DRILL = register("diamond_drill",
            () -> new DrillItem(Tiers.DIAMOND, new Item.Properties(), 13, 2*32000));

    public static final DeferredItem<Item> ADV_CHAINSAW = register("advanced_chainsaw",
            () -> new ChainsawItem(Tiers.DIAMOND, new Item.Properties().rarity(Rarity.UNCOMMON)
                    .attributes(AxeItem.createAttributes(Tiers.DIAMOND, 7, -2.4f)), 14, 2*64000));
    public static final DeferredItem<Item> ADV_DRILL = register("advanced_drill",
            () -> new DrillItem(Tiers.DIAMOND, new Item.Properties().rarity(Rarity.UNCOMMON), 14, 2*64000));

    public static final DeferredItem<Item> NANO_CHAINSAW = register("nano_chainsaw",
            () -> new ChainsawItem(Tiers.DIAMOND, new Item.Properties().rarity(Rarity.RARE)
                    .attributes(AxeItem.createAttributes(Tiers.DIAMOND, 8, -2.4f)), 16, 2*128000));
    public static final DeferredItem<Item> NANO_DRILL = register("nano_drill",
            () -> new DrillItem(Tiers.DIAMOND, new Item.Properties().rarity(Rarity.RARE), 16, 2*128000));

    public static final DeferredItem<Item> QUANTUM_CHAINSAW = register("quantum_chainsaw",
            () -> new ChainsawItem(Tiers.NETHERITE, new Item.Properties().rarity(Rarity.EPIC)
                    .attributes(AxeItem.createAttributes(Tiers.NETHERITE, 8, -2.4f)), 18, 2*256000));
    public static final DeferredItem<Item> QUANTUM_DRILL = register("quantum_drill",
            () -> new DrillItem(Tiers.NETHERITE, new Item.Properties().rarity(Rarity.EPIC), 18, 2*256000));

    public static final DeferredItem<Item> VAJRA = register("vajra",
            () -> new VajraItem(Tiers.NETHERITE, new Item.Properties().rarity(Rarity.EPIC)
                    .attributes(PickaxeItem.createAttributes(Tiers.NETHERITE, 9, -1.0f)),
                    List.of(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_AXE,
                            BlockTags.MINEABLE_WITH_HOE, BlockTags.MINEABLE_WITH_SHOVEL,
                            BlockTags.SWORD_EFFICIENT),1800, 2*256000*4));
    public static final DeferredItem<Item> LASER_RIFLE = register("laser_rifle",
            () -> new LaserRifleItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON),
                    200000,8000));
    public static final DeferredItem<Item> ELECTRIC_SWORD = register("electric_sword",
            () -> new ElectricSwordItem(new Item.Properties().rarity(Rarity.UNCOMMON), 200000));
    public static final DeferredItem<Item> GRAVI_CHESTPLATE = register("gravichest",
            () -> new GraviChestplateItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC),
                    (int) Math.pow(2,21), 64000));
    public static final DeferredItem<Item> JETPACK = register("jetpack",
            () -> new JetpackItem(100000, 64000));

    //endregion

    //region BATTERIES
    public static final DeferredItem<Item> SMALL_BATTERY = register("small_battery",
            () -> new BatteryItem(32000, 200)); // 8 SECONDS TO DEPLETE
    public static final DeferredItem<Item> BASIC_BATTERY = register("basic_battery",
            () -> new BatteryItem(64000, 400)); // 8 SECONDS TO DEPLETE
    public static final DeferredItem<Item> EVEN_BIGGER_BATTERY = register("bigger_battery",
            () -> new BatteryItem(128000*2, 1600)); // 8 SECONDS TO DEPLETE
    public static final DeferredItem<Item> HUGE_BATTERY = register("huge_battery",
            () -> new BatteryItem(256000*4, 6400)); // 8 SECONDS TO DEPLETE
    public static final DeferredItem<Item> CREATIVE_BATTERY = register("creative_battery",
            () -> new CreativeBatteryItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)
                    .component(ModDataComponents.ENERGY_STORAGE, Integer.MAX_VALUE), Integer.MAX_VALUE, Integer.MAX_VALUE)); // basically infinite
    //endregion

    //region CABLES
    public static final DeferredItem<Item> UNINSULATED_COPPER_CABLE = register("uninsulated_copper_cable",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> INSULATED_COPPER_CABLE = register("insulated_copper_cable",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> UNINSULATED_TIN_CABLE = register("uninsulated_tin_cable",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> INSULATED_TIN_CABLE = register("insulated_tin_cable",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> UNINSULATED_GOLD_CABLE = register("uninsulated_gold_cable",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> INSULATED_GOLD_CABLE = register("insulated_gold_cable",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SUPERCONDUCTOR = register("superconductor",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    //endregion

    //region INGOTS
    public static final DeferredItem<Item> TIN_INGOT = register("tin_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BRONZE_INGOT = register("bronze_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MIXED_INGOT = register("mixed_ingot",
            () -> new Item(new Item.Properties()));
    //endregion

    //region DUSTS
    public static final DeferredItem<Item> CARBON_DUST = register("carbon_dust",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> IRON_DUST = register("iron_dust",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COPPER_DUST = register("copper_dust",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GOLD_DUST = register("gold_dust",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TIN_DUST = register("tin_dust",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BRONZE_DUST = register("bronze_dust",
            () -> new Item(new Item.Properties()));
    //endregion

    //region CRAFTING COMPONENTS
    public static final DeferredItem<Item> BASIC_SUBSTRATE = register("basic_substrate",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ADV_SUBSTRATE = register("advanced_substrate",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BASIC_CIRCUIT = register("basic_circuit",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ADV_CIRCUIT = register("advanced_circuit",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ADV_ALLOY = register("alloy_plate",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CARBON_FIBRE = register("carbon_fiber",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CARBON_LUMP = register("carbon_lump",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CARBON_PLATE = register("carbon_plate",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GRAPHENE = register("graphene",
            () -> new Item(new Item.Properties()));
    //endregion

    public static final DeferredItem<Item> TIN_ORE = register("tin_ore",
            () -> new BlockItem(ModBlocks.TIN_ORE.get(), new Item.Properties()));
    public static final DeferredItem<Item> DEEPSLATE_TIN_ORE = register("deepslate_tin_ore",
            () -> new BlockItem(ModBlocks.DEEPSLATE_TIN_ORE.get(), new Item.Properties()));
    public static final DeferredItem<Item> RAW_TIN_BLOCK = register("raw_tin_block",
            () -> new BlockItem(ModBlocks.RAW_TIN_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<Item> BRONZE_BLOCK = register("bronze_block", // TODO: Needs some texture help
            () -> new BlockItem(ModBlocks.BRONZE_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<Item> TIN_BLOCK = register("tin_block",
            () -> new BlockItem(ModBlocks.TIN_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<Item> RAW_TIN = register("raw_tin", // TODO: Still a little green
            () -> new Item(new Item.Properties()));

    private static DeferredItem<Item> register(String name, Supplier<Item> supp) {
        return ITEMS.register(name, supp);
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
        TABS.register(bus);
    }
}
