package net.nitroless.changed_exposed;

import net.ltxprogrammer.changed.data.AccessorySlots;
import net.ltxprogrammer.changed.init.ChangedItems;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class Util {
    // REMEMBER TO ACTUALLY MAKE THIS LMAO
    public static boolean isLightPaleProtected(Player player) {
        return AccessorySlots.isWearing(player, itemStack -> itemStack.is(ChangedItems.FACE_MASK.get())) || false;
    }

    public static boolean isHeavyPaleProtected(Player player) {

        return true;
    }
}
