package net.nitroless.changed_exposed.mixin.changed;

import net.ltxprogrammer.changed.process.Pale;
import net.minecraft.world.entity.player.Player;
import net.nitroless.changed_exposed.pale.PaleImproved;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Pale.class)

public abstract class PaleMixin {
    @Inject(method = "tickPaleExposure", at = @At("HEAD"), cancellable = true, remap = false)
    private static void tickPaleExposureMixin(Player player, CallbackInfo ci) {
        // moved my own tickPale away and simply canceled the original
        // its evil and disgusting but its more manageable for me
        // this should be fine as long as others dont try to mixin (unlikely)
        PaleImproved.tickPaleButAwesome(player);
        ci.cancel();
    }

    @Inject(method = "tryCure", at = @At("HEAD"), cancellable = true, remap = false)
    private static void tryCureMixin(Player player, CallbackInfoReturnable cir) {
        // likewise here
        cir.setReturnValue(PaleImproved.tryCurePale(player));
    }
}
