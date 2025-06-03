package live.hisui.classicindustrialization.block.entity.machine;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.energy.CustomEnergyStorage;
import live.hisui.classicindustrialization.menu.GeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GeneratorBlockEntity extends BaseContainerBlockEntity {
    private NonNullList<ItemStack> ITEMS = NonNullList.withSize(2, ItemStack.EMPTY);
    protected final int FUEL_SLOT_ID = 0;
    protected final int CHARGE_SLOT_ID = 1;
    private long ENERGY_TO_GENERATE;
    private CustomEnergyStorage energyStorage = new CustomEnergyStorage(64000, 0, 32);

    int burnTimeLeft;
    int totalBurnTime;
    int propertyDelegate_energyStored;
    int propertyDelegate_energyCapacity;

    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int p_58431_) {
            return switch (p_58431_) {
                case 0 -> {
                    if (totalBurnTime > Short.MAX_VALUE) {
                        // Neo: preserve litTime / litDuration ratio on the client as data slots are synced as shorts.
                        yield net.minecraft.util.Mth.floor(((double) burnTimeLeft / totalBurnTime) * Short.MAX_VALUE);
                    }

                    yield GeneratorBlockEntity.this.burnTimeLeft;
                }
                case 1 -> Math.min(GeneratorBlockEntity.this.totalBurnTime, Short.MAX_VALUE);
                case 2 -> GeneratorBlockEntity.this.propertyDelegate_energyStored;
                case 3 -> GeneratorBlockEntity.this.propertyDelegate_energyCapacity;
                default -> 0;
            };
        }

        @Override
        public void set(int p_58433_, int p_58434_) {
            switch (p_58433_) {
                case 0:
                    GeneratorBlockEntity.this.burnTimeLeft = p_58434_;
                    break;
                case 1:
                    GeneratorBlockEntity.this.totalBurnTime = p_58434_;
                    break;
                case 2:
                    GeneratorBlockEntity.this.propertyDelegate_energyStored = p_58434_;
                    break;
                case 3:
                    GeneratorBlockEntity.this.propertyDelegate_energyCapacity = p_58434_;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    public GeneratorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ClassicIndustrialization.GENERATOR_BE.get(), pos, blockState);
    }

    public CustomEnergyStorage getEnergyStorage() {
        return energyStorage;
    }


    @Override
    protected Component getDefaultName() {
        return Component.literal("Generator");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return ITEMS;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.ITEMS = items;
    }

    private void addEnergyToGenerate(long energy){
        this.ENERGY_TO_GENERATE += energy;
    }

    private void takeEnergyToGenerate(long energy){
        this.ENERGY_TO_GENERATE -= energy;
    }

    private long getEnergyToGenerate(){
        return this.ENERGY_TO_GENERATE;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GeneratorBlockEntity blockEntity){
        boolean changed = false;
        ItemStack fuelItem = blockEntity.getItem(blockEntity.FUEL_SLOT_ID);
        int burnTime = fuelItem.getBurnTime(RecipeType.SMELTING);
        if(burnTime != 0 && blockEntity.getEnergyToGenerate() <= 0 && blockEntity.getEnergyStorage().getEnergyStored() < blockEntity.getEnergyStorage().getMaxEnergyStored()) {
            fuelItem.shrink(1);
            blockEntity.addEnergyToGenerate(burnTime * 10L);
            blockEntity.burnTimeLeft = (int) (blockEntity.getEnergyToGenerate() / 32);
            blockEntity.totalBurnTime = blockEntity.burnTimeLeft;
            changed = true;
        } else if(blockEntity.getEnergyToGenerate() > 0){
            int energyReceived = blockEntity.getEnergyStorage().receiveInternal((int) Math.min(32, blockEntity.getEnergyToGenerate()), false);
            blockEntity.takeEnergyToGenerate(energyReceived);
            changed = true;
        }
        if(blockEntity.getEnergyToGenerate() > 0){
            blockEntity.burnTimeLeft--;
            changed = true;
        }


        ClassicIndustrialization.LOGGER.debug("BE values: {}, {}, {}, {}", blockEntity.burnTimeLeft, blockEntity.totalBurnTime, blockEntity.propertyDelegate_energyStored, blockEntity.propertyDelegate_energyCapacity);

        blockEntity.propertyDelegate_energyStored = blockEntity.getEnergyStorage().getEnergyStored();
        blockEntity.propertyDelegate_energyCapacity = blockEntity.getEnergyStorage().getMaxEnergyStored();
//        ClassicIndustrialization.LOGGER.debug("Energy to generate: {}", blockEntity.ENERGY_TO_GENERATE);
//        ClassicIndustrialization.LOGGER.debug("Generator energy stored: {}", blockEntity.getEnergyStorage().getEnergyStored());

        if(changed){
            blockEntity.setChanged();
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new GeneratorMenu(containerId, inventory, this, this.dataAccess);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.setItems(NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY));
        energyStorage.deserializeNBT(registries, tag.get("energy_stored"));
        ContainerHelper.loadAllItems(tag, this.getItems(), registries);
        this.burnTimeLeft = tag.getInt("burnTimeLeft");
        this.totalBurnTime = tag.getInt("totalBurnTime");
        this.propertyDelegate_energyStored = tag.getInt("pd_energyStored");
        this.propertyDelegate_energyCapacity = tag.getInt("pd_energyCapacity");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("burnTimeLeft", this.burnTimeLeft);
        tag.putInt("totalBurnTime", this.totalBurnTime);
        tag.putInt("pd_energyStored", this.propertyDelegate_energyStored);
        tag.putInt("pd_energyCapacity", this.propertyDelegate_energyCapacity);
        tag.put("energy_stored",energyStorage.serializeNBT(registries));
        ContainerHelper.saveAllItems(tag, this.getItems(), registries);
        setChanged();
    }

    @Override
    public int getContainerSize() {
        return 2;
    }
}
