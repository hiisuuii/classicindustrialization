package live.hisui.classicindustrialization.block.machine;

import net.minecraft.world.level.block.BaseEntityBlock;

public abstract class ProviderBase extends BaseEntityBlock {
    protected ProviderBase(Properties properties) {
        super(properties);
    }

    public abstract int getMaxOutputRate();

    public abstract int getGenerationRate();

    public abstract int getMaxEnergyStored();

    public abstract int getCurrentEnergyStored();

}
