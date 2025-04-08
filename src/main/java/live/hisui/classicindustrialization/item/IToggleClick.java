package live.hisui.classicindustrialization.item;

import live.hisui.classicindustrialization.component.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public interface IToggleClick {

    default void onToggle(ItemStack stack, Player player) {
        if (Optional.ofNullable(stack.get(ModDataComponents.ITEM_ENABLED)).orElse(false)) {
            stack.set(ModDataComponents.ITEM_ENABLED, false);
        } else {
            stack.set(ModDataComponents.ITEM_ENABLED, true);
        }
    }
}
