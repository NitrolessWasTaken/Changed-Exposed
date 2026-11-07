package net.nitroless.changed_exposed.mixin;

import net.ltxprogrammer.changed.init.ChangedGameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ChangedGameRules.class)
public class ChangedGameRulesMixin {
    @ModifyArg(method = "<clinit>", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/GameRules$BooleanValue;create(Z)Lnet/minecraft/world/level/GameRules$Type;",
            ordinal = 1
    ))
    private static boolean defaultDoPaleToTrue(boolean neverFuckingDoingThisAgain) {
        return true;
    }
}
