package io.github.bonsaistudi0s.crittersandcompanions.common.entity.animation;

import net.minecraft.world.entity.TamableAnimal;

import org.jetbrains.annotations.Nullable;

import io.github.bonsaistudi0s.crittersandcompanions.common.entity.base.FlyingAnimal;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.BehaviourDriven;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.Behaviours;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour.DancingBehaviour;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.RawAnimation;

public class BugAnimations<T extends GeoAnimatable & BehaviourDriven> implements AnimationController.AnimationStateHandler<T> {

    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("walk");
    private static final RawAnimation FLY_ANIM = RawAnimation.begin().thenLoop("fly");
    private static final RawAnimation SIT_ANIM = RawAnimation.begin().thenLoop("sit");

    private static final int TRANSITION_TICK_TIME = 4;

    @Nullable
    private final DancingBehaviour dancingBehaviour;

    public BugAnimations(Behaviours behaviours) {
        this.dancingBehaviour = behaviours.optional(DancingBehaviour.class).orElse(null);
    }

    public static <T extends GeoAnimatable & BehaviourDriven> AnimationController<T> createController(T animatable) {
        return new AnimationController<>("controller", TRANSITION_TICK_TIME, new BugAnimations<>(animatable.getBehaviours()));
    }

    @Override
    public PlayState handle(AnimationTest<T> state) {
        var custom = getCustomAnimation(state);
        if (custom != null) {
            state.setAnimation(custom);
            return PlayState.CONTINUE;
        }

        state.setAnimation(createAnimation(state));
        return PlayState.CONTINUE;
    }

    protected @Nullable RawAnimation getCustomAnimation(AnimationTest<T> state) {
        return null;
    }

    private RawAnimation createAnimation(AnimationTest<T> state) {
        var controller = state.controller();

        if (state.animatable() instanceof FlyingAnimal animal && animal.isFlying()) {
            controller.setTransitionTicks(TRANSITION_TICK_TIME);
            return FLY_ANIM;
        }

        if (dancingBehaviour != null && dancingBehaviour.isDancing()) {
            controller.setTransitionTicks(TRANSITION_TICK_TIME);
            return RawAnimation.begin().thenLoop(dancingBehaviour.getDanceAnimationName());
        }

        if (state.animatable() instanceof TamableAnimal animal && animal.isInSittingPose()) {
            controller.setTransitionTicks(0);
            return SIT_ANIM;
        }

        if (state.isMoving()) {
            controller.setTransitionTicks(TRANSITION_TICK_TIME);
            return WALK_ANIM;
        }

        if (state.isCurrentAnimation(SIT_ANIM)) {
            controller.setTransitionTicks(0);
        } else {
            controller.setTransitionTicks(TRANSITION_TICK_TIME);
        }

        return IDLE_ANIM;
    }
}
