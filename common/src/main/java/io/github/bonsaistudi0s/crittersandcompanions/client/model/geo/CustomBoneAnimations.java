package io.github.bonsaistudi0s.crittersandcompanions.client.model.geo;

import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;

/**
 * Implemented by models that adjust bones procedurally after animations have been applied, such as head tracking.
 */
public interface CustomBoneAnimations {

    void adjustModelBones(GeoRenderState renderState, BoneSnapshots snapshots);
}
