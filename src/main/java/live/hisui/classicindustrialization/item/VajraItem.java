package live.hisui.classicindustrialization.item;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.component.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;
import java.util.Optional;

public class VajraItem extends ElectricToolItem {

    // TODO: Silk touch shouldnt work without power

    private static final List<ItemAbility> ACTIONS = List.of(
            ItemAbilities.AXE_SCRAPE,
            ItemAbilities.AXE_STRIP,
            ItemAbilities.AXE_WAX_OFF,
            ItemAbilities.SHEARS_CARVE,
            ItemAbilities.SHEARS_TRIM,
            ItemAbilities.SHEARS_HARVEST,
            ItemAbilities.SHEARS_REMOVE_ARMOR,
            ItemAbilities.SHEARS_DIG,
            ItemAbilities.SHOVEL_DOUSE,
            ItemAbilities.SHOVEL_FLATTEN,
            ItemAbilities.AXE_DIG,
            ItemAbilities.PICKAXE_DIG,
            ItemAbilities.SWORD_DIG
    );

    public VajraItem(Tier tier, Properties properties, TagKey<Block> mineableTag, float speed, int maxEnergy) {
        super(tier, properties, mineableTag, speed, maxEnergy);
        this.ENERGY_TO_MINE = 500;
    }

    public VajraItem(Tier tier, Properties properties, List<TagKey<Block>> mineableTags, float speed, int maxEnergy) {
        super(tier, properties, mineableTags, speed, maxEnergy);
        this.ENERGY_TO_MINE = 500;
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(!level.isClientSide()) {
            ItemStack stack = player.getItemInHand(usedHand);
            Holder<Enchantment> holder = level.holder(Enchantments.SILK_TOUCH).orElse(null);
            if(holder != null) {
                if (player.isCrouching()) {
                    if (Optional.ofNullable(stack.get(ModDataComponents.ITEM_ENABLED)).orElse(false)) {
                        stack.set(ModDataComponents.ITEM_ENABLED, false);
                        ItemEnchantments ench = Optional.ofNullable(stack.get(DataComponents.ENCHANTMENTS)).orElse(ItemEnchantments.EMPTY);
                        ItemEnchantments.Mutable mut = new ItemEnchantments.Mutable(ench);
                        mut.set(holder, 0);
                        stack.set(DataComponents.ENCHANTMENTS, mut.toImmutable());
                        player.displayClientMessage(Component.literal("Silk touch disabled"), false);
                    } else {
                        stack.enchant(holder, 1);
                        stack.set(ModDataComponents.ITEM_ENABLED, true);
                        player.displayClientMessage(Component.literal("Silk touch enabled"), false);
                    }
                }
            }
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        if(stack.getItem() instanceof EnergyStoringItem energyStoringItem) {
            Holder<Enchantment> holder = level.holder(Enchantments.SILK_TOUCH).orElse(null);
            if(holder != null) {
                if (stack.getEnchantmentLevel(holder) > 0) {
                    energyStoringItem.extractEnergy(stack, ENERGY_TO_MINE * 4, false);
                } else {
                    energyStoringItem.extractEnergy(stack, ENERGY_TO_MINE, false);
                }
            } else {
                energyStoringItem.extractEnergy(stack, ENERGY_TO_MINE, false);
            }
        }
        return true;
//        return super.mineBlock(stack, level, state, pos, miningEntity);
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
        InteractionResult shovelResult = Items.DIAMOND_SHOVEL.useOn(context);
        if(shovelResult == InteractionResult.SUCCESS) return shovelResult;
        return super.useOn(context);
    }
}
