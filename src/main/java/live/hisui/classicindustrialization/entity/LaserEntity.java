package live.hisui.classicindustrialization.entity;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import live.hisui.classicindustrialization.ClassicIndustrialization;
import live.hisui.classicindustrialization.ModEntities;
import live.hisui.classicindustrialization.item.LaserMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

import java.util.Optional;
import java.util.UUID;

public class LaserEntity extends Entity {

    private UUID owner;
    private LaserMode mode;
    private int blocksLeft;
    private int age;

    private static final Object2IntArrayMap<LaserMode> MODES = new Object2IntArrayMap<>();
    static {
        MODES.put(LaserMode.NORMAL,5);
        MODES.put(LaserMode.LONG_RANGE,10);
        MODES.put(LaserMode.HORIZONTAL,10);
        MODES.put(LaserMode.EXPLOSIVE,1);
        MODES.put(LaserMode.SCATTER,2);
        MODES.put(LaserMode.IGNITE,1);
        MODES.put(LaserMode.HAMMER,1);
        MODES.put(LaserMode.PRECISION,1);
    }

    public LaserEntity(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
        this.blocksLeft = MODES.getInt(this.mode);
    }

    public LaserEntity(Level level, double x, double y, double z, LaserMode mode) {
        this(ModEntities.LASER.get(), level);
        this.setPos(x, y, z);
        this.mode = mode;
        this.blocksLeft = MODES.getInt(this.mode);
    }
    public LaserEntity(Level level, Player owner, double x, double y, double z, float xrot, float yrot, LaserMode mode) {
        this(level, x, y, z, mode);
        this.setOwner(owner);
        this.setPos(x, y, z);
        this.setRot(yrot,xrot);
        this.xRotO = xrot;
        this.yRotO = yrot;
        this.mode = mode;
        Vec3 velocity = Vec3.directionFromRotation(xrot, yrot).scale(1.2f);
        this.setDeltaMovement(velocity);
        this.hurtMarked = true;
        this.hasImpulse = true;
        this.blocksLeft = MODES.getInt(this.mode);
    }
    public LaserEntity(Level level, LivingEntity shooter) {
        this(level, shooter.getX(), shooter.getY() - 0.1f, shooter.getZ(), live.hisui.classicindustrialization.item.LaserMode.NORMAL);
    }

    public boolean canHitEntity(Entity entity){
        if(entity instanceof LaserEntity) return false;
        if(entity instanceof ItemEntity) return false;
        if(entity.getUUID().equals(owner)) return false;
        return true;
    }

    public void setOwner(Player owner){
        this.owner = owner.getUUID();
    }

