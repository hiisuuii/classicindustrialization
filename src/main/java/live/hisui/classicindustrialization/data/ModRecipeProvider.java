package live.hisui.classicindustrialization.data;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.ModItems;
import live.hisui.classicindustrialization.util.ConventionTags;
import live.hisui.classicindustrialization.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ELECTRIC_HOE)
                .pattern("  H")
                .pattern("CI ")
                .pattern("BU ")
                .define('C', ModItems.BASIC_CIRCUIT)
                .define('H', Items.IRON_HOE)
                .define('U', Tags.Items.INGOTS_COPPER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('B', ModItems.BASIC_BATTERY)
                .unlockedBy("has_circuit",has(ModItems.BASIC_CIRCUIT))
                .unlockedBy("has_hoes",has(ItemTags.HOES))
                .unlockedBy("has_battery",has(ModItems.BASIC_BATTERY)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CHAINSAW)
                .pattern(" II")
                .pattern("CII")
                .pattern("BU ")
                .define('C', ModItems.BASIC_CIRCUIT)
                .define('U', Tags.Items.INGOTS_COPPER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('B', ModItems.BASIC_BATTERY)
                .unlockedBy("has_circuit",has(ModItems.BASIC_CIRCUIT))
                .unlockedBy("has_axes",has(ItemTags.AXES))
                .unlockedBy("has_battery",has(ModItems.BASIC_BATTERY)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DRILL)
                .pattern(" I ")
                .pattern("ICI")
                .pattern("UBU")
                .define('C', ModItems.BASIC_CIRCUIT)
                .define('U', Tags.Items.INGOTS_COPPER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('B', ModItems.BASIC_BATTERY)
                .unlockedBy("has_circuit",has(ModItems.BASIC_CIRCUIT))
                .unlockedBy("has_picks",has(ItemTags.PICKAXES))
                .unlockedBy("has_battery",has(ModItems.BASIC_BATTERY)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIAMOND_DRILL)
                .pattern(" I ")
                .pattern("IBI")
                .pattern("CRC")
                .define('C', ModItems.ADV_CIRCUIT)
                .define('I', Tags.Items.GEMS_DIAMOND)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('B', ModItems.DRILL)
                .unlockedBy("has_diamond_pick",has(Items.DIAMOND_PICKAXE))
                .unlockedBy("has_drill",has(ModItems.DRILL))
                .unlockedBy("has_circuit",has(ModItems.ADV_CIRCUIT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ADV_CHAINSAW)
                .pattern(" C ")
                .pattern("LDL")
                .pattern("ABA")
                .define('C', ModItems.ADV_CIRCUIT)
                .define('L', Tags.Items.GEMS_LAPIS)
                .define('A', ModItems.ADV_ALLOY)
                .define('B', ModItems.EVEN_BIGGER_BATTERY)
                .define('D', ModItems.CHAINSAW)
                .unlockedBy("has_alloy",has(ModItems.ADV_ALLOY))
                .unlockedBy("has_csaw",has(ModItems.CHAINSAW))
                .unlockedBy("has_circuit",has(ModItems.ADV_CIRCUIT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ADV_DRILL)
                .pattern(" C ")
                .pattern("LDL")
                .pattern("ABA")
                .define('C', ModItems.ADV_CIRCUIT)
                .define('L', Tags.Items.GEMS_LAPIS)
                .define('A', ModItems.ADV_ALLOY)
                .define('B', ModItems.EVEN_BIGGER_BATTERY)
                .define('D', ModItems.DRILL)
                .unlockedBy("has_alloy",has(ModItems.ADV_ALLOY))
                .unlockedBy("has_drill",has(ModItems.DRILL))
                .unlockedBy("has_circuit",has(ModItems.ADV_CIRCUIT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NANO_CHAINSAW)
                .pattern(" C ")
                .pattern("LDL")
                .pattern("ABA")
                .define('C', ModItems.ADV_CIRCUIT)
                .define('L', ModItems.INSULATED_GOLD_CABLE)
                .define('A', ModItems.CARBON_PLATE)
                .define('B', ModItems.EVEN_BIGGER_BATTERY)
                .define('D', ModItems.ADV_CHAINSAW)
                .unlockedBy("has_carbon",has(ModItems.CARBON_PLATE))
                .unlockedBy("has_csaw",has(ModItems.ADV_CHAINSAW))
                .unlockedBy("has_circuit",has(ModItems.ADV_CIRCUIT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NANO_DRILL)
                .pattern(" C ")
                .pattern("LDL")
                .pattern("ABA")
                .define('C', ModItems.ADV_CIRCUIT)
                .define('L', ModItems.INSULATED_GOLD_CABLE)
                .define('A', ModItems.CARBON_PLATE)
                .define('B', ModItems.EVEN_BIGGER_BATTERY)
                .define('D', ModItems.ADV_DRILL)
                .unlockedBy("has_carbon",has(ModItems.CARBON_PLATE))
                .unlockedBy("has_drill",has(ModItems.ADV_DRILL))
                .unlockedBy("has_circuit",has(ModItems.ADV_CIRCUIT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.JETPACK)
                .pattern("ICI")
                .pattern("IBI")
                .pattern("R R")
                .define('C', ModItems.ADV_CIRCUIT)
                .define('R',Tags.Items.DUSTS_REDSTONE)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('B', ModItems.BASIC_BATTERY)
                .unlockedBy("has_circuit",has(ModItems.ADV_CIRCUIT))
                .unlockedBy("has_battery",has(ModItems.BASIC_BATTERY)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ELECTRIC_SWORD)
                .pattern("GD ")
                .pattern("GD ")
                .pattern("CBC")
                .define('C', ModItems.CARBON_PLATE)
                .define('G',Tags.Items.DUSTS_GLOWSTONE)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('B', ModItems.EVEN_BIGGER_BATTERY)
                .unlockedBy("has_plate",has(ModItems.CARBON_PLATE))
                .unlockedBy("has_glowstone",has(Tags.Items.DUSTS_GLOWSTONE))
                .unlockedBy("has_battery",has(ModItems.EVEN_BIGGER_BATTERY)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LASER_RIFLE)
                .pattern("RRD")
                .pattern("ABC")
                .pattern(" AA")
                .define('C', ModItems.INSULATED_GOLD_CABLE)
                .define('R',Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('D',Tags.Items.GEMS_DIAMOND)
                .define('A', ModItems.ADV_ALLOY)
                .define('B', ModItems.EVEN_BIGGER_BATTERY)
                .unlockedBy("has_adv_alloy",has(ModItems.ADV_ALLOY)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BASIC_SUBSTRATE, 6)
                .pattern(" D ")
                .pattern("GGG")
                .pattern(" C ")
                .define('G', Tags.Items.GLASS_BLOCKS)
                .define('D',Tags.Items.DYES_GREEN)
                .define('C', Items.CLAY_BALL)
                .unlockedBy("has_copper",has(Tags.Items.INGOTS_COPPER)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ADV_SUBSTRATE, 6)
                .pattern(" D ")
                .pattern("GGG")
                .pattern(" C ")
                .define('G', Items.QUARTZ_BLOCK)
                .define('D',Tags.Items.DYES_RED)
                .define('C', Tags.Items.GLASS_BLOCKS)
                .unlockedBy("has_substrate",has(ModItems.BASIC_SUBSTRATE))
                .unlockedBy("has_quartz",has(Tags.Items.GEMS_QUARTZ)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BASIC_CIRCUIT)
                .pattern("CCC")
                .pattern("RSR")
                .pattern("CCC")
                .define('C', ModItems.INSULATED_COPPER_CABLE)
                .define('R',Tags.Items.DUSTS_REDSTONE)
                .define('S', ModItems.BASIC_SUBSTRATE)
                .unlockedBy("has_substrate",has(ModItems.BASIC_SUBSTRATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ADV_CIRCUIT)
                .pattern("CLC")
                .pattern("RSR")
                .pattern("CLC")
                .define('C', ModItems.INSULATED_GOLD_CABLE)
                .define('R',Tags.Items.DUSTS_REDSTONE)
                .define('L',Tags.Items.GEMS_LAPIS)
                .define('S', ModItems.ADV_SUBSTRATE)
                .unlockedBy("has_substrate",has(ModItems.ADV_SUBSTRATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SMALL_BATTERY)
                .pattern(" C ")
                .pattern("IRI")
                .define('C', ModItems.INSULATED_TIN_CABLE)
                .define('R',Tags.Items.DUSTS_REDSTONE)
                .define('I', ModTags.Items.INGOTS_TIN)
                .unlockedBy("has_cable",has(ModItems.INSULATED_COPPER_CABLE))
                .unlockedBy("has_redstone",has(Tags.Items.DUSTS_REDSTONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BASIC_BATTERY)
                .pattern(" C ")
                .pattern("IRI")
                .pattern("IRI")
                .define('C', ModItems.INSULATED_COPPER_CABLE)
                .define('R',Tags.Items.DUSTS_REDSTONE)
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_cable",has(ModItems.INSULATED_COPPER_CABLE))
                .unlockedBy("has_redstone",has(Tags.Items.DUSTS_REDSTONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EVEN_BIGGER_BATTERY)
                .pattern(" C ")
                .pattern("IRI")
                .pattern("IRI")
                .define('C', ModItems.INSULATED_GOLD_CABLE)
                .define('R',Tags.Items.DUSTS_REDSTONE)
                .define('I', ModItems.CARBON_PLATE)
                .unlockedBy("has_cable",has(ModItems.INSULATED_GOLD_CABLE))
                .unlockedBy("has_carbon",has(ModItems.CARBON_PLATE))
                .unlockedBy("has_redstone",has(Tags.Items.DUSTS_REDSTONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HUGE_BATTERY)
                .pattern("GCG")
                .pattern("CRC")
                .pattern("GCG")
                .define('C', ModItems.SUPERCONDUCTOR)
                .define('R',Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('G', Tags.Items.DUSTS_GLOWSTONE)
                .unlockedBy("has_cable",has(ModItems.SUPERCONDUCTOR))
                .unlockedBy("has_glowstone",has(Tags.Items.DUSTS_GLOWSTONE))
                .unlockedBy("has_redstone",has(Tags.Items.DUSTS_REDSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, new ItemStack(ModItems.UNINSULATED_COPPER_CABLE.get(), 6))
                .pattern("CCC")
                .define('C', Tags.Items.INGOTS_COPPER)
                .unlockedBy("has_copper",has(Tags.Items.INGOTS_COPPER)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, new ItemStack(ModItems.UNINSULATED_TIN_CABLE.get(), 6))
                .pattern("CCC")
                .define('C', ConventionTags.INGOTS_TIN)
                .unlockedBy("has_tin",has(ConventionTags.INGOTS_TIN)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, new ItemStack(ModItems.UNINSULATED_GOLD_CABLE.get(), 6))
                .pattern("CCC")
                .define('C', Tags.Items.INGOTS_GOLD)
                .unlockedBy("has_gold",has(Tags.Items.INGOTS_GOLD)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, new ItemStack(ModItems.INSULATED_COPPER_CABLE.get(), 6))
                .pattern("RRR")
                .pattern("CCC")
                .pattern("RRR")
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('R', ModTags.Items.INSULATION)
                .unlockedBy("has_copper",has(Tags.Items.INGOTS_COPPER))
                .unlockedBy("has_insulation", has(ModTags.Items.INSULATION)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, new ItemStack(ModItems.INSULATED_TIN_CABLE.get(), 6))
                .pattern("RRR")
                .pattern("CCC")
                .pattern("RRR")
                .define('C', ConventionTags.INGOTS_TIN)
                .define('R', ModTags.Items.INSULATION)
                .unlockedBy("has_tin",has(ConventionTags.INGOTS_TIN))
                .unlockedBy("has_insulation", has(ModTags.Items.INSULATION)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, new ItemStack(ModItems.INSULATED_GOLD_CABLE.get(), 6))
                .pattern("RRR")
                .pattern("CCC")
                .pattern("RRR")
                .define('C', Tags.Items.INGOTS_GOLD)
                .define('R', ModTags.Items.INSULATION)
                .unlockedBy("has_gold",has(Tags.Items.INGOTS_GOLD))
                .unlockedBy("has_insulation", has(ModTags.Items.INSULATION)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, new ItemStack(ModItems.SUPERCONDUCTOR.get(), 6))
                .pattern("RRR")
                .pattern("CCC")
                .pattern("RRR")
                .define('C', ModItems.GRAPHENE)
                .define('R', ModTags.Items.INSULATION)
                .unlockedBy("has_graphene",has(ModItems.GRAPHENE))
                .unlockedBy("has_insulation", has(ModTags.Items.INSULATION)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, new ItemStack(ModItems.MIXED_INGOT.get(), 2))
                .pattern("III")
                .pattern("BBB")
                .pattern("TTT")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('B', ModTags.Items.INGOTS_BRONZE)
                .define('T', ModTags.Items.INGOTS_TIN)
                .unlockedBy("has_iron",has(Tags.Items.INGOTS_IRON))
                .unlockedBy("has_tin",has(ModTags.Items.INGOTS_TIN))
                .unlockedBy("has_bronze", has(ModTags.Items.INGOTS_BRONZE)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.INSULATED_COPPER_CABLE)
                .requires(ModItems.UNINSULATED_COPPER_CABLE)
                .requires(ModTags.Items.INSULATION)
                .unlockedBy("has_copper",has(Tags.Items.INGOTS_COPPER))
                .unlockedBy("has_insulation", has(ModTags.Items.INSULATION))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ClassicIndustrialization.MODID,"insulated_copper_cable_single"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.INSULATED_TIN_CABLE)
                .requires(ModItems.UNINSULATED_TIN_CABLE)
                .requires(ModTags.Items.INSULATION)
                .unlockedBy("has_tin",has(ConventionTags.INGOTS_TIN))
                .unlockedBy("has_insulation", has(ModTags.Items.INSULATION))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath(ClassicIndustrialization.MODID,"insulated_tin_cable_single"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.INSULATED_GOLD_CABLE)
                .requires(ModItems.UNINSULATED_GOLD_CABLE)
                .requires(ModTags.Items.INSULATION)
                .unlockedBy("has_gold",has(Tags.Items.INGOTS_GOLD))
                .unlockedBy("has_insulation", has(ModTags.Items.INSULATION))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ClassicIndustrialization.MODID,"insulated_gold_cable_single"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_TIN_BLOCK)
                .requires(ModItems.RAW_TIN, 9)
                .unlockedBy("has_item",has(ModItems.RAW_TIN))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TIN_BLOCK)
                .requires(Ingredient.of(ModTags.Items.INGOTS_TIN), 9)
                .unlockedBy("has_item",has(ModTags.Items.INGOTS_TIN))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BRONZE_BLOCK)
                .requires(Ingredient.of(ModTags.Items.INGOTS_BRONZE), 9)
                .unlockedBy("has_item",has(ModTags.Items.INGOTS_BRONZE))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CARBON_FIBRE)
                .requires(ModItems.CARBON_DUST, 4)
                .unlockedBy("has_carbon",has(ModItems.CARBON_DUST))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CARBON_LUMP)
                .requires(ModItems.CARBON_FIBRE, 2)
                .unlockedBy("has_carbon",has(ModItems.CARBON_FIBRE))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BRONZE_DUST, 4)
                .requires(ModTags.Items.DUSTS_TIN)
                .requires(Ingredient.of(ModTags.Items.DUSTS_COPPER), 3)
                .unlockedBy("has_copper",has(ModTags.Items.DUSTS_COPPER))
                .unlockedBy("has_tin",has(ModTags.Items.DUSTS_TIN))
                .save(recipeOutput);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.IRON_DUST), RecipeCategory.MISC, Items.IRON_INGOT,
                        0.7f, 200)
                .unlockedBy("has_dust",has(ModItems.IRON_DUST))
                .save(recipeOutput,"iron_dust_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.IRON_DUST), RecipeCategory.MISC, Items.IRON_INGOT,
                        0.7f, 100)
                .unlockedBy("has_dust",has(ModItems.IRON_DUST))
                .save(recipeOutput,"iron_dust_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.COPPER_DUST), RecipeCategory.MISC, Items.COPPER_INGOT,
                        0.7f, 200)
                .unlockedBy("has_dust",has(ModItems.COPPER_DUST))
                .save(recipeOutput,"copper_dust_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.COPPER_DUST), RecipeCategory.MISC, Items.COPPER_INGOT,
                        0.7f, 100)
                .unlockedBy("has_dust",has(ModItems.COPPER_DUST))
                .save(recipeOutput,"copper_dust_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.GOLD_DUST), RecipeCategory.MISC, Items.GOLD_INGOT,
                        0.7f, 200)
                .unlockedBy("has_dust",has(ModItems.GOLD_DUST))
                .save(recipeOutput,"gold_dust_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.GOLD_DUST), RecipeCategory.MISC, Items.GOLD_INGOT,
                        0.7f, 100)
                .unlockedBy("has_dust",has(ModItems.GOLD_DUST))
                .save(recipeOutput,"gold_dust_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.BRONZE_DUST), RecipeCategory.MISC, ModItems.BRONZE_INGOT.get(),
                        0.7f, 200)
                .unlockedBy("has_dust",has(ModItems.BRONZE_DUST))
                .save(recipeOutput,"bronze_dust_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.BRONZE_DUST), RecipeCategory.MISC, ModItems.BRONZE_INGOT.get(),
                        0.7f, 100)
                .unlockedBy("has_dust",has(ModItems.BRONZE_DUST))
                .save(recipeOutput,"bronze_dust_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.TIN_DUST), RecipeCategory.MISC, ModItems.TIN_INGOT.get(),
                        0.7f, 200)
                .unlockedBy("has_dust",has(ModItems.TIN_DUST))
                .save(recipeOutput,"tin_dust_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.TIN_DUST), RecipeCategory.MISC, ModItems.TIN_INGOT.get(),
                        0.7f, 100)
                .unlockedBy("has_dust",has(ModItems.TIN_DUST))
                .save(recipeOutput,"tin_dust_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_TIN), RecipeCategory.MISC, ModItems.TIN_INGOT.get(),
                        0.7f, 200)
                .unlockedBy("has_ore",has(ModItems.RAW_TIN))
                .save(recipeOutput,"tin_raw_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_TIN), RecipeCategory.MISC, ModItems.TIN_INGOT.get(),
                        0.7f, 100)
                .unlockedBy("has_ore",has(ModItems.RAW_TIN))
                .save(recipeOutput,"tin_raw_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.TIN_ORE), RecipeCategory.MISC, ModItems.TIN_INGOT.get(),
                        0.7f, 200)
                .unlockedBy("has_ore",has(ModItems.TIN_ORE))
                .save(recipeOutput,"tin_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.TIN_ORE), RecipeCategory.MISC, ModItems.TIN_INGOT.get(),
                        0.7f, 100)
                .unlockedBy("has_ore",has(ModItems.TIN_ORE))
                .save(recipeOutput,"tin_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.DEEPSLATE_TIN_ORE), RecipeCategory.MISC, ModItems.TIN_INGOT.get(),
                        0.7f, 200)
                .unlockedBy("has_ore",has(ModItems.DEEPSLATE_TIN_ORE))
                .save(recipeOutput,"deep_tin_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.DEEPSLATE_TIN_ORE), RecipeCategory.MISC, ModItems.TIN_INGOT.get(),
                        0.7f, 100)
                .unlockedBy("has_ore",has(ModItems.DEEPSLATE_TIN_ORE))
                .save(recipeOutput,"deep_tin_ore_blasting");

    }
}
