package live.hisui.classicindustrialization.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.entity.LaserEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import org.jetbrains.annotations.NotNull;

public class LaserEntityRenderer extends EntityRenderer<LaserEntity> {
    public static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(ClassicIndustrialization.MODID, "textures/entity/laser/laser_red.png");
    private static final RenderType RENDER_TYPE = RenderType.entitySolid(TEXTURE_LOCATION);
    private final ModelPart main;
    public LaserEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        ModelPart modelpart = context.bakeLayer(LaserEntityModel.LAYER_LOCATION);
        this.main = modelpart.getChild("bb_main");
    }


    @Override
    public @NotNull ResourceLocation getTextureLocation(LaserEntity entity) {
        return TEXTURE_LOCATION;
    }

    @Override
    public void render(LaserEntity p_entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YN.rotationDegrees(p_entity.getYRot()/*Mth.lerp(partialTick, p_entity.yRotO, p_entity.getYRot()) - 0.0F*/));
        poseStack.mulPose(Axis.XP.rotationDegrees(p_entity.getXRot()/*Mth.lerp(partialTick, p_entity.xRotO, p_entity.getXRot()) - 0.0F*/));

        VertexConsumer vertexconsumer = bufferSource.getBuffer(RENDER_TYPE);
        int i = 15728850;
        this.main.render(poseStack, vertexconsumer, i, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(p_entity, entityYaw, partialTick, poseStack, bufferSource, i);
    }

    @Override
    public boolean shouldRender(LaserEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }
}
