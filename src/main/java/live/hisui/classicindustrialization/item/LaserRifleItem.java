package live.hisui.classicindustrialization.item;

import live.hisui.classicindustrialization.ModItems;
import live.hisui.classicindustrialization.component.ModDataComponents;
import live.hisui.classicindustrialization.entity.LaserEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class LaserRifleItem extends EnergyStoringItem{

    public LaserRifleItem(int maxCapacity, int transferRate) {
        this(new Item.Properties().stacksTo(1).component(ModDataComponents.LASER_MODE_ALT, LaserMode.NORMAL), maxCapacity, transferRate);
    }

    public LaserRifleItem(Properties properties, int maxCapacity, int transferRate) {
        super(properties, maxCapacity, transferRate);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if(oldStack.getItem() instanceof LaserRifleItem oldRifle && newStack.getItem() instanceof LaserRifleItem newRifle) {
            if(!oldStack.get(ModDataComponents.LASER_MODE_ALT).equals(newStack.get(ModDataComponents.LASER_MODE_ALT))) {
                return true;
            }
        }
        return slotChanged || !ItemStack.isSameItem(oldStack, newStack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(!context.getLevel().isClientSide()) {
            ItemStack stack = context.getItemInHand();
            Player player = context.getPlayer();
            Level level = context.getLevel();
            BlockPos pos = context.getClickedPos();
            if (stack.getItem() instanceof LaserRifleItem) {
                if(player.isCrouching()) return InteractionResult.PASS;
                LaserMode mode = stack.get(ModDataComponents.LASER_MODE_ALT);
                if (mode != null && mode.equals(LaserMode.HORIZONTAL)) {
                    double laserHeight = pos.getCenter().y();

                    if (this.getEnergyStored(stack) < mode.getEnergyCost()) {
                        return InteractionResult.PASS;
                    }
                    this.extractEnergy(stack, mode.getEnergyCost(), false);
                    if (player != null) {
                        if (pos.getY() < player.blockPosition().getY() || pos.getY() > player.blockPosition().above(2).getY()) {
                            player.displayClientMessage(Component.literal("Laser rifle aiming angle too steep"), false);
                            player.getCooldowns().addCooldown(this, 5);
                            return InteractionResult.PASS;
                        }
                        Vec3 eyePos = player.getEyePosition();

                        Vec3 laserFinalPos = new Vec3(eyePos.x, laserHeight, eyePos.z);

                        float xrot = 0;
                        float yrot = player.yRotO;


                        LaserEntity laserEntity = new LaserEntity(level, player, laserFinalPos.x, laserFinalPos.y, laserFinalPos.z, xrot, yrot, mode);
                        level.addFreshEntity(laserEntity);
                        return InteractionResult.PASS;
                    }
                }
            }
        }
        return super.useOn(context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);

        LaserMode mode = Optional.ofNullable(stack.get(ModDataComponents.LASER_MODE_ALT)).orElse(LaserMode.NORMAL);
        if (!level.isClientSide()) {
            if (player.isCrouching()) {
                mode = mode.next();
                stack.set(ModDataComponents.LASER_MODE_ALT, mode);
                player.displayClientMessage(Component.literal("Laser mode: %s".formatted(mode.getName())), false);
                return InteractionResultHolder.pass(stack);
            } else {

                // Horizontal Mode
                if(mode.equals(LaserMode.HORIZONTAL)) {
                    return super.use(level, player, usedHand);
                }
                if(this.getEnergyStored(stack) < mode.getEnergyCost()) {
                    return InteractionResultHolder.pass(stack);
                }
                this.extractEnergy(stack, mode.getEnergyCost(), false);
                Vec3 vec = player.getEyePosition().subtract(0, 0.15, 0);

                float xrot = player.xRotO;
                float yrot = player.yRotO;

                if(mode.equals(LaserMode.SCATTER)) {
                    // Start with the player's base direction vector
                    Vec3 forward = player.getLookAngle().normalize();
                    Vec3 globalUp = new Vec3(0, 1, 0);

                    // Handle gimbal lock cases
                    Vec3 right = forward.y > 0.999 || forward.y < -0.999
                            ? new Vec3(1, 0, 0)
                            : forward.cross(globalUp).normalize();

                    Vec3 up = right.cross(forward).normalize();

                    float maxAngle = 24.0f; // Spread angle in degrees
                    int gridSize = 5; // -2 to +2 = 5 positions

                    // Calculate appropriate step sizes
                    float stepSize = 2.0f * maxAngle / (gridSize - 1);

                    for (int k = 0; k < gridSize; k++) {
                        for (int l = 0; l < gridSize; l++) {
                            // Convert from grid position to angle offsets
                            float horizontalAngle = (k - (gridSize - 1) / 2.0f) * stepSize;
                            float verticalAngle = (l - (gridSize - 1) / 2.0f) * stepSize;

                            // Convert angles to radians
                            double hRad = Math.toRadians(horizontalAngle);
                            double vRad = Math.toRadians(verticalAngle);

                            // First rotate around vertical axis (yaw)
                            Vec3 temp = new Vec3(
                                    forward.x * Math.cos(hRad) + right.x * Math.sin(hRad),
                                    forward.y * Math.cos(hRad) + right.y * Math.sin(hRad),
                                    forward.z * Math.cos(hRad) + right.z * Math.sin(hRad)
                            ).normalize();

                            // Then rotate around horizontal axis (pitch)
                            Vec3 spreadDirection = new Vec3(
                                    temp.x * Math.cos(vRad) + up.x * Math.sin(vRad),
                                    temp.y * Math.cos(vRad) + up.y * Math.sin(vRad),
                                    temp.z * Math.cos(vRad) + up.z * Math.sin(vRad)
                            ).normalize();

                            // Convert direction to yaw/pitch for Minecraft entity
                            float newYaw = (float) (Mth.atan2(-spreadDirection.x, spreadDirection.z) * (180.0F / Math.PI));
                            float newPitch = -1 * (float) (Mth.atan2(spreadDirection.y, spreadDirection.horizontalDistance()) * (180.0F / Math.PI));

                            // Create laser entity with calculated direction
                            level.addFreshEntity(new LaserEntity(level, player, vec.x, vec.y, vec.z, newPitch, newYaw, mode));
                        }
                    }
                    return super.use(level, player, usedHand);
                } if(mode.equals(LaserMode.HAMMER)) {
                    // Start with the player's base direction vector
                    Vec3 forward = player.getLookAngle().normalize();
                    Vec3 globalUp = new Vec3(0, 1, 0);

// Handle gimbal lock cases
                    Vec3 right = forward.y > 0.999 || forward.y < -0.999
                            ? new Vec3(1, 0, 0)
                            : forward.cross(globalUp).normalize();

                    Vec3 up = right.cross(forward).normalize();

                    float maxAngle = 12.0f; // Spread angle in degrees
                    int gridSize = 3; // -2 to +2 = 5 positions

// Calculate appropriate step sizes
                    float stepSize = 2.0f * maxAngle / (gridSize - 1);

                    for (int k = 0; k < gridSize; k++) {
                        for (int l = 0; l < gridSize; l++) {
                            // Convert from grid position to angle offsets
                            float horizontalAngle = (k - (gridSize - 1) / 2.0f) * stepSize;
                            float verticalAngle = (l - (gridSize - 1) / 2.0f) * stepSize;

                            // Convert angles to radians
                            double hRad = Math.toRadians(horizontalAngle);
                            double vRad = Math.toRadians(verticalAngle);

                            // First rotate around vertical axis (yaw)
                            Vec3 temp = new Vec3(
                                    forward.x * Math.cos(hRad) + right.x * Math.sin(hRad),
                                    forward.y * Math.cos(hRad) + right.y * Math.sin(hRad),
                                    forward.z * Math.cos(hRad) + right.z * Math.sin(hRad)
                            ).normalize();

                            // Then rotate around horizontal axis (pitch)
                            Vec3 spreadDirection = new Vec3(
                                    temp.x * Math.cos(vRad) + up.x * Math.sin(vRad),
                                    temp.y * Math.cos(vRad) + up.y * Math.sin(vRad),
                                    temp.z * Math.cos(vRad) + up.z * Math.sin(vRad)
                            ).normalize();

                            // Convert direction to yaw/pitch for Minecraft entity
                            float newYaw = (float) (Mth.atan2(-spreadDirection.x, spreadDirection.z) * (180.0F / Math.PI));
                            float newPitch = -1 * (float) (Mth.atan2(spreadDirection.y, spreadDirection.horizontalDistance()) * (180.0F / Math.PI));

                            // Create laser entity with calculated direction
                            level.addFreshEntity(new LaserEntity(level, player, vec.x, vec.y, vec.z, newPitch, newYaw, mode));

                        }
                    }
                    return super.use(level, player, usedHand);
                }
                else {
                    LaserEntity laserEntity = new LaserEntity(level, player, vec.x, vec.y, vec.z, xrot, yrot, mode);
                    level.addFreshEntity(laserEntity);
                }

            }
        }
        return super.use(level, player, usedHand);
    }
}
