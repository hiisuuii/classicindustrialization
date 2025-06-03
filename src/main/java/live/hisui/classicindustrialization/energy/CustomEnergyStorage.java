package live.hisui.classicindustrialization.energy;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage {
    public CustomEnergyStorage(int capacity) {
        super(capacity);
    }

    public CustomEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    @Override
    public boolean canReceive() {
        return super.canReceive() && this.getEnergyStored() < this.getMaxEnergyStored();
    }

    public boolean isFull(){
        return this.getEnergyStored() >= this.getMaxEnergyStored();
    }

    public int receiveInternal(int toReceive, boolean simulate){
        if (isFull() || toReceive <= 0) {
            return 0;
        }

        int energyReceived = Mth.clamp(this.capacity - this.energy, 0, toReceive);
//        ClassicIndustrialization.LOGGER.debug("Energy Received: {}", energyReceived);
        if (!simulate)
            this.energy += energyReceived;
        return energyReceived;
    }
}
