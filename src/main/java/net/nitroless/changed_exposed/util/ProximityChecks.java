package net.nitroless.changed_exposed.util;

import net.ltxprogrammer.changed.init.ChangedTags;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.nitroless.changed_exposed.ChangedExposedConfig;
import oshi.util.tuples.Pair;

public class ProximityChecks {
    // wow, what a disgusting set of lines of code that maybe works maybe doesnt
    // this started as one method returning a pair (lightAmount, heavyAmount)
    // which would have worked, but the way i brought over the original code did not
    // and then intellij suggested a very simple solution which is awesome
    // (as far as im aware, auto complete and quick fix doesnt use ai)
    // anyways, the major difference is that instead of iterating through every nearby entity
    // and adding values for every condition passing entity
    // we just collect all nearby entities, filter them out and count them
    // and then only do one addition
    // the performance impact is probably insignificant either way tho
    // maybe if the bounding box was massive it would make a difference lmao
    // also im writing all this out because 1. im bored 2. im procrastinating 3. ill remember wtf is going on
    public static int proximityLightExposureAmount(Player player) {
        return (int)player.level().getEntitiesOfClass(LivingEntity.class, new AABB(player.blockPosition()).inflate(ChangedExposedConfig.PALE_EFFECTIVITY_RANGE.get())).stream().filter(LivingEntity -> LivingEntity.getType().is(ChangedTags.EntityTypes.PALE_SMALL_EXPOSURE)).count();
    }

    public static int proximityHeavyExposureAmount(Player player) {
        return (int)player.level().getEntitiesOfClass(LivingEntity.class, new AABB(player.blockPosition()).inflate(ChangedExposedConfig.PALE_EFFECTIVITY_RANGE.get())).stream().filter(LivingEntity -> LivingEntity.getType().is(ChangedTags.EntityTypes.PALE_LARGE_EXPOSURE)).count();
    }
}
