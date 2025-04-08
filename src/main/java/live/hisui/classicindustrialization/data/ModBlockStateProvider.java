package live.hisui.classicindustrialization.data;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.ModBlocks;
import net.minecraft.data.PackOutput;
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
    }
}
