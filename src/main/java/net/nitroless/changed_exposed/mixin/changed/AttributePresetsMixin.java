package net.nitroless.changed_exposed.mixin.changed;

import net.ltxprogrammer.changed.entity.AttributePresets;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;
import net.nitroless.changed_exposed.ChangedExposedConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


// TODO: finish the rest and find where attributes are actually applied to forms
@Mixin(AttributePresets.class)
public class AttributePresetsMixin {
    @Inject(method = "playerLike", at = @At("TAIL"), remap = false)
    private static void playerLikeMixin(AttributeMap map, CallbackInfo ci) {
        if (ChangedExposedConfig.NERF_LATEX.get()) {
            map.getInstance(Attributes.MOVEMENT_SPEED).setBaseValue(0.9);
            map.getInstance(ForgeMod.SWIM_SPEED.get()).setBaseValue(0.8);
            map.getInstance(Attributes.MAX_HEALTH).setBaseValue(16.0);
        }
    }
    @Inject(method = "wolfLike", at = @At("TAIL"), remap = false)
    private static void wolfLikeMixin(AttributeMap map, CallbackInfo ci) {
        if (ChangedExposedConfig.NERF_LATEX.get()) {
            map.getInstance(Attributes.MOVEMENT_SPEED).setBaseValue(1.05);
            map.getInstance(ForgeMod.SWIM_SPEED.get()).setBaseValue(0.8);
            map.getInstance(Attributes.MAX_HEALTH).setBaseValue(24.0);
        }
    }
}
