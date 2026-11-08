package net.nitroless.changed_exposed.mixin.changed;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.ltxprogrammer.changed.command.CommandPale;
import net.ltxprogrammer.changed.process.Pale;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// TODO: cancel this method entirely and use own implementation
@Mixin(CommandPale.class)
public class CommandPaleMixin {
    @Inject(method = "registerCommands", at = @At("HEAD"), remap = false, cancellable = true)
    private static void registerCommandsMixin(RegisterCommandsEvent event, CallbackInfo ci) {
        event.getDispatcher().register(Commands.literal("setpaleexposure").requires(p -> p.hasPermission(2))
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("exposure", IntegerArgumentType.integer(-1, 576000))
                                .executes(context -> {
                                    int providedArg = IntegerArgumentType.getInteger(context, "exposure")*20;

                                    Pale.setPaleExposure(EntityArgument.getPlayer(context, "player"), providedArg);
                                    context.getSource().sendSuccess(() -> Component.literal("Exposure set to " + providedArg + " seconds"), true);

                                    if (IntegerArgumentType.getInteger(context, "exposure") == -1) {
                                        context.getSource().sendSuccess(() -> Component.literal("Note: Use /curepale instead"), true);
                                    }

                                    return Command.SINGLE_SUCCESS;
                                })
                        )));
        event.getDispatcher().register(Commands.literal("curepale").requires(p -> p.hasPermission(2))
                .then(Commands.argument("player", EntityArgument.player())
                        .executes(context -> {
                            Player player = EntityArgument.getPlayer(context, "player");
                            Pale.setPaleExposure(player, -1); // Reset pale exposure incase player is not TF'd
                            Pale.tryCure(player);
                            return Command.SINGLE_SUCCESS;
                        })
                ));
        ci.cancel();
    }
}
