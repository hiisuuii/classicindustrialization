package live.hisui.classicindustrialization.render;

import com.mojang.blaze3d.vertex.PoseStack;
import live.hisui.classicindustrialization.ClassicIndustrialization;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

public class FluidCellRenderer extends BlockEntityWithoutLevelRenderer {

    private static final ResourceLocation BASE_MODEL = ClassicIndustrialization.modLoc("item/fluid_cell");

    public FluidCellRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        IFluidHandlerItem cap = stack.getCapability(Capabilities.FluidHandler.ITEM);
        if(cap != null) {
            FluidStack fluidStack = cap.getFluidInTank(0);
            if(!fluidStack.isEmpty()){
                IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
                ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture();
                TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);

                var renderer = Minecraft.getInstance().getItemRenderer();
                var basemodel = renderer.getItemModelShaper().getModelManager().getModel(ModelResourceLocation.inventory(BASE_MODEL));
                var rendertype = basemodel.getRenderTypes(stack, Minecraft.useFancyGraphics());
                var foilbuffer = ItemRenderer.getFoilBufferDirect(buffer, rendertype.getFirst(), true, stack.hasFoil());

                poseStack.pushPose();

                renderer.renderModelLists(basemodel, stack, packedLight, packedOverlay, poseStack, foilbuffer);

                poseStack.popPose();
            }
        }


        super.renderByItem(stack, displayContext, poseStack, buffer, packedLight, packedOverlay);
    }
}
