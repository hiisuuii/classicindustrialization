package live.hisui.classicindustrialization.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class ElectricToolItem extends EnergyStoringItem {
    protected int ENERGY_TO_MINE = 200;
    protected int ENERGY_TO_HURT = 400;

    public ElectricToolItem(Tier tier, Properties properties, TagKey<Block> mineableTag, float speed, int maxEnergy) {
        this(tier, properties, List.of(mineableTag), speed, maxEnergy);
    }
    public ElectricToolItem(Tier tier, Properties properties, List<TagKey<Block>> mineableTags, float speed, int maxEnergy) {
        super(properties.component(DataComponents.TOOL,
                        createCustomToolProperties(tier.getIncorrectBlocksForDrops(), mineableTags, speed))
                .component(DataComponents.MAX_STACK_SIZE, 1), maxEnergy, 80000);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(stack.getItem() instanceof EnergyStoringItem energyStoringItem) {
            energyStoringItem.extractEnergy(stack, ENERGY_TO_HURT, false);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        if(stack.getItem() instanceof EnergyStoringItem energyStoringItem) {
            energyStoringItem.extractEnergy(stack, ENERGY_TO_MINE, false);
        }
        return super.mineBlock(stack, level, state, pos, miningEntity);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if(stack.getItem() instanceof EnergyStoringItem energyStoringItem) {
            if (energyStoringItem.getEnergyStored(stack) < ENERGY_TO_MINE) return 1.0f;
        }
        return super.getDestroySpeed(stack, state);
    }

    public static Tool createCustomToolProperties(TagKey<Block> incorrectTag, List<TagKey<Block>> minesTags, float speed) {
        List<Tool.Rule> rules = new ArrayList<>();
        rules.add(Tool.Rule.deniesDrops(incorrectTag));
        for(TagKey<Block> tag : minesTags) {
            rules.add(Tool.Rule.minesAndDrops(tag, speed));
        }
        return new Tool(rules, 1.0f, 1);
    }
    public static Tool createCustomToolProperties(TagKey<Block> incorrectTag, TagKey<Block> minesTag, float speed) {
        return createCustomToolProperties(incorrectTag, List.of(minesTag), speed);
    }
}
