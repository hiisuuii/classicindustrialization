package live.hisui.classicindustrialization.item;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum LaserMode implements StringRepresentable{
    NORMAL("Normal", 1250),
    LONG_RANGE("Long Range",5000),
    HORIZONTAL("Horizontal", 5000),
    EXPLOSIVE("Explosive", 5000),
    SCATTER("Scatter", 10000),
    IGNITE("Ignite", 2500),
    HAMMER("3x3", 5000),
    PRECISION("Precision", 100);
    private final String name;
    private final int energyAmount;
    public static final Codec<LaserMode> CODEC = StringRepresentable.fromEnum(LaserMode::values);

    LaserMode(String name, int energyAmount){
        this.name=name;
        this.energyAmount = energyAmount;
    }
    public String getName(){
        return name;
    }
    public int getEnergyCost() {
        return energyAmount;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name.toLowerCase(Locale.ROOT);
    }

    public LaserMode next() {
        LaserMode[] values = values();
        return values[((this.ordinal() + 1) % values.length)];
    }
}
