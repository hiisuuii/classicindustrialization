package live.hisui.classicindustrialization.util;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Items {

        public static final TagKey<Item> INSULATION = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(
                ClassicIndustrialization.MODID, "insulation"
        ));
        public static final TagKey<Item> INGOTS_TIN = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(
                "c", "ingots/tin"
        ));
        public static final TagKey<Item> INGOTS_BRONZE = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(
                "c", "ingots/bronze"
        ));
        public static final TagKey<Item> ORES_TIN = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(
                "c", "ores/tin"
        ));
        public static final TagKey<Item> DUSTS_TIN = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(
                "c", "dusts/tin"
        ));
        public static final TagKey<Item> DUSTS_COPPER = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(
                "c", "dusts/copper"
        ));
        public static final TagKey<Item> DUSTS_BRONZE = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(
                "c", "dusts/bronze"
        ));
        public static final TagKey<Item> DUSTS_IRON = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(
                "c", "dusts/iron"
        ));
        public static final TagKey<Item> DUSTS_GOLD = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(
                "c", "dusts/gold"
        ));
        public static final TagKey<Item> DUSTS_COAL = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(
                "c", "dusts/coal"
        ));
    }
    public static class Blocks {
        public static final TagKey<Block> ORES_TIN = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(
                "c", "ores/tin"
        ));
    }
}
