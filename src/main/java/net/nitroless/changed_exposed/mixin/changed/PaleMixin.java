package net.nitroless.changed_exposed.mixin.changed;

import net.ltxprogrammer.changed.init.ChangedDamageSources;
import net.ltxprogrammer.changed.init.ChangedGameRules;
import net.ltxprogrammer.changed.init.ChangedTags;
import net.ltxprogrammer.changed.process.Pale;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.network.chat.Component;
import net.nitroless.changed_exposed.ChangedExposedConfig;
import net.nitroless.changed_exposed.util.EquipmentChecks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(Pale.class)

public abstract class PaleMixin {
    @Shadow
    public static void setPaleExposure(Player player, int level) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Inject(method = "tickPaleExposure", at = @At("HEAD"), cancellable = true, remap = false)
    private static void tickPaleExposureMixin(Player player, CallbackInfo ci) {
        // cancel early under specific conditions (taken directly from source)
        // i COULD probably target the logic closer but replacing the entire method is just much easier
        // shouldn't cause an issues
        if (Pale.isCured(player)) {
            // JUST making super sure, although setting a value to itself every tick is a terrible idea
            setPaleExposure(player, -1);
            ci.cancel();
        }
        if (!player.level().getGameRules().getBoolean(ChangedGameRules.RULE_DO_PALE))
            ci.cancel();
        if (player.isCreative() || player.isSpectator())
            ci.cancel();

        int exposure = Pale.getPaleExposure(player);
        final boolean isTransfurred = ProcessTransfur.isPlayerTransfurred(player);
        final boolean isLightProtected = EquipmentChecks.isLightPaleProtected(player);
        final boolean isHeavyProtected = EquipmentChecks.isHeavyPaleProtected(player);

        int PALE_INFECTED_THRESHOLD = (int)(ChangedExposedConfig.PALE_INFECTED_THRESHOLD.get()*20);
        int PALE_STAGE1_THRESHOLD = (int)(ChangedExposedConfig.PALE_STAGE1_THRESHOLD.get()*20) + PALE_INFECTED_THRESHOLD;
        int PALE_STAGE2_THRESHOLD = (int)(ChangedExposedConfig.PALE_STAGE2_THRESHOLD.get()*20) + PALE_STAGE1_THRESHOLD;
        int PALE_STAGE3_THRESHOLD = (int)(ChangedExposedConfig.PALE_STAGE3_THRESHOLD.get()*20) + PALE_STAGE2_THRESHOLD;
        int PALE_TERMINAL_THRESHOLD = (int)(ChangedExposedConfig.PALE_TERMINAL_THRESHOLD.get()*20) + PALE_STAGE3_THRESHOLD;

        int PALE_STAGE1_DMG_INTERVAL = (int)(ChangedExposedConfig.PALE_STAGE1_DMG_TIMER.get()*20);
        int PALE_STAGE2_DMG_INTERVAL = (int)(ChangedExposedConfig.PALE_STAGE2_DMG_TIMER.get()*20);
        int PALE_STAGE3_DMG_INTERVAL = (int)(ChangedExposedConfig.PALE_STAGE3_DMG_TIMER.get()*20);
        int PALE_TERMINAL_DMG_INTERVAL = (int)(ChangedExposedConfig.PALE_TERMINAL_DMG_TIMER.get()*20);

        AtomicInteger localExposure = new AtomicInteger(0);

        // TODO: rewrite this mess
        // good luck future me
        player.level().getEntitiesOfClass(LivingEntity.class, new AABB(player.blockPosition()).inflate(2.5)).forEach(livingEntity -> {
            if (player == livingEntity) return;

            // i'm gonna have to rewrite this whole thing i think 🥀🥀🥀🥀
            if (livingEntity.getType().is(ChangedTags.EntityTypes.PALE_SMALL_EXPOSURE) && Pale.getPaleExposure(player) < 11800)
                localExposure.addAndGet(isHeavyProtected || isLightProtected ? 0 : 1);
            else if (livingEntity.getType().is(ChangedTags.EntityTypes.PALE_LARGE_EXPOSURE)) {
                if (!isLightProtected && !isHeavyProtected)
                    localExposure.addAndGet(2);
                else if ((isHeavyProtected || isLightProtected) && Pale.getPaleExposure(player) < 11800) {// gas mask prevents threshold cross
                    localExposure.addAndGet(1);
                }
            }
            else if (livingEntity instanceof Player otherPlayer) {
                int otherExposure = Pale.getPaleExposure(otherPlayer);
                if (otherExposure < PALE_INFECTED_THRESHOLD)
                    return;
                if (otherExposure >= PALE_STAGE1_THRESHOLD) {
                    if (!isLightProtected && !isHeavyProtected)
                        localExposure.addAndGet(1);
                    else if ((isLightProtected || isHeavyProtected) && Pale.getPaleExposure(player) < 11800) // gas mask prevents threshold cross
                        localExposure.addAndGet(1);
                }
                if (otherExposure >= PALE_STAGE2_THRESHOLD) {}
                    localExposure.addAndGet(1);
                if (otherExposure >= PALE_STAGE3_THRESHOLD) {}
                    localExposure.addAndGet(1);
            }
        });

        exposure += localExposure.getAcquire();
        if (exposure > 0 && isTransfurred)
            exposure--;
        if (exposure >= PALE_INFECTED_THRESHOLD && !isTransfurred)
            exposure++;


        Pale.setPaleExposure(player, exposure);

        if (ProcessTransfur.isPlayerTransfurred(player))
            return;

        // VVV effects VVV
        if (exposure >= PALE_STAGE1_THRESHOLD && exposure < PALE_STAGE2_THRESHOLD) {
            if (exposure % PALE_STAGE1_DMG_INTERVAL < 5) { // 1 Heart per 5 minutes
                player.hurt(ChangedDamageSources.PALE.source(player.level().registryAccess()), 2f);
            }
        } else if (exposure >= PALE_STAGE2_THRESHOLD && exposure < PALE_STAGE3_THRESHOLD) {
            if (exposure % PALE_STAGE2_DMG_INTERVAL < 5) { // 1 Heart per 2.5 minutes
                player.hurt(ChangedDamageSources.PALE.source(player.level().registryAccess()), 2f);
            }
        } else if (exposure >= PALE_STAGE3_THRESHOLD && exposure < PALE_TERMINAL_THRESHOLD) {
            if (exposure % PALE_STAGE3_DMG_INTERVAL < 5) { // 1 Heart per 1 minute
                player.hurt(ChangedDamageSources.PALE.source(player.level().registryAccess()), 2f);
            }
        } else if (exposure >= PALE_TERMINAL_THRESHOLD) {
            if (exposure % PALE_TERMINAL_DMG_INTERVAL < 5) { // 1 Heart per 10 seconds
                player.hurt(ChangedDamageSources.PALE.source(player.level().registryAccess()), 2f);
            }
        }
        if (ChangedExposedConfig.DEBUG.get()) {
            player.displayClientMessage(Component.literal(String.valueOf(exposure)), true);
        }
    }
}
