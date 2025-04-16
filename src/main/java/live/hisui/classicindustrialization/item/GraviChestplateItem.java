package live.hisui.classicindustrialization.item;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.component.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import java.text.NumberFormat;
import java.util.List;
import java.util.Optional;

public class GraviChestplateItem extends EnergyStoringItem implements IToggleClick, INotArmorButRendersLikeArmor {
    public GraviChestplateItem(int maxCapacity, int transferRate) {
        super(maxCapacity, transferRate);
    }

    public GraviChestplateItem(Properties properties, int maxCapacity, int transferRate) {
        super(properties, maxCapacity, transferRate);
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        if(this.getEnergyStored(stack) > 0 && Optional.ofNullable(stack.get(ModDataComponents.ITEM_ENABLED)).orElse(false)) {
            return ItemAttributeModifiers.builder()
                    .add(NeoForgeMod.CREATIVE_FLIGHT,
                            new AttributeModifier(ClassicIndustrialization.modLoc("creative_flight"), 1,
                                    AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.CHEST)
                    .build();
        }
        return super.getDefaultAttributeModifiers(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(level.isClientSide()) return;
        if(entity instanceof Player player){
            if(Optional.ofNullable(stack.get(ModDataComponents.ITEM_ENABLED)).orElse(false) && player.getAbilities().flying) {
                this.extractEnergy(stack,20, false);
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return this.swapWithEquipmentSlot(this, level, player, hand);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.CHEST;
    }

    @Override
    public Holder<SoundEvent> getEquipSound() {
        return INotArmorButRendersLikeArmor.super.getEquipSound();
    }

    @Override
    public InteractionResultHolder<ItemStack> swapWithEquipmentSlot(Item item, Level level, Player player, InteractionHand hand) {
        return INotArmorButRendersLikeArmor.super.swapWithEquipmentSlot(item, level, player, hand);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nonnull TooltipContext context, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        tooltip.add(Optional.ofNullable(stack.get(ModDataComponents.ITEM_ENABLED)).orElse(false)
                ? Component.literal("Enabled").withStyle(ChatFormatting.GREEN)
                : Component.literal("Disabled").withStyle(ChatFormatting.RED));
        super.appendHoverText(stack, context, tooltip, flagIn);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        if(Optional.ofNullable(stack.get(ModDataComponents.ITEM_ENABLED)).orElse(false)){
            return true;
        }
        return super.isFoil(stack);
    }

//    @Override
//    public void onToggle(ItemStack stack, Player player) {
//        if (Optional.ofNullable(stack.get(ModDataComponents.ITEM_ENABLED)).orElse(false)) {
//            stack.set(ModDataComponents.ITEM_ENABLED, false);
//            player.displayClientMessage(Component.literal("Flight disabled"), false);
//        } else {
//            stack.set(ModDataComponents.ITEM_ENABLED, true);
//            player.displayClientMessage(Component.literal("Flight enabled"), false);
//        }
//    }
}
