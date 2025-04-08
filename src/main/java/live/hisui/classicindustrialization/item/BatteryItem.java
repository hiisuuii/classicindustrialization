package live.hisui.classicindustrialization.item;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BatteryItem extends EnergyStoringItem {
    public BatteryItem(int maxCapacity, int transferRate) {
        super(maxCapacity, transferRate);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        for(ItemStack itemStack : player.getInventory().items) {
            if(itemStack.getItem() instanceof EnergyStoringItem energyStoringItem) {
                energyStoringItem.setEnergy(itemStack, energyStoringItem.getMaxEnergyStored(itemStack), false);
            }
        }
        return super.use(level, player, usedHand);
    }
}
