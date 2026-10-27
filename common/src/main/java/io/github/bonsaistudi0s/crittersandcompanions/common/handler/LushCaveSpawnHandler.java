package io.github.bonsaistudi0s.crittersandcompanions.common.handler;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.phys.AABB;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.github.bonsaistudi0s.crittersandcompanions.common.registry.CACTags;

public class LushCaveSpawnHandler {

    private static final int SPAWN_INTERVAL = 40;
    private static final int MAX_ENTITIES_PER_PLAYER = 8;
    private static final int SPAWN_RADIUS = 24;
    private static final int MIN_SPAWN_DISTANCE = 6;
    private static final int MAX_POSITION_ATTEMPTS = 12;
    private static final int GROUP_MEMBER_RADIUS = 2;

    private static int tickCount = 0;
    private static final List<Weighted<MobSpawnSettings.SpawnerData>> spawnerData = new ArrayList<>();
    private static final Set<EntityType<?>> entityTypeSet = new HashSet<>();

    public static void addSpawnerData(MobSpawnSettings.SpawnerData data, int weight) {
        spawnerData.add(new Weighted<>(data, weight));
        entityTypeSet.add(data.type());
    }

    public static void tick(MinecraftServer server) {
        if (++tickCount % SPAWN_INTERVAL != 0 || spawnerData.isEmpty()) {
            return;
        }

        for (var level : server.getAllLevels()) {
            for (var player : level.players()) {
                trySpawnNearPlayer(level, player);
            }
        }
    }

    private static void trySpawnNearPlayer(ServerLevel level, ServerPlayer player) {
        var playerPos = player.blockPosition();

        if (!isUndergroundLushCave(level, playerPos)) {
            return;
        }

        var searchBox = AABB.ofSize(player.position(), SPAWN_RADIUS * 2, SPAWN_RADIUS * 2, SPAWN_RADIUS * 2);
        var existingCount = level.getEntitiesOfClass(Mob.class, searchBox, entity -> entityTypeSet.contains(entity.getType())).size();

        if (existingCount >= MAX_ENTITIES_PER_PLAYER) {
            return;
        }

        var random = level.getRandom();

        var selected = WeightedRandom.getRandomItem(random, spawnerData, Weighted::weight).map(Weighted::value).orElse(null);
        if (selected == null) {
            return;
        }

        var entityType = selected.type();
        var anchorPos = findAnchorPosition(level, playerPos, entityType);
        if (anchorPos == null) {
            return;
        }

        var groupSize = selected.count().sample(random);
        SpawnGroupData groupData = null;
        for (var i = 0; i < groupSize; i++) {
            var spawnPos = (i == 0) ? anchorPos : findGroupMemberPosition(level, anchorPos, entityType);
            if (spawnPos == null) {
                continue;
            }

            groupData = spawnEntity(level, selected.type(), spawnPos, random, groupData);
        }
    }

    private static BlockPos findGround(ServerLevel level, BlockPos candidate, EntityType<?> entityType) {
        if (SpawnPlacements.getPlacementType(entityType).equals(SpawnPlacementTypes.NO_RESTRICTIONS)) {
            return candidate;
        }

        while (candidate.getY() > level.getMinY() && level.getBlockState(candidate).getCollisionShape(level, candidate).isEmpty() && level.getFluidState(candidate).isEmpty()) {
            candidate = candidate.below();
        }

        if (level.getFluidState(candidate).isEmpty()) {
            candidate = candidate.above();
        }

        return candidate;
    }

    private static BlockPos findAnchorPosition(ServerLevel level, BlockPos playerPos, EntityType<?> entityType) {
        var random = level.getRandom();

        for (var i = 0; i < MAX_POSITION_ATTEMPTS; i++) {
            var dx = random.nextInt(SPAWN_RADIUS * 2 + 1) - SPAWN_RADIUS;
            var dy = random.nextInt(SPAWN_RADIUS * 2 + 1) - SPAWN_RADIUS;
            var dz = random.nextInt(SPAWN_RADIUS * 2 + 1) - SPAWN_RADIUS;
            var candidate = playerPos.offset(dx, dy, dz);

            if (candidate.distSqr(playerPos) < (long) MIN_SPAWN_DISTANCE * MIN_SPAWN_DISTANCE) {
                continue;
            }

            candidate = findGround(level, candidate, entityType);

            if (!isValidSpawnPosition(level, candidate, entityType)) {
                continue;
            }

            return candidate;
        }

        return null;
    }

    private static BlockPos findGroupMemberPosition(ServerLevel level, BlockPos anchor, EntityType<?> entityType) {
        var random = level.getRandom();

        for (var i = 0; i < MAX_POSITION_ATTEMPTS; i++) {
            var dx = random.nextInt(GROUP_MEMBER_RADIUS * 2 + 1) - GROUP_MEMBER_RADIUS;
            var dy = random.nextInt(GROUP_MEMBER_RADIUS * 2 + 1) - GROUP_MEMBER_RADIUS;
            var dz = random.nextInt(GROUP_MEMBER_RADIUS * 2 + 1) - GROUP_MEMBER_RADIUS;
            var candidate = anchor.offset(dx, dy, dz);

            candidate = findGround(level, candidate, entityType);

            if (!isValidSpawnPosition(level, candidate, entityType)) {
                continue;
            }

            return candidate;
        }

        return null;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean isValidSpawnPosition(ServerLevel level, BlockPos pos, EntityType<?> entityType) {
        if (!isUndergroundLushCave(level, pos)) {
            return false;
        }

        return SpawnPlacements.isSpawnPositionOk(entityType, level, pos) && SpawnPlacements.checkSpawnRules((EntityType<?>) entityType, level, EntitySpawnReason.NATURAL, pos, level.getRandom());
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean isUndergroundLushCave(ServerLevel level, BlockPos pos) {
        return level.getBiome(pos).is(CACTags.IS_LUSH) && level.getBrightness(LightLayer.SKY, pos) == 0;
    }

    @Nullable
    private static SpawnGroupData spawnEntity(ServerLevel level, EntityType<?> type, BlockPos pos, RandomSource random, @Nullable SpawnGroupData groupData) {
        if (!(type.create(level, EntitySpawnReason.NATURAL) instanceof Mob mob)) {
            return groupData;
        }

        mob.snapTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, random.nextFloat() * 360f, 0f);

        if (!level.isUnobstructed(mob)) {
            mob.discard();
            return groupData;
        }

        groupData = mob.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), EntitySpawnReason.NATURAL, groupData);
        level.addFreshEntityWithPassengers(mob);
        return groupData;
    }
}
