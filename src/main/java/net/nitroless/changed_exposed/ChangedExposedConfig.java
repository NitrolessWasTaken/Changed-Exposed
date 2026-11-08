package net.nitroless.changed_exposed;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = ChangedExposed.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChangedExposedConfig
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.DoubleValue PALE_INFECTED_THRESHOLD;
    public static final ForgeConfigSpec.DoubleValue PALE_STAGE1_THRESHOLD;
    public static final ForgeConfigSpec.DoubleValue PALE_STAGE2_THRESHOLD;
    public static final ForgeConfigSpec.DoubleValue PALE_STAGE3_THRESHOLD;
    public static final ForgeConfigSpec.DoubleValue PALE_TERMINAL_THRESHOLD;

    public static final ForgeConfigSpec.DoubleValue PALE_STAGE1_DMG_TIMER;
    public static final ForgeConfigSpec.DoubleValue PALE_STAGE2_DMG_TIMER;
    public static final ForgeConfigSpec.DoubleValue PALE_STAGE3_DMG_TIMER;
    public static final ForgeConfigSpec.DoubleValue PALE_TERMINAL_DMG_TIMER;

    public static final ForgeConfigSpec.IntValue PALE_STAGE1_DMG;
    public static final ForgeConfigSpec.IntValue PALE_STAGE2_DMG;
    public static final ForgeConfigSpec.IntValue PALE_STAGE3_DMG;
    public static final ForgeConfigSpec.IntValue PALE_TERMINAL_DMG;

    public static final ForgeConfigSpec.BooleanValue NERF_LATEX;

    public static final ForgeConfigSpec.BooleanValue DEBUG;

    static {
        BUILDER.comment("Pale").push("pale");

        BUILDER.comment("Infection thresholds").push("thresholds");
        BUILDER.comment("Note: Timers are stacked on top of each other.");
        BUILDER.comment("To reach terminal stage, the timer has to go through all stage timers combined.");
        PALE_INFECTED_THRESHOLD = BUILDER
                .comment("How much you can be exposed to Pale before you become infected, measured in seconds. Timer increases when near entities that spread Pale.")
                .defineInRange("paleInfectedThreshold", 60.0, 0.0, 28800.0);
        PALE_STAGE1_THRESHOLD = BUILDER
                .comment("How long until Pale advances to stage 1, measured in seconds.")
                .defineInRange("paleStage1Threshold",1200.0,0.0,28800.0);
        PALE_STAGE2_THRESHOLD = BUILDER
                .comment("How long until Pale advances to stage 2, measured in seconds.")
                .defineInRange("paleStage2Threshold",2400.0,0.0,28800.0);
        PALE_STAGE3_THRESHOLD = BUILDER
                .comment("How long until Pale advances to stage 3, measured in seconds.")
                .defineInRange("paleStage3Threshold",3000.0,0.0,28800.0);
        PALE_TERMINAL_THRESHOLD = BUILDER
                .comment("How long until Pale reaches terminal stage, measured in seconds.")
                .defineInRange("paleTerminalThreshold",3600.0,0.0,28800.0);
        BUILDER.pop();

        BUILDER.comment("Damage timers").push("damageTimers");
        PALE_STAGE1_DMG_TIMER = BUILDER
                .comment("How frequently you should take damage at stage 1, measured in seconds.")
                .defineInRange("paleStage1DamageTimer",120.0,0.0,600.0);
        PALE_STAGE2_DMG_TIMER = BUILDER
                .comment("How frequently you should take damage at stage 2, measured in seconds.")
                .defineInRange("paleStage2DamageTimer",45.0,0.0,600.0);
        PALE_STAGE3_DMG_TIMER = BUILDER
                .comment("How frequently you should take damage at stage 3, measured in seconds.")
                .defineInRange("paleStage3DamageTimer",20.0,0.0,600.0);
        PALE_TERMINAL_DMG_TIMER = BUILDER
                .comment("How frequently you should take damage at terminal stage,  measured in seconds.")
                .defineInRange("paleTerminalDamageTimer",5.0,0.0,600.0);
        BUILDER.pop();

        BUILDER.comment("Damage amounts").push("damageAmounts");
        PALE_STAGE1_DMG = BUILDER
                .comment("How much damage you take at stage 1, measured in half hearts.")
                .defineInRange("paleStage1Damage",1,0,100);
        PALE_STAGE2_DMG = BUILDER
                .comment("How much damage you take at stage 2, measured in half hearts.")
                .defineInRange("paleStage2Damage",1,0,100);
        PALE_STAGE3_DMG = BUILDER
                .comment("How much damage you take at stage 3, measured in half hearts.")
                .defineInRange("paleStage3Damage",2,0,100);
        PALE_TERMINAL_DMG = BUILDER
                .comment("How much damage you take at terminal stage, measured in half hearts.")
                .defineInRange("paleStage4Damage",2,0,100);

        BUILDER.pop();
        // exited pale category, we are on top level

        BUILDER.comment("Latex").push("latex");
        NERF_LATEX = BUILDER
                .comment("Whether to nerf latex attributes. Recommended to keep on.")
                .define("nerfLatex", true);
        BUILDER.pop();

        DEBUG = BUILDER.comment("Guess fucking what this is").comment("Do not turn this on unless specifically requested").define("debug", false);
    }

    static final ForgeConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        // don't @ me
        // ...so toString() did NOT mean what i thought it did
        ChangedExposed.LOGGER.debug("PALE_INFECTED_THRESHOLD = " + PALE_INFECTED_THRESHOLD.get());
        ChangedExposed.LOGGER.debug("PALE_STAGE1_THRESHOLD = " +  PALE_STAGE1_THRESHOLD.get());
        ChangedExposed.LOGGER.debug("PALE_STAGE2_THRESHOLD = " +  PALE_STAGE2_THRESHOLD.get());
        ChangedExposed.LOGGER.debug("PALE_STAGE3_THRESHOLD = " +  PALE_STAGE3_THRESHOLD.get());
        ChangedExposed.LOGGER.debug("PALE_TERMINAL_THRESHOLD = " +  PALE_TERMINAL_THRESHOLD.get());

        ChangedExposed.LOGGER.debug("PALE_STAGE1_DMG_TIMER = " + PALE_STAGE1_DMG_TIMER.get());
        ChangedExposed.LOGGER.debug("PALE_STAGE2_DMG_TIMER = " + PALE_STAGE2_DMG_TIMER.get());
        ChangedExposed.LOGGER.debug("PALE_STAGE3_DMG_TIMER = " + PALE_STAGE3_DMG_TIMER.get());
        ChangedExposed.LOGGER.debug("PALE_TERMINAL_DMG_TIMER = " + PALE_TERMINAL_DMG_TIMER.get());

        ChangedExposed.LOGGER.debug("PALE_STAGE1_DMG = " + PALE_STAGE1_DMG.get());
        ChangedExposed.LOGGER.debug("PALE_STAGE2_DMG = " + PALE_STAGE2_DMG.get());
        ChangedExposed.LOGGER.debug("PALE_STAGE3_DMG = " + PALE_STAGE3_DMG.get());
        ChangedExposed.LOGGER.debug("PALE_TERMINAL_DMG = " + PALE_TERMINAL_DMG.get());

        ChangedExposed.LOGGER.debug("NERF_LATEX = " + NERF_LATEX.get());
    }
}
