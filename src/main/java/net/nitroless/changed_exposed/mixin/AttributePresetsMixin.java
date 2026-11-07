package net.nitroless.changed_exposed.mixin;

import net.ltxprogrammer.changed.entity.AttributePresets;
import net.ltxprogrammer.changed.init.ChangedAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;
import net.nitroless.changed_exposed.ChangedExposedConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AttributePresets.class)
public class AttributePresetsMixin {
    @Inject(method = "playerLike", at = @At("TAIL"))
    public static void playerLikeMixin(AttributeMap map, CallbackInfo ci) {
        if (ChangedExposedConfig.NERF_LATEX.get()) {
            map.getInstance(Attributes.MOVEMENT_SPEED).setBaseValue(0.9);
            map.getInstance(Attributes.MAX_HEALTH).setBaseValue(16.0);
            map.getInstance(ForgeMod.);
        }
    }
    @Inject(method = "wolfLike", at = @At("TAIL"))
    public static void wolfLikeMixin(AttributeMap map, CallbackInfo ci) {
        if (ChangedExposedConfig.NERF_LATEX.get()) {
            map.getInstance(ChangedAttributes.);
        }
    }
}
