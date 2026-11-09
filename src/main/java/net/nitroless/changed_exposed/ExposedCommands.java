package net.nitroless.changed_exposed;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.nitroless.changed_exposed.util.EquipmentChecks;

import static net.ltxprogrammer.changed.process.Pale.*;

// TODO: finish this
// TODO: replace literals with translatables
@Mod.EventBusSubscriber
public class ExposedCommands {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                // register main command
                // /changed_exposed
                Commands.literal("changed_exposed")
                        // test command
                        // /changed_exposed test
                        .then(Commands.literal("test")).executes(context -> {
                            context.getSource().sendSuccess(() -> Component
                                    .translatable("changed_exposed.command.test"), true);
                            return 1;
                        })

                        // dump a bunch of info during runtime :3
                        // /changed_exposed dumpDebugInfo
                        .then(Commands.literal("dumpDebugInfo")
                                .requires(player -> player.hasPermission(2))
                                .executes(context -> {
                                    var executor = context.getSource();

                                    // only works if debug mode is enabled
                                    // this is because it it shows some gameplay critical information
                                    if (ChangedExposedConfig.DEBUG.get()) {
                                        executor.sendSystemMessage(Component
                                                .translatable("changed_exposed.command.dumpDebugInfo.dumping"));

                                        String cureState;
                                        String wearingLightProtect;
                                        String wearingHeavyProtect;
                                        int exposureTicks = getPaleExposure(executor.getPlayer());
                                        double exposureSeconds = (double)exposureTicks/20;

                                        if (isCured(executor.getPlayer()))
                                            cureState = "Cured";
                                        else cureState = "Not cured";

                                        if (EquipmentChecks.isLightPaleProtected(executor.getPlayer()))
                                            wearingLightProtect = "Yes";
                                        else wearingLightProtect = "No";

                                        if (EquipmentChecks.isHeavyPaleProtected(executor.getPlayer()))
                                            wearingHeavyProtect = "Yes";
                                        else wearingHeavyProtect = "No";

                                        ChangedExposed.LOGGER.info("Player exposure value (ticks): " + exposureTicks);
                                        ChangedExposed.LOGGER.info("Player exposure value (seconds): " + exposureSeconds);
                                        ChangedExposed.LOGGER.info("Player cure state: " + cureState);
                                        ChangedExposed.LOGGER.info("Player wearing light Pale protection equipment: " + wearingLightProtect);
                                        ChangedExposed.LOGGER.info("Player wearing heavy Pale protection equipment: " + wearingHeavyProtect);
                                        if (exposureTicks < 0)
                                            ChangedExposed.LOGGER.info("NOTE: Negative values in the timer are intended.");
                                        if (cureState.contentEquals(null) || wearingLightProtect.contentEquals(null) || wearingHeavyProtect.contentEquals(null))
                                            // this is probably impossible but yknow
                                            ChangedExposed.LOGGER.error("Failed to gather some values. This is REALLY bad.");
                                        executor.sendSuccess(() -> Component
                                                .translatable("changed_exposed.command.dumpDebugInfo.finished"), false);
                                        return 1;
                                    }

                                    // return error if debug is not enabled lmao
                                    else {
                                        executor.sendFailure(Component
                                                .translatable("changed_exposed.command.dumpDebugInfo.noDebug1").append(Component
                                                .translatable("changed_exposed.command.dumpDebugInfo.noDebug2")));
                                        return 0;
                                    }
                                }))

                        // register setPaleExposure
                        // /changed_exposed setPaleExposure
                        .then(Commands.literal("setPaleExposure")
                                .requires(player -> player.hasPermission(2))
                                // add player argument
                                // /changed_exposed setPaleExposure <player>
                                .then(Commands.argument("player", EntityArgument.player())
                                        // add tick amount argument
                                        // /changed_exposed setPaleExposure <player> <exposure>
                                        .then(Commands.argument("exposure", IntegerArgumentType.integer(-576000, 576000))
                                                .executes(context -> {
                                                    int argument = IntegerArgumentType.getInteger(context, "exposure");
                                                    Player player = EntityArgument.getPlayer(context, "player");
                                                    var executor = context.getSource();

                                                    if (isCured(player)) {
                                                        executor.sendFailure(Component.translatable("changed_exposed.command.setPaleExposure.alreadyCured", player.getName()));
                                                        return 0;
                                                    }

                                                    // TODO: hello missing logic
                                                    tryCure(player);

                                                    if (argument < 0)
                                                        executor.sendSystemMessage(Component.translatable("changed_exposed.command.setPaleExposure.possiblyWrongCommandNote"));
                                                    return 1;
                                                })
                                        )
                                )
                        )

                        // register subcommand curePale
                        .then(Commands.literal("curePale").requires(player -> player.hasPermission(2))
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(context -> {
                                            Player player = EntityArgument.getPlayer(context, "player");

                                            if (isCured(player)) {

                                                return 0;
                                            }
                                            setPaleExposure(player, 0);
                                            return 1;
                                        })
                                )
                        )

                        // register new subcommands here
        );

        // register new commands here
    }
}
