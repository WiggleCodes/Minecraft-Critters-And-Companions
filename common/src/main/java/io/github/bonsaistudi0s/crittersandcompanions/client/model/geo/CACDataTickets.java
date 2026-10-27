package io.github.bonsaistudi0s.crittersandcompanions.client.model.geo;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import com.geckolib.constant.dataticket.DataTicket;

/**
 * Entity state captured for rendering. GeckoLib models and layers only see the render state, not the entity itself.
 */
public final class CACDataTickets {

    public static final DataTicket<Boolean> BABY = create("baby", Boolean.class);
    public static final DataTicket<Integer> VARIANT = create("variant", Integer.class);
    public static final DataTicket<Boolean> SLEEPING = create("sleeping", Boolean.class);
    public static final DataTicket<Boolean> GARY = create("gary", Boolean.class);
    public static final DataTicket<Boolean> IN_WATER = create("in_water", Boolean.class);
    public static final DataTicket<Boolean> FLOATING = create("floating", Boolean.class);
    public static final DataTicket<Boolean> EATING = create("eating", Boolean.class);
    public static final DataTicket<Boolean> CLIMBING = create("climbing", Boolean.class);
    public static final DataTicket<Float> LOOK_BLEND_WEIGHT = create("look_blend_weight", Float.class);
    public static final DataTicket<String> CURRENT_ANIMATION = create("current_animation", String.class);
    public static final DataTicket<Identifier> ARMOR_TEXTURE = create("armor_texture", Identifier.class);
    public static final DataTicket<DyeColor> COLLAR_COLOR = create("collar_color", DyeColor.class);
    public static final DataTicket<Boolean> HAS_CHEST = create("has_chest", Boolean.class);

    private static <D> DataTicket<D> create(String name, Class<? extends D> type) {
        return DataTicket.create(CrittersAndCompanions.MODID + ":" + name, type);
    }

    private CACDataTickets() {
    }
}
