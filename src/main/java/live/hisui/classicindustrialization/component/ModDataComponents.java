package live.hisui.classicindustrialization.component;

import com.mojang.serialization.Codec;
import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.item.LaserMode;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.NeoForgeExtraCodecs;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ClassicIndustrialization.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> FLUID_STORAGE =
            register("fluid_storage", builder -> builder.persistent(SimpleFluidContent.CODEC)
                    .networkSynchronized(SimpleFluidContent.STREAM_CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ENERGY_STORAGE =
            register("energy_storage",
                    builder -> builder.persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> ITEM_ENABLED =
            register("item_enabled",
                    builder -> builder.persistent(Codec.BOOL)
                            .networkSynchronized(ByteBufCodecs.BOOL));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Byte>> LASER_MODE =
            register("laser_mode",
                    builder -> builder.persistent(Codec.BYTE)
                            .networkSynchronized(ByteBufCodecs.BYTE));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<LaserMode>> LASER_MODE_ALT =
            register("laser_mode_alt",
                    builder -> builder.persistent(LaserMode.CODEC)
                            .networkSynchronized(ByteBufCodecs.fromCodec(LaserMode.CODEC)));


    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name,
                                                                                          UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus bus){
        DATA_COMPONENT_TYPES.register(bus);
    }
}
