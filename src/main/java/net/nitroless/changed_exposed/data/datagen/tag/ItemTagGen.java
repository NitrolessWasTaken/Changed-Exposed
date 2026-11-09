package net.nitroless.changed_exposed.data.datagen.tag;

import net.ltxprogrammer.changed.init.ChangedItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.nitroless.changed_exposed.ChangedExposed;
import net.nitroless.changed_exposed.data.ExposedTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ItemTagGen extends ItemTagsProvider {
    public ItemTagGen(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, ChangedExposed.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ExposedTags.ItemTags.light_pale_protection)
                .add(ChangedItems.FACE_MASK.get());

        this.tag(ExposedTags.ItemTags.heavy_pale_protection)
                .add(ChangedItems.GAS_MASK.get());
    }
}
