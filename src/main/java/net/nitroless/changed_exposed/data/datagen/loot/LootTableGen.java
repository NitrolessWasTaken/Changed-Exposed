package net.nitroless.changed_exposed.data.datagen.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class LootTableGen {
    public static LootTableProvider create(PackOutput packOutput) {
        return new LootTableProvider(packOutput, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(BlockLootTableGen::new, LootContextParamSets.BLOCK)
        ));
    }
}
