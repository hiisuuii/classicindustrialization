package live.hisui.classicindustrialization.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import java.util.List;

public class FluidCellItem extends Item {
    public FluidCellItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        var cap = stack.getCapability(Capabilities.FluidHandler.ITEM);
        if(cap != null ) {
            tooltipComponents.add(Component.literal(cap.getFluidInTank(0).getDescriptionId()));
            tooltipComponents.add(Component.literal(String.valueOf(cap.getFluidInTank(0).getAmount())));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        var cap = itemstack.getCapability(Capabilities.FluidHandler.ITEM);
        if(cap != null ) {
            BlockHitResult blockhitresult = getPlayerPOVHitResult(
                    level, player, cap.getFluidInTank(0).isEmpty() ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE
            );
            if (blockhitresult.getType() == HitResult.Type.MISS) {
                return InteractionResultHolder.pass(itemstack);
            } else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
                return InteractionResultHolder.pass(itemstack);
            } else {
                BlockPos blockpos = blockhitresult.getBlockPos();
                Direction direction = blockhitresult.getDirection();
                BlockPos blockpos1 = blockpos.relative(direction);
                if (!level.mayInteract(player, blockpos) || !player.mayUseItemAt(blockpos1, direction, itemstack)) {
                    return InteractionResultHolder.fail(itemstack);
                } else if (cap.getFluidInTank(0).isEmpty()) {
                    FluidState fluidState = level.getFluidState(blockpos);
                    Fluid fluid = fluidState.getType();
                    if(fluid.isSame(Fluids.EMPTY)){
                        return InteractionResultHolder.fail(itemstack);
                    } else {
                        FluidStack fluidStack = new FluidStack(fluid,1000);
                        ItemStack itemStack2 = itemstack.copyWithCount(1);
                        cap = itemStack2.getCapability(Capabilities.FluidHandler.ITEM);
                        cap.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                        ItemStack itemstack1 = ItemUtils.createFilledResult(itemstack, player, itemStack2, true);
                        level.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 11);
                        return InteractionResultHolder.sidedSuccess(itemstack1, level.isClientSide());
                    }
                }
            }
        }

        return InteractionResultHolder.fail(itemstack);
    }



}
