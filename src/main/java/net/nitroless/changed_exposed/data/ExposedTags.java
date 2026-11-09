package net.nitroless.changed_exposed.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.nitroless.changed_exposed.ChangedExposed;

// TODO: implement
// also maybe datagen
public class ExposedTags {
    public static class ItemTags {
        public static final TagKey<Item> light_pale_protection = tag("light_pale_protection");
        public static final TagKey<Item> heavy_pale_protection = tag("heavy_pale_protection");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(ChangedExposed.MODID, name));
        }
    }

    public static class BlockTags {

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(ChangedExposed.MODID, name));
        }
    }
}
