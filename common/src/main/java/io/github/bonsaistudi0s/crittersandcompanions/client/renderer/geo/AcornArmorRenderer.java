package io.github.bonsaistudi0s.crittersandcompanions.client.renderer.geo;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.common.item.AcornHatItem;
import com.geckolib.model.DefaultedItemGeoModel;
import com.geckolib.renderer.GeoArmorRenderer;

public class AcornArmorRenderer extends GeoArmorRenderer<AcornHatItem, HumanoidRenderState> {
    public AcornArmorRenderer() {
        super(new DefaultedItemGeoModel<>(CrittersAndCompanions.createId("armor/acorn")));
    }
}
