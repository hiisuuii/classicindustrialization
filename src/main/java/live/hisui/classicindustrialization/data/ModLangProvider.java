package live.hisui.classicindustrialization.data;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.ModItems;
import live.hisui.classicindustrialization.util.Util;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.fml.common.Mod;
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
        addSimple(ModItems.SUPERCONDUCTOR);
        addSimple(ModItems.RAW_TIN);
        addSimple(ModItems.TIN_ORE);
        addSimple(ModItems.DEEPSLATE_TIN_ORE);
        addSimple(ModItems.TIN_INGOT);
        addSimple(ModItems.RAW_TIN_BLOCK);
        addSimple(ModItems.TIN_BLOCK);
        addSimple(ModItems.BRONZE_INGOT);
        addSimple(ModItems.BRONZE_BLOCK);
        addSimple(ModItems.BRONZE_DUST);
        addSimple(ModItems.COPPER_DUST);
        addSimple(ModItems.GOLD_DUST);
        addSimple(ModItems.IRON_DUST);
        addSimple(ModItems.TIN_DUST);
        addSimple(ModItems.CARBON_DUST);
        addSimple(ModItems.CARBON_FIBRE);
        addSimple(ModItems.CARBON_LUMP);
        addSimple(ModItems.CARBON_PLATE);
        addSimple(ModItems.GRAPHENE);
        addSimple(ModItems.BASIC_SUBSTRATE);
        addSimple(ModItems.ADV_SUBSTRATE);
        addSimple(ModItems.CREATIVE_BATTERY);
        add(ModItems.ELECTRIC_SWORD.get(), "NanoSaber");
        add(ModItems.GRAVI_CHESTPLATE.get(), "Gravi Chestplate");
        addSimple(ModItems.JETPACK);
        add(ModItems.MIXED_INGOT.get(), "Mixed Metal Ingot");
        add("tooltip.classicindustrialization.energy_stored","%s/%s FE");
        add("itemgroup.classicindustrialization","Classic Industrialization");
        add(ModItems.EVEN_BIGGER_BATTERY.get(), "Advanced Battery");
        add(ModItems.HUGE_BATTERY.get(), "Energy Crystal");

    }

    private void addSimple(DeferredItem<Item> item){
        String name = Util.formatString(item.getId().getPath());
        this.add(item.get(), name);
    }
}
