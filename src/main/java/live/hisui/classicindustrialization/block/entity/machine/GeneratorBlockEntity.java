package live.hisui.classicindustrialization.block.entity.machine;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.energy.EnergyTiers;
import live.hisui.classicindustrialization.menu.GeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class GeneratorBlockEntity extends BaseContainerBlockEntity {
    private NonNullList<ItemStack> ITEMS = NonNullList.withSize(2, ItemStack.EMPTY);
    protected final int FUEL_SLOT_ID = 0;
    protected final int CHARGE_SLOT_ID = 1;
    private long ENERGY_TO_GENERATE;
    public GeneratorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ClassicIndustrialization.GENERATOR_BE.get(), pos, blockState);
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

    private long getEnergyToGenerate(){
        return this.ENERGY_TO_GENERATE;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GeneratorBlockEntity blockEntity){
        ItemStack fuelItem = blockEntity.getItem(blockEntity.FUEL_SLOT_ID);
        int burnTime = fuelItem.getBurnTime(RecipeType.SMELTING);
        if(burnTime != 0 && blockEntity.getEnergyToGenerate() <= 0) {
            fuelItem.shrink(1);
            blockEntity.addEnergyToGenerate(burnTime * 10L);
            IEnergyStorage storage = level.getCapability(Capabilities.EnergyStorage.BLOCK, pos, state.getValue(BlockStateProperties.HORIZONTAL_FACING));
            storage.receiveEnergy(EnergyTiers.LOW_VOLTAGE.getProductionRate(), false);
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new GeneratorMenu(containerId, inventory, this);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.setItems(NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY));
        ContainerHelper.loadAllItems(tag, this.getItems(), registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.getItems(), registries);
    }

    @Override
    public int getContainerSize() {
        return 2;
    }
}
