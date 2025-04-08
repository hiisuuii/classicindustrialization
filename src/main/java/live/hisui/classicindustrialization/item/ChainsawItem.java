package live.hisui.classicindustrialization.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

public class ChainsawItem extends ElectricToolItem{
    private static final List<ItemAbility> ACTIONS = List.of(
            ItemAbilities.AXE_SCRAPE,
            ItemAbilities.AXE_STRIP,
            ItemAbilities.AXE_WAX_OFF,
            ItemAbilities.SHEARS_CARVE,
            ItemAbilities.SHEARS_TRIM,
            ItemAbilities.SHEARS_HARVEST,
            ItemAbilities.SHEARS_REMOVE_ARMOR,
            ItemAbilities.SHEARS_DIG
    );
    public ChainsawItem(Tier tier, Properties properties, float speed, int maxEnergy) {
        super(tier, properties, List.of(BlockTags.MINEABLE_WITH_HOE, BlockTags.MINEABLE_WITH_AXE), speed, maxEnergy);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        if(stack.getItem() instanceof ElectricToolItem eti) {
            if(eti.getEnergyStored(stack) < eti.ENERGY_TO_MINE) {
                return false;
            }
        }
        if(ACTIONS.contains(itemAbility))
            return true;
        return super.canPerformAction(stack, itemAbility);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if(this.getEnergyStored(stack) < this.ENERGY_TO_MINE) {
            return InteractionResult.PASS;
        }
        this.extractEnergy(stack, this.ENERGY_TO_MINE, false);
        return Items.SHEARS.interactLivingEntity(stack, player, interactionTarget, usedHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(this.getEnergyStored(context.getItemInHand()) < this.ENERGY_TO_MINE) {
            return InteractionResult.PASS;
        }
        this.extractEnergy(context.getItemInHand(), this.ENERGY_TO_MINE, false);
        if(context.getPlayer() == null) return InteractionResult.FAIL;
        InteractionResult axeResult = Items.DIAMOND_AXE.useOn(context);
        if(axeResult == InteractionResult.SUCCESS) return axeResult;
        InteractionResult shearResult = Items.SHEARS.useOn(context);
        if(shearResult == InteractionResult.SUCCESS) return shearResult;
        return super.useOn(context);
    }
}
