package live.hisui.classicindustrialization.data;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.TexturedModel;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ClassicIndustrialization.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.TIN_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_TIN_ORE.get());
        simpleBlock(ModBlocks.TIN_BLOCK.get());
        simpleBlock(ModBlocks.RAW_TIN_BLOCK.get());
        simpleBlock(ModBlocks.BRONZE_BLOCK.get());
        horizontalBlock(ModBlocks.GENERATOR.get(), models().orientableWithBottom("generator",
                ClassicIndustrialization.modLoc("block/generator_side"),
                ClassicIndustrialization.modLoc("block/generator_front"),
                ClassicIndustrialization.modLoc("block/generator_bottom"),
                ClassicIndustrialization.modLoc("block/generator_top"))
        );
    }

}
