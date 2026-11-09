package net.nitroless.changed_exposed.data.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class BlockLootTableGen extends BlockLootSubProvider {
    protected BlockLootTableGen() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

    }

    // TODO: some bs
    // thanks random yt tutorial guy
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return super.getKnownBlocks();
    }
}
