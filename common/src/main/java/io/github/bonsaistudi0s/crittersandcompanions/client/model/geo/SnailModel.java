package io.github.bonsaistudi0s.crittersandcompanions.client.model.geo;

import net.minecraft.resources.Identifier;

import org.jspecify.annotations.Nullable;

import io.github.bonsaistudi0s.crittersandcompanions.CrittersAndCompanions;
import io.github.bonsaistudi0s.crittersandcompanions.client.util.AnimationUtils;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.SnailEntity;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;

public class SnailModel extends AgedVariantGeoModel<SnailEntity> implements CustomBoneAnimations {

    public SnailModel() {
        super(CrittersAndCompanions.createId("snail"));
    }

    @Override
    public void addAdditionalStateData(SnailEntity animatable, @Nullable Object relatedObject, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, relatedObject, renderState);
        renderState.addGeckolibData(CACDataTickets.GARY, animatable.isGaryVariant());
        renderState.addGeckolibData(CACDataTickets.CLIMBING, animatable.onClimbable() && !animatable.onGround());
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        if (renderState.getOrDefaultGeckolibData(CACDataTickets.GARY, false)) {
            if (isBaby(renderState)) {
                return CrittersAndCompanions.createId("textures/entity/baby_snail_gary.png");
            } else {
                return CrittersAndCompanions.createId("textures/entity/snail_gary.png");
            }
        }

        return super.getTextureResource(renderState);
    }

    @Override
    public void adjustModelBones(GeoRenderState renderState, BoneSnapshots snapshots) {
        snapshots.ifPresent("main", mainBone -> {
            if (renderState.getOrDefaultGeckolibData(CACDataTickets.CLIMBING, false)) {
                AnimationUtils.setRotX(mainBone, 90.0F * ((float) Math.PI / 180.0F));
                mainBone.setTranslateY(isBaby(renderState) ? 4.0F : 6.0F);
            } else {
                AnimationUtils.setRotX(mainBone, 0F);
                mainBone.setTranslateY(0F);
            }
        });
    }
}
