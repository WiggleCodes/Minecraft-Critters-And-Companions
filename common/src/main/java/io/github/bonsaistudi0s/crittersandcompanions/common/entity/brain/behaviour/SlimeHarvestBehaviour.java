package io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.behaviour;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class SlimeHarvestBehaviour implements Behaviour {

    public static final int HARVEST_COOLDOWN = 20 * 60 * 5;
    private static final String TAG_COOLDOWN = "SlimeHarvestCooldown";

    private int cooldown = 0;

    public SlimeHarvestBehaviour() {
    }

    @Override
    public void serverTick() {
        if (cooldown > 0) {
            cooldown--;
        }
    }

    @Override
    public void save(ValueOutput output) {
        output.putInt(TAG_COOLDOWN, cooldown);
    }

    @Override
    public void read(ValueInput input) {
        cooldown = input.getIntOr(TAG_COOLDOWN, 0);
    }

    public boolean isReady() {
        return cooldown <= 0;
    }

    public void startCooldown() {
        this.cooldown = HARVEST_COOLDOWN;
    }
}
