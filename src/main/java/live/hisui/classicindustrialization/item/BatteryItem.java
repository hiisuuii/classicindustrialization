package live.hisui.classicindustrialization.item;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.component.ModDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

public class BatteryItem extends EnergyStoringItem {
    public BatteryItem(int maxCapacity, int transferRate) {
        super(maxCapacity, transferRate);
    }
    public BatteryItem(Properties properties, int maxCapacity, int transferRate) {
        super(properties, maxCapacity, transferRate);
    }

    // TODO: Make charging better (while loop)
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(level.isClientSide()) return;
        if(entity instanceof Player player) {
            if (Optional.ofNullable(stack.get(ModDataComponents.ITEM_ENABLED)).orElse(false) && this.getEnergyStored(stack) > 0) {
                for(ItemStack invStack : Iterables.filter(
                        // TODO: check for item handler capability (backpacks etc)
                        Iterables.concat(player.getInventory().items, player.getInventory().armor, player.getInventory().offhand), input -> input != stack)){
                    if(invStack.getItem() instanceof EnergyStoringItem energyStoringItem) {
                        if(energyStoringItem.canReceiveEnergy(invStack)){
                            int chargeRate = Math.min(this.getTransferRate(), energyStoringItem.getTransferRate());
                            int amountToCharge = Math.min(chargeRate, energyStoringItem.getMaxEnergyStored(invStack) - energyStoringItem.getEnergyStored(invStack));
                            if(this.getEnergyStored(stack) < amountToCharge) {
                                amountToCharge = this.getEnergyStored(stack);
                            }

                            this.extractEnergy(stack, amountToCharge, false);
                            energyStoringItem.receiveEnergy(invStack,amountToCharge,false);
                        }
                    }
                }
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged || !ItemStack.isSameItem(oldStack, newStack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(!level.isClientSide()) {
            ItemStack stack = player.getItemInHand(usedHand);
            if (player.isCrouching()) {
                if (Optional.ofNullable(stack.get(ModDataComponents.ITEM_ENABLED)).orElse(false)) {
                    stack.set(ModDataComponents.ITEM_ENABLED, false);
                    player.displayClientMessage(Component.literal("Battery disabled"), false);
                } else {
                    stack.set(ModDataComponents.ITEM_ENABLED, true);
                    player.displayClientMessage(Component.literal("Battery enabled"), false);
                }
            }
        }
        return super.use(level, player, usedHand);
    }
}
