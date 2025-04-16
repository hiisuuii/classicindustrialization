package live.hisui.classicindustrialization.render;

import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

public class EquipmentModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
    public EquipmentModel(ModelPart root) {
        super(root);
    }
}