    @Override
    public void tick() {
        super.tick();
        age++;

        Vec3 vec3 = this.getDeltaMovement();
        if(!this.level().isClientSide()) {
            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS) {
                this.onHit(hitresult);
            }
        }
        if(this.age > (this.mode == LaserMode.LONG_RANGE ? 60 : (this.mode == LaserMode.PRECISION ? 15 : 30))) {
            this.kill();
            return;
        }
        this.checkInsideBlocks();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        float f;
        if (this.isInWater()) {
            for (int i = 0; i < 4; i++) {
                float f1 = 0.25F;
                this.level().addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25, d1 - vec3.y * 0.25, d2 - vec3.z * 0.25, vec3.x, vec3.y, vec3.z);
            }
        }

        this.setDeltaMovement(vec3);
        this.setPos(d0, d1, d2);
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d3 = vec3.horizontalDistance();
//            this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI));
//            this.setXRot((float)(Mth.atan2(vec3.y, d3) * 180.0F / (float)Math.PI));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }
    }

    public void onHitEntity(Entity entity, LaserMode mode){
        if(mode.equals(LaserMode.IGNITE)) { //ignite
            entity.igniteForSeconds(4);
        } else if(mode.equals(LaserMode.EXPLOSIVE)) { //explode
            entity.level().explode(this, entity.getX(), entity.getY(), entity.getZ(), 4.0f, false, Level.ExplosionInteraction.TNT);
        }  //everything else
        entity.hurt(entity.level().damageSources().generic(), 6);

    }
    public void onHitBlock(Level level, BlockPos pos, LaserMode mode){
        if(mode.equals(LaserMode.IGNITE)) { //ignite
            if(level instanceof ServerLevel) {
                RecipeManager recipeManager = level.getServer().getRecipeManager();
                Block type = level.getBlockState(pos).getBlock();
                BlockItem item = (BlockItem) type.asItem();
                if(item != Items.AIR) {
                    ItemStack ingredient = new ItemStack(item);
                    RecipeHolder<SmeltingRecipe> recipe = Optional.of(recipeManager.getRecipeFor(RecipeType.SMELTING,
                            new SingleRecipeInput(ingredient), level)).get().orElse(null);
                    if (recipe != null) {
                        ItemStack result = recipe.value().getResultItem(level.registryAccess());
                        if (result.getItem() instanceof BlockItem bi) {
                            Block block = bi.getBlock();
                            level.setBlock(pos, block.defaultBlockState(), Block.UPDATE_ALL);
                        } else {
                            if(BaseFireBlock.canBePlacedAt(level, pos.above(), Direction.DOWN)) {
                                level.setBlock(pos.above(), BaseFireBlock.getState(level, pos.above()), Block.UPDATE_ALL);
                            } else if(level.getBlockState(pos).isFlammable(level, pos, Direction.DOWN)) {
                                level.setBlock(pos, BaseFireBlock.getState(level, pos), Block.UPDATE_ALL);
                            }
                        }
                    } else {
                        if(BaseFireBlock.canBePlacedAt(level, pos.above(), Direction.DOWN)) {
                            level.setBlock(pos.above(), BaseFireBlock.getState(level, pos.above()), Block.UPDATE_ALL);
                        } else if(level.getBlockState(pos).isFlammable(level, pos, Direction.DOWN)) {
                            level.setBlock(pos, BaseFireBlock.getState(level, pos), Block.UPDATE_ALL);
                        }
                    }
                }
            }
            this.kill();
        } else if(mode.equals(LaserMode.EXPLOSIVE)) { //explode
            level().explode(this, pos.getX(), pos.getY(), pos.getZ(), 3.0f, false, Level.ExplosionInteraction.TNT);
            this.kill();
        } else { //everything else
            FakePlayer fake = FakePlayerFactory.getMinecraft((ServerLevel)level);
            fake.setGameMode(GameType.SURVIVAL);
            fake.getInventory().setItem(fake.getInventory().selected, Items.NETHERITE_PICKAXE.getDefaultInstance());
            BlockState stateToBreak = level.getBlockState(pos);
            boolean canBreak = stateToBreak.canHarvestBlock(level, pos, fake);
            boolean canDestroy = stateToBreak.getDestroySpeed(level, pos) >= 0.0f;
            if(canDestroy) {
                level.destroyBlock(pos, canBreak);
                this.blocksLeft--;
            } else {
                this.kill();
            }
        }
    }

    protected void onHit(HitResult result) {
        HitResult.Type hitresult$type = result.getType();
        if (hitresult$type == HitResult.Type.ENTITY) {
            EntityHitResult entityhitresult = (EntityHitResult)result;
            Entity entity = entityhitresult.getEntity();

            this.onHitEntity(entity, this.mode);
            this.kill();
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, result.getLocation(), GameEvent.Context.of(this, null));
        } else if (hitresult$type == HitResult.Type.BLOCK) {
            BlockHitResult blockhitresult = (BlockHitResult)result;
            BlockPos blockpos = blockhitresult.getBlockPos();

            this.onHitBlock(this.level(), blockpos, this.mode);
            if(this.blocksLeft <= 0) { this.kill(); }
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, blockpos, GameEvent.Context.of(this, this.level().getBlockState(blockpos)));
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        this.owner = compound.getUUID("owner");
        this.mode = StringRepresentable.fromEnum(LaserMode::values).byName(compound.getString("mode"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putUUID("owner",this.owner);
        compound.putString("mode",this.mode.getSerializedName());
    }
}
