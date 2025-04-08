package live.hisui.classicindustrialization;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ClassicIndustrialization.MODID);

    public static final DeferredBlock<Block> TIN_ORE = register("tin_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_ORE)));
    public static final DeferredBlock<Block> DEEPSLATE_TIN_ORE = register("deepslate_tin_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_COPPER_ORE)));

    private static DeferredBlock<Block> register(String name, Supplier<Block> supp) {
        return BLOCKS.register(name, supp);
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
