package live.hisui.classicindustrialization.data;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.ModBlocks;
import live.hisui.classicindustrialization.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ClassicIndustrialization.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.TIN_ORE.get())
                .add(ModBlocks.DEEPSLATE_TIN_ORE.get());
        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.TIN_ORE.get())
                .add(ModBlocks.DEEPSLATE_TIN_ORE.get());
        tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES)
                .add(ModBlocks.TIN_ORE.get())
                .add(ModBlocks.DEEPSLATE_TIN_ORE.get());
        tag(BlockTags.INCORRECT_FOR_WOODEN_TOOL)
                .add(ModBlocks.TIN_ORE.get())
                .add(ModBlocks.DEEPSLATE_TIN_ORE.get());
        tag(BlockTags.INCORRECT_FOR_GOLD_TOOL)
                .add(ModBlocks.TIN_ORE.get())
                .add(ModBlocks.DEEPSLATE_TIN_ORE.get());
        tag(Tags.Blocks.ORES)
                .add(ModBlocks.TIN_ORE.get())
                .add(ModBlocks.DEEPSLATE_TIN_ORE.get());
        tag(BlockTags.SNAPS_GOAT_HORN)
                .add(ModBlocks.TIN_ORE.get());
        tag(Tags.Blocks.ORE_RATES_DENSE)
                .add(ModBlocks.TIN_ORE.get())
                .add(ModBlocks.DEEPSLATE_TIN_ORE.get());
        tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE)
                .add(ModBlocks.DEEPSLATE_TIN_ORE.get());
        tag(Tags.Blocks.ORES_IN_GROUND_STONE)
                .add(ModBlocks.TIN_ORE.get());
        tag(ModTags.Blocks.ORES_TIN)
                .add(ModBlocks.TIN_ORE.get())
                .add(ModBlocks.DEEPSLATE_TIN_ORE.get());
    }
}
