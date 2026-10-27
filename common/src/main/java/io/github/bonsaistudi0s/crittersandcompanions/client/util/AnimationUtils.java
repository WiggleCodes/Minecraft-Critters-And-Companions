package io.github.bonsaistudi0s.crittersandcompanions.client.util;

import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.state.BoneSnapshot;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.constant.DataTickets;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AnimationUtils {

    /**
     * Head pitch in degrees, using the same sign convention as GeckoLib 4's {@code EntityModelData#headPitch}.
     */
    public static float getHeadPitch(GeoRenderState renderState) {
        return -renderState.getOrDefaultGeckolibData(DataTickets.ENTITY_PITCH, 0f);
    }

    /**
     * Head yaw relative to the body in degrees, using the same sign convention as GeckoLib 4's {@code EntityModelData#netHeadYaw}.
     */
    public static float getNetHeadYaw(GeoRenderState renderState) {
        return -renderState.getOrDefaultGeckolibData(DataTickets.ENTITY_YAW, 0f);
    }

    // Snapshot rotations are offsets from the bone's base rotation, these work with the full rotation instead.

    public static float getRotX(BoneSnapshot snapshot) {
        return snapshot.getBone().baseRotX() + snapshot.getRotX();
    }

    public static float getRotY(BoneSnapshot snapshot) {
        return snapshot.getBone().baseRotY() + snapshot.getRotY();
    }

    public static float getRotZ(BoneSnapshot snapshot) {
        return snapshot.getBone().baseRotZ() + snapshot.getRotZ();
    }

    public static void setRotX(BoneSnapshot snapshot, float rotation) {
        snapshot.setRotX(rotation - snapshot.getBone().baseRotX());
    }

    public static void setRotY(BoneSnapshot snapshot, float rotation) {
        snapshot.setRotY(rotation - snapshot.getBone().baseRotY());
    }

    public static void setRotZ(BoneSnapshot snapshot, float rotation) {
        snapshot.setRotZ(rotation - snapshot.getBone().baseRotZ());
    }

    public static void setHidden(BoneSnapshot snapshot, boolean hidden) {
        snapshot.skipRender(hidden).skipChildrenRender(hidden);
    }

    public static Vector3f calculateInverseWorldRotation(BoneSnapshots snapshots, GeoBone fixBone) {
        List<GeoBone> parents = new ArrayList<>();
        var current = fixBone.parent();
        while (current != null) {
            parents.add(current);
            current = current.parent();
        }

        Collections.reverse(parents);

        var worldRot = new Quaternionf();
        for (var parent : parents) {
            var snapshot = snapshots.get(parent);
            var localRot = new Quaternionf()
                    .rotateX(getRotX(snapshot))
                    .rotateY(getRotY(snapshot))
                    .rotateZ(getRotZ(snapshot));

            worldRot.mul(localRot);
        }

        worldRot.invert();

        var invertedEulers = new Vector3f();
        worldRot.getEulerAnglesXYZ(invertedEulers);
        return invertedEulers;
    }

    public static @Nullable AnimationController<?> getAnimationController(GeoAnimatable animatable, long instanceId, String controllerName) {
        return animatable.getAnimatableInstanceCache().getManagerForId(instanceId).getAnimationControllers().get(controllerName);
    }

    public static @Nullable String getCurrentAnimationName(GeoAnimatable animatable, long instanceId) {
        return getCurrentAnimationName(animatable, instanceId, "controller");
    }

    public static @Nullable String getCurrentAnimationName(GeoAnimatable animatable, long instanceId, String controllerName) {
        var controller = getAnimationController(animatable, instanceId, controllerName);
        if (controller == null) {
            return null;
        }

        var currentAnimation = controller.getCurrentAnimationPoint();
        if (currentAnimation == null) {
            return null;
        }

        return currentAnimation.animation().name();
    }
}
