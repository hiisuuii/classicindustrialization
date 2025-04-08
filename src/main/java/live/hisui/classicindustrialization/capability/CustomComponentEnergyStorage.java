package live.hisui.classicindustrialization.capability;

import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.neoforge.energy.ComponentEnergyStorage;

public class CustomComponentEnergyStorage extends ComponentEnergyStorage {

    public CustomComponentEnergyStorage(MutableDataComponentHolder parent, DataComponentType<Integer> energyComponent, int capacity, int maxReceive, int maxExtract) {
        super(parent, energyComponent, capacity, maxReceive, maxExtract);
    }

    public CustomComponentEnergyStorage(MutableDataComponentHolder parent, DataComponentType<Integer> energyComponent, int capacity, int maxTransfer) {
        super(parent, energyComponent, capacity, maxTransfer);
    }

    public CustomComponentEnergyStorage(MutableDataComponentHolder parent, DataComponentType<Integer> energyComponent, int capacity) {
        super(parent, energyComponent, capacity);
    }

    public void setEnergy(int energy){
        super.setEnergy(energy);
    }
}
