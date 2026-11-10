package net.nitroless.changed_exposed.mixin.changed;

import net.ltxprogrammer.changed.command.CommandPale;
import net.minecraftforge.event.RegisterCommandsEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandPale.class)
public class CommandPaleMixin {
    @Inject(method = "registerCommands", at = @At("HEAD"), remap = false, cancellable = true)
    private static void registerCommandsMixin(RegisterCommandsEvent event, CallbackInfo ci) {
        // we use our own commands over at ExposedCommands.class
        ci.cancel();
    }
}
