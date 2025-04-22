package live.hisui.classicindustrialization.item;

import live.hisui.classicindustrialization.component.ModDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.List;
import java.util.Optional;

public class ElectricSwordItem extends ElectricToolItem {

    private static final List<ItemAbility> ACTIONS = List.of(
            ItemAbilities.SWORD_DIG,
            ItemAbilities.SWORD_SWEEP
    );

    public ElectricSwordItem(Properties properties, int maxEnergy){
        this(Tiers.NETHERITE, properties, BlockTags.SWORD_EFFICIENT, 1.5f, maxEnergy);
        this.ENERGY_TO_HURT = 1000;
    }

    public ElectricSwordItem(Tier tier, Properties properties, TagKey<Block> mineableTag, float speed, int maxEnergy) {
        super(tier, properties, mineableTag, speed, maxEnergy);
    }

    public ElectricSwordItem(Tier tier, Properties properties, List<TagKey<Block>> mineableTags, float speed, int maxEnergy) {
        super(tier, properties, mineableTags, speed, maxEnergy);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(stack.getItem() instanceof EnergyStoringItem energyStoringItem && Optional.ofNullable(stack.get(ModDataComponents.ITEM_ENABLED)).orElse(false)) {
            energyStoringItem.extractEnergy(stack, ENERGY_TO_HURT, false);
            return true;
        }
        return false;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        if(Optional.ofNullable(stack.get(ModDataComponents.ITEM_ENABLED)).orElse(false)) {
            if (stack.getItem() instanceof ElectricToolItem eti) {
                if (eti.getEnergyStored(stack) < eti.ENERGY_TO_MINE) {
                    return false;
                }
            }
            if (ACTIONS.contains(itemAbility))
                return true;
        }
        return super.canPerformAction(stack, itemAbility);
    }

    public static ItemAttributeModifiers createAttributes(float dmg, float spd) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID, dmg, AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, spd, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(!level.isClientSide()) {
            if (this.getEnergyStored(stack) == 0) {
                if (Optional.ofNullable(stack.get(ModDataComponents.ITEM_ENABLED)).orElse(false)) {
                    stack.set(ModDataComponents.ITEM_ENABLED, false);
                    stack.remove(DataComponents.ATTRIBUTE_MODIFIERS);
                }
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(!level.isClientSide()) {
            ItemStack stack = player.getItemInHand(usedHand);
            if(this.getEnergyStored(stack) > 0) {
                if (player.isCrouching()) {
                    if (Optional.ofNullable(stack.get(ModDataComponents.ITEM_ENABLED)).orElse(false)) {
                        stack.set(ModDataComponents.ITEM_ENABLED, false);
                        stack.remove(DataComponents.ATTRIBUTE_MODIFIERS);
                        player.displayClientMessage(Component.literal("Sword disabled"), false);
                    } else {
                        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, createAttributes(15, -2.0f));
                        stack.set(ModDataComponents.ITEM_ENABLED, true);
                        player.displayClientMessage(Component.literal("Sword enabled"), false);
                    }
                }
            }
        }
        return super.use(level, player, usedHand);
    }
}
