package live.hisui.classicindustrialization.item;

import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;
import java.util.Set;

public class ElectricHoeItem extends ElectricToolItem {
    public ElectricHoeItem(Tier tier, Properties properties, TagKey<Block> mineableTag, float speed, int maxEnergy) {
        super(tier, properties, mineableTag, speed, maxEnergy);
    }

    public ElectricHoeItem(Tier tier, Properties properties, List<TagKey<Block>> mineableTags, float speed, int maxEnergy) {
        super(tier, properties, mineableTags, speed, maxEnergy);
    }

    private static final Set<ItemAbility> ACTIONS = ItemAbilities.DEFAULT_HOE_ACTIONS;

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
    public InteractionResult useOn(UseOnContext context) {
        if(this.getEnergyStored(context.getItemInHand()) < this.ENERGY_TO_MINE) {
            return InteractionResult.PASS;
        }
        this.extractEnergy(context.getItemInHand(), this.ENERGY_TO_MINE, false);
        if(context.getPlayer() == null) return InteractionResult.FAIL;
        InteractionResult hoeResult = Items.DIAMOND_HOE.useOn(context);
        if(hoeResult == InteractionResult.SUCCESS) return hoeResult;
        return super.useOn(context);
    }
}
