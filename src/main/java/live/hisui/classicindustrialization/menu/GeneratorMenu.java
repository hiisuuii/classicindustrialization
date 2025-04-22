package live.hisui.classicindustrialization.menu;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.item.EnergyStoringItem;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.jetbrains.annotations.Nullable;

public class GeneratorMenu extends AbstractContainerMenu {

    private final Container container;

    public GeneratorMenu(int containerId, Inventory playerInventory, Container furnaceContainer){
        super(ClassicIndustrialization.GENERATOR_MENU.get(), containerId);
        this.container = furnaceContainer;


        this.addSlot(new Slot(container, 0, 56, 17));
        this.addSlot(new Slot(container, 1, 56, 53));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    public GeneratorMenu(int containerId, Inventory playerInventory) {
        this(containerId,playerInventory,new SimpleContainer(2));
    }



    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                return ItemStack.EMPTY;
            }

            slot.onQuickCraft(itemstack1, itemstack);
            if (index != 1 && index != 0) {
                if ((itemstack1.getItem() instanceof EnergyStoringItem)) {
                    if (!this.moveItemStackTo(itemstack1, 1, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (AbstractFurnaceBlockEntity.isFuel(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 0, 0, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 2 && index < 29) {
                    if (!this.moveItemStackTo(itemstack1, 29, 38, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 29 && index < 38 && !this.moveItemStackTo(itemstack1, 2, 29, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 2, 38, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
