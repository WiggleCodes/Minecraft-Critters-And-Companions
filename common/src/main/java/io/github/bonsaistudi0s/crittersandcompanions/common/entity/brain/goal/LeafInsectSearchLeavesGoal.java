package io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.goal;

import io.github.bonsaistudi0s.crittersandcompanions.common.entity.LeafInsectEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.EnumSet;
import java.util.List;

public class LeafInsectSearchLeavesGoal extends Goal {

    private final LeafInsectEntity mob;

    public LeafInsectSearchLeavesGoal(LeafInsectEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    private List<ItemEntity> findWantedItems() {
        var level = getServerLevel(mob);
        return level.getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), (itemEntity -> mob.wantsToPickUp(level, itemEntity.getItem())));
    }

    @Override
    public boolean canUse() {
        if (!mob.getMainHandItem().isEmpty()) {
            return false;
        } else {
            return !findWantedItems().isEmpty();
        }
    }

    @Override
    public void tick() {
        var itemsInRadius = findWantedItems();
        var handStack = mob.getMainHandItem();
        if (handStack.isEmpty() && !itemsInRadius.isEmpty()) {
            var path = mob.getNavigation().createPath(itemsInRadius.get(0), 0);
            mob.getNavigation().moveTo(path, 1.0D);
        }
    }

    @Override
    public void start() {
        var itemsInRadius = findWantedItems();
        if (!itemsInRadius.isEmpty()) {
            var path = mob.getNavigation().createPath(itemsInRadius.get(0), 0);
            mob.getNavigation().moveTo(path, 1.0D);
        }
    }
}
