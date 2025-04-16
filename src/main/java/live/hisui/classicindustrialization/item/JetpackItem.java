package live.hisui.classicindustrialization.item;

import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.component.ModDataComponents;
import live.hisui.classicindustrialization.util.client.InputHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class JetpackItem  extends EnergyStoringItem implements Equipable, IToggleClick, INotArmorButRendersLikeArmor {
    public static final int ENERGY_PER_TICK = 5;
    public JetpackItem(int maxCapacity, int transferRate) {
        super(maxCapacity, transferRate);
    }

    public JetpackItem(Properties properties, int maxCapacity, int transferRate) {
        super(properties, maxCapacity, transferRate);
    }


    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(entity instanceof Player player) {
            ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
            if (chest.equals(stack)) {
                if (this.getEnergyStored(stack) > 0 && !player.onGround()) {
                    final float HORIZONTAL_SCALAR = 0.06f;
                    final float VERTICAL_SCALAR = 0.1f;
                    final float HOVER_SCALAR = 0.3f;
                    boolean hover = Optional.ofNullable(stack.get(ModDataComponents.ITEM_ENABLED)).orElse(false);
                    Vector3f dir = InputHandler.getMotion(player);
                    if(dir.y() < 0.001 && !hover) return;
                    this.extractEnergy(stack,ENERGY_PER_TICK,false);
                    Vec3 deltaMotion = player.getDeltaMovement();
                    Vec3 deltaMotionNoY = new Vec3(deltaMotion.x, 0, deltaMotion.z);
                    // TODO: make motion less shit
                    if(hover) {
                        player.resetFallDistance();
                        Vec3 absoluteHorizontalMotion = new Vec3(dir.x, 0, dir.z).normalize();
                        Vec3 relativeHorizontalMotion = getInputVector(absoluteHorizontalMotion, 1f, player.getYRot());
                        Vec3 verticalMotion = new Vec3(0, dir.y, 0).normalize();

                        Vec3 intendedMotion = relativeHorizontalMotion.scale(HORIZONTAL_SCALAR).add(verticalMotion.scale(VERTICAL_SCALAR/HOVER_SCALAR));
                        player.setDeltaMovement(deltaMotionNoY.add(intendedMotion));
                    } else {
                        player.resetFallDistance();
                        Vec3 absoluteHorizontalMotion = new Vec3(dir.x, 0, dir.z).normalize();
                        Vec3 relativeHorizontalMotion = getInputVector(absoluteHorizontalMotion, 1f, player.getYRot());
                        Vec3 verticalMotion = new Vec3(0, dir.y, 0).normalize();

                        Vec3 intendedMotion = relativeHorizontalMotion.scale(HORIZONTAL_SCALAR).add(verticalMotion.scale(VERTICAL_SCALAR));
                        player.setDeltaMovement(deltaMotion.add(intendedMotion));
                    }
                }
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }
    @Override
    public boolean isFoil(ItemStack stack) {
        if(Optional.ofNullable(stack.get(ModDataComponents.ITEM_ENABLED)).orElse(false)){
            return true;
        }
        return super.isFoil(stack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return this.swapWithEquipmentSlot(this, level, player, hand);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nonnull TooltipContext context, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        tooltip.add(Optional.ofNullable(stack.get(ModDataComponents.ITEM_ENABLED)).orElse(false)
                ? Component.literal("Hover Mode: Enabled").withStyle(ChatFormatting.GREEN)
                : Component.literal("Hover Mode: Disabled").withStyle(ChatFormatting.RED));
        super.appendHoverText(stack, context, tooltip, flagIn);
    }

    private static Vec3 getInputVector(Vec3 relative, float motionScaler, float facing) {
        double d0 = relative.lengthSqr();
        if (d0 < 1.0E-7) {
            return Vec3.ZERO;
        } else {
            Vec3 vec3 = (d0 > 1.0 ? relative.normalize() : relative).scale((double)motionScaler);
            float f = Mth.sin(facing * (float) (Math.PI / 180.0));
            float f1 = Mth.cos(facing * (float) (Math.PI / 180.0));
            return new Vec3(vec3.x * (double)f1 - vec3.z * (double)f, vec3.y, vec3.z * (double)f1 + vec3.x * (double)f);
        }
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.CHEST;
    }

    @Override
    public Holder<SoundEvent> getEquipSound() {
        return Equipable.super.getEquipSound();
    }

    @Override
    public InteractionResultHolder<ItemStack> swapWithEquipmentSlot(Item item, Level level, Player player, InteractionHand hand) {
        return Equipable.super.swapWithEquipmentSlot(item, level, player, hand);
    }

    @Override
    public void onToggle(ItemStack stack, Player player) {
        player.displayClientMessage(Component.literal("Hover toggled"), false);
        IToggleClick.super.onToggle(stack, player);
    }
}
