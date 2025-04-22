package live.hisui.classicindustrialization.block.machine;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.block.entity.machine.GeneratorBlockEntity;
import live.hisui.classicindustrialization.energy.EnergyTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class GeneratorBlock extends ProviderBase{
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final MapCodec<GeneratorBlock> CODEC = simpleCodec(GeneratorBlock::new);
    public GeneratorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH)
                .setValue(LIT, Boolean.FALSE));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(level.isClientSide()){
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity instanceof GeneratorBlockEntity gbe){
                player.openMenu(gbe);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Override
    public int getMaxOutputRate() {
        return EnergyTiers.LOW_VOLTAGE.getMaxEnergyExtract();
    }

    @Override
    public int getGenerationRate() {
        return EnergyTiers.LOW_VOLTAGE.getProductionRate();
    }

    @Override
    public int getMaxEnergyStored() {
        return EnergyTiers.LOW_VOLTAGE.getMaxEnergyStored();
    }

    @Override
    public int getCurrentEnergyStored() {
        return 0;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GeneratorBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createFurnaceTicker(level, blockEntityType, ClassicIndustrialization.GENERATOR_BE.get());
    }

    protected static <T extends BlockEntity> BlockEntityTicker<T> createFurnaceTicker(
            Level level, BlockEntityType<T> serverType, BlockEntityType<? extends GeneratorBlockEntity> clientType
    ) {
        return level.isClientSide ? null : createTickerHelper(serverType, clientType, GeneratorBlockEntity::serverTick);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

}
