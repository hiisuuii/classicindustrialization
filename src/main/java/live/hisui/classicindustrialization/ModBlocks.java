package live.hisui.classicindustrialization;

import live.hisui.classicindustrialization.block.entity.machine.GeneratorBlockEntity;
import live.hisui.classicindustrialization.block.machine.GeneratorBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
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
    public static final DeferredBlock<Block> RAW_TIN_BLOCK = register("raw_tin_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_COPPER_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final DeferredBlock<Block> TIN_BLOCK = register("tin_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final DeferredBlock<Block> BRONZE_BLOCK = register("bronze_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_ORANGE)));

    public static final DeferredBlock<Block> GENERATOR = register("generator",
            () -> new GeneratorBlock(BlockBehaviour.Properties.of()));

    private static DeferredBlock<Block> register(String name, Supplier<Block> supp) {
        return BLOCKS.register(name, supp);
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
