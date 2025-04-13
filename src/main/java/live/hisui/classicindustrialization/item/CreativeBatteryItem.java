package live.hisui.classicindustrialization.item;

import com.google.common.collect.Iterables;
import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.component.ModDataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class CreativeBatteryItem extends BatteryItem{

    public CreativeBatteryItem(Properties properties, int maxCapacity, int transferRate) {
        super(properties, maxCapacity, transferRate);
    }
    public CreativeBatteryItem(int maxCapacity, int transferRate) {
        super(maxCapacity, transferRate);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(level.isClientSide()) return;
        if(entity instanceof Player player) {
            if (Optional.ofNullable(stack.get(ModDataComponents.ITEM_ENABLED)).orElse(false)) {
                for(ItemStack invStack : Iterables.filter(
                        Iterables.concat(player.getInventory().items, player.getInventory().armor, player.getInventory().offhand), input -> input != stack)){
                    if(invStack.getItem() instanceof EnergyStoringItem energyStoringItem) {
                        if(energyStoringItem.canReceiveEnergy(invStack)){
                            int chargeRate = Math.min(this.getTransferRate(), energyStoringItem.getTransferRate());
                            int amountToCharge = Math.min(chargeRate, energyStoringItem.getMaxEnergyStored(invStack) - energyStoringItem.getEnergyStored(invStack));

                            energyStoringItem.receiveEnergy(invStack,amountToCharge,false);
                        }
                    }
                }
            }
        }
    }
}
