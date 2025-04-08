package live.hisui.classicindustrialization.data;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.ModItems;
import live.hisui.classicindustrialization.util.Util;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, ClassicIndustrialization.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addSimple(ModItems.CHAINSAW);
        addSimple(ModItems.ELECTRIC_HOE);
        addSimple(ModItems.DRILL);
        addSimple(ModItems.DIAMOND_DRILL);
        addSimple(ModItems.ADV_CHAINSAW);
        addSimple(ModItems.ADV_DRILL);
        addSimple(ModItems.NANO_CHAINSAW);
        addSimple(ModItems.NANO_DRILL);
        addSimple(ModItems.QUANTUM_CHAINSAW);
        addSimple(ModItems.QUANTUM_DRILL);
        addSimple(ModItems.VAJRA);
        addSimple(ModItems.SMALL_BATTERY);
        addSimple(ModItems.BASIC_BATTERY);
        addSimple(ModItems.EVEN_BIGGER_BATTERY);
        addSimple(ModItems.HUGE_BATTERY);
        addSimple(ModItems.LASER_RIFLE);
        addSimple(ModItems.UNINSULATED_COPPER_CABLE);
        addSimple(ModItems.INSULATED_COPPER_CABLE);
        addSimple(ModItems.UNINSULATED_GOLD_CABLE);
        addSimple(ModItems.INSULATED_GOLD_CABLE);
        addSimple(ModItems.UNINSULATED_TIN_CABLE);
        addSimple(ModItems.INSULATED_TIN_CABLE);
        addSimple(ModItems.BASIC_CIRCUIT);
        addSimple(ModItems.ADV_CIRCUIT);
        addSimple(ModItems.ADV_ALLOY);
        add("tooltip.classicindustrialization.energy_stored","%s/%s FE");
        add("itemgroup.classicindustrialization","Classic Industrialization");
    }

    private void addSimple(DeferredItem<Item> item){
        String name = Util.formatString(item.getId().getPath());
        this.add(item.get(), name);
    }
}
