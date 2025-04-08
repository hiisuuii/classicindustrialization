package live.hisui.classicindustrialization.item;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.capability.CustomComponentEnergyStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import java.text.NumberFormat;
import java.util.List;
import java.util.Optional;

public class EnergyStoringItem extends Item {
    public final int maxCapacity;
    public final int transferRate;

    public EnergyStoringItem(int maxCapacity, int transferRate) {
        super(new Item.Properties().stacksTo(1));
        this.maxCapacity = maxCapacity;
        this.transferRate = transferRate;
    }
    public EnergyStoringItem(Properties properties, int maxCapacity, int transferRate){
        super(properties);
        this.maxCapacity = maxCapacity;
        this.transferRate = transferRate;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nonnull TooltipContext context, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);
        IEnergyStorage storage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if(storage != null) {
            int energy = storage.getEnergyStored();
            NumberFormat format = NumberFormat.getInstance();
            tooltip.add(Component.translatable("tooltip.classicindustrialization.energy_stored", format.format(energy), format.format(storage.getMaxEnergyStored()))
                    .withStyle(ChatFormatting.GRAY));
        }
    }
    @Override
    public boolean isBarVisible(@Nonnull ItemStack itemStack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        IEnergyStorage storage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (storage != null) {
            return Math.round((13.0F / storage.getMaxEnergyStored() * storage.getEnergyStored()));
        }
        return 0;
    }
    @Override
    public int getBarColor(@Nonnull ItemStack stack) {
        int stackMaxEnergy = Optional.ofNullable(stack.getCapability(Capabilities.EnergyStorage.ITEM))
                .map(IEnergyStorage::getMaxEnergyStored).orElse(0);
        int stackStoredEnergy = Optional.ofNullable(stack.getCapability(Capabilities.EnergyStorage.ITEM))
                .map(IEnergyStorage::getEnergyStored).orElse(0);
        float f = Math.max(0.0F, (float)stackStoredEnergy / (float)stackMaxEnergy);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

    public void setEnergy(ItemStack stack, int energy, boolean simulate) {
        Optional.ofNullable(stack.getCapability(Capabilities.EnergyStorage.ITEM)).ifPresent( cap -> {
            if(cap instanceof CustomComponentEnergyStorage ces) {
                ces.setEnergy(energy);
            }
        });
    }
    public int receiveEnergy(ItemStack stack, int toReceive, boolean simulate) {
        return Optional.ofNullable(stack.getCapability(Capabilities.EnergyStorage.ITEM))
                .map(cap -> cap.receiveEnergy(toReceive, simulate))
                .orElse(0);
    }

    public int extractEnergy(ItemStack stack, int toExtract, boolean simulate) {
        return Optional.ofNullable(stack.getCapability(Capabilities.EnergyStorage.ITEM))
                .map(cap -> cap.extractEnergy(toExtract, simulate))
                .orElse(0);
    }

    public int getEnergyStored(ItemStack stack) {
        return Optional.ofNullable(stack.getCapability(Capabilities.EnergyStorage.ITEM))
                .map(IEnergyStorage::getEnergyStored)
                .orElse(0);
    }

    public int getMaxEnergyStored(ItemStack stack) {
        return Optional.ofNullable(stack.getCapability(Capabilities.EnergyStorage.ITEM))
                .map(IEnergyStorage::getMaxEnergyStored)
                .orElse(0);
    }
}
