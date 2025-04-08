package live.hisui.classicindustrialization.util.client;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.Map;
import java.util.WeakHashMap;

public class InputHandler {
    private static final Map<Player, Vector3f> MOVEMENT_VECTORS = new WeakHashMap<>();

    public static final Vector3f ZERO = new Vector3f(0,0,0);
    public static Vector3f getMotion(Player player){
        return MOVEMENT_VECTORS.getOrDefault(player, ZERO);
    }

    public static void update(Player player, Vector3f vec3){
        MOVEMENT_VECTORS.put(player,vec3);
    }

    public static void remove(Player player){
        MOVEMENT_VECTORS.remove(player);
    }

    public static void clear(){
        MOVEMENT_VECTORS.clear();
    }
}
