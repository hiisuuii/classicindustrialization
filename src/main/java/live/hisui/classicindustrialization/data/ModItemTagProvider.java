package live.hisui.classicindustrialization.data;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.ModItems;
import live.hisui.classicindustrialization.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, ClassicIndustrialization.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.INSULATION)
                .add(Items.LEATHER)
                .addTag(ItemTags.WOOL);
        copy(Tags.Blocks.ORES,Tags.Items.ORES);
        copy(ModTags.Blocks.ORES_TIN,ModTags.Items.ORES_TIN);
        copy(Tags.Blocks.ORE_RATES_DENSE, Tags.Items.ORE_RATES_DENSE);
        copy(Tags.Blocks.ORES_IN_GROUND_STONE, Tags.Items.ORES_IN_GROUND_STONE);
        copy(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Items.ORES_IN_GROUND_DEEPSLATE);
        tag(ModTags.Items.INGOTS_TIN).add(ModItems.TIN_INGOT.get());
        tag(ModTags.Items.INGOTS_BRONZE).add(ModItems.BRONZE_INGOT.get());
        tag(ModTags.Items.DUSTS_TIN).add(ModItems.TIN_DUST.get());
        tag(ModTags.Items.DUSTS_IRON).add(ModItems.IRON_DUST.get());
        tag(ModTags.Items.DUSTS_GOLD).add(ModItems.GOLD_DUST.get());
        tag(ModTags.Items.DUSTS_COAL).add(ModItems.CARBON_DUST.get());
        tag(ModTags.Items.DUSTS_COPPER).add(ModItems.COPPER_DUST.get());
        tag(ModTags.Items.DUSTS_BRONZE).add(ModItems.BRONZE_DUST.get());
        tag(Tags.Items.INGOTS).add(ModItems.TIN_INGOT.get())
                .add(ModItems.BRONZE_INGOT.get());
        tag(ModTags.Items.RAW_TIN).add(ModItems.RAW_TIN.get());
    }
}
