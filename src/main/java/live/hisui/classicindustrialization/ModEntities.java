package live.hisui.classicindustrialization;

import live.hisui.classicindustrialization.entity.LaserEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ClassicIndustrialization.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<LaserEntity>> LASER = ENTITY_TYPES.register("laser",
            () -> EntityType.Builder.<LaserEntity>of(LaserEntity::new, MobCategory.MISC)
                    .sized((float) 6 /16, (float) 4 /16)
                    .clientTrackingRange(16)
                    .build("laser"));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
