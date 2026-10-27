package io.github.bonsaistudi0s.crittersandcompanions.client.model.geo;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.AgeableMob;

import org.jspecify.annotations.Nullable;

import java.util.function.Function;

import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.BehaviourDriven;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.model.DefaultedEntityGeoModel;
import com.geckolib.renderer.base.GeoRenderState;

public class AgingGeoModel<T extends AgeableMob & GeoAnimatable & BehaviourDriven> extends DefaultedEntityGeoModel<T> {

    public static Identifier[] createResources(Identifier id, Function<Identifier, Identifier> factory) {
        return new Identifier[]{
                factory.apply(id),
                factory.apply(id.withPrefix("baby_"))
        };
    }

    protected static boolean isBaby(GeoRenderState renderState) {
        return renderState.getOrDefaultGeckolibData(CACDataTickets.BABY, false);
    }

    private final Identifier[] textures;
    private final Identifier[] animations;
    private final Identifier[] models;

    public AgingGeoModel(Identifier id) {
        super(id);
        this.textures = createResources(id, this::buildFormattedTexturePath);
        this.animations = createResources(id, this::buildFormattedAnimationPath);
        this.models = createResources(id, this::buildFormattedModelPath);
    }

    @Override
    public void addAdditionalStateData(T animatable, @Nullable Object relatedObject, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, relatedObject, renderState);
        renderState.addGeckolibData(CACDataTickets.BABY, animatable.isBaby());
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return isBaby(renderState) ? textures[1] : textures[0];
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return isBaby(renderState) ? models[1] : models[0];
    }

    @Override
    public Identifier getAnimationResource(T entity) {
        return entity.isBaby() ? animations[1] : animations[0];
    }
}
