package live.hisui.classicindustrialization.data;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.ModBlocks;
import live.hisui.classicindustrialization.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ClassicIndustrialization.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        simpleBlockItem(ModBlocks.TIN_ORE.get());
        simpleBlockItem(ModBlocks.DEEPSLATE_TIN_ORE.get());
        simpleBlockItem(ModBlocks.RAW_TIN_BLOCK.get());
        simpleBlockItem(ModBlocks.BRONZE_BLOCK.get());
        simpleBlockItem(ModBlocks.TIN_BLOCK.get());

        basicItem(ModItems.SMALL_BATTERY.get());
        basicItem(ModItems.BASIC_BATTERY.get());
        basicItem(ModItems.EVEN_BIGGER_BATTERY.get());
        basicItem(ModItems.HUGE_BATTERY.get());
        basicItem(ModItems.CREATIVE_BATTERY.get());

        basicItem(ModItems.UNINSULATED_COPPER_CABLE.get());
        basicItem(ModItems.INSULATED_COPPER_CABLE.get());
        basicItem(ModItems.UNINSULATED_TIN_CABLE.get());
        basicItem(ModItems.INSULATED_TIN_CABLE.get());
        basicItem(ModItems.UNINSULATED_GOLD_CABLE.get());
        basicItem(ModItems.INSULATED_GOLD_CABLE.get());
        basicItem(ModItems.BASIC_CIRCUIT.get());
        basicItem(ModItems.ADV_CIRCUIT.get());
        basicItem(ModItems.BASIC_SUBSTRATE.get());
        basicItem(ModItems.ADV_SUBSTRATE.get());
        basicItem(ModItems.SUPERCONDUCTOR.get());
        basicItem(ModItems.CARBON_DUST.get());
        basicItem(ModItems.CARBON_FIBRE.get());
        basicItem(ModItems.CARBON_LUMP.get());
        basicItem(ModItems.CARBON_PLATE.get());
        basicItem(ModItems.GOLD_DUST.get());
        basicItem(ModItems.IRON_DUST.get());
        basicItem(ModItems.COPPER_DUST.get());
        basicItem(ModItems.TIN_DUST.get());
        basicItem(ModItems.BRONZE_DUST.get());

        basicItem(ModItems.GRAPHENE.get());
        basicItem(ModItems.MIXED_INGOT.get());

        basicItem(ModItems.TIN_INGOT.get());
        basicItem(ModItems.RAW_TIN.get());
        basicItem(ModItems.BRONZE_INGOT.get());

        basicItem(ModItems.ADV_ALLOY.get());

//        handheldItem(ModItems.ELECTRIC_HOE.get());
        handheldItem(ModItems.CHAINSAW.get());
        handheldItem(ModItems.DRILL.get());
        handheldItem(ModItems.DIAMOND_DRILL.get());
        handheldItem(ModItems.ADV_CHAINSAW.get());
        handheldItem(ModItems.ADV_DRILL.get());
        handheldItem(ModItems.NANO_CHAINSAW.get());
        handheldItem(ModItems.NANO_DRILL.get());
        handheldItem(ModItems.QUANTUM_CHAINSAW.get());
        handheldItem(ModItems.QUANTUM_DRILL.get());
//        handheldItem(ModItems.VAJRA.get());
        handheldItem(ModItems.LASER_RIFLE.get());
        handheldItem(ModItems.ELECTRIC_SWORD.get())
                .texture("layer1",ClassicIndustrialization.modLoc("item/electric_sword_blade"))
                .texture("layer2",ClassicIndustrialization.modLoc("item/electric_sword_overlay"));
    }
}
