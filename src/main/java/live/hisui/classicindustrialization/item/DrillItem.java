package live.hisui.classicindustrialization.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

import java.util.List;

public class DrillItem extends ElectricToolItem {
    public DrillItem(Tier tier, Item.Properties properties, float speed, int maxEnergy) {
        super(tier, properties, List.of(BlockTags.MINEABLE_WITH_SHOVEL, BlockTags.MINEABLE_WITH_PICKAXE), speed, maxEnergy);
    }
}
