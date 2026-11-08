package net.nitroless.changed_exposed.util;

import net.ltxprogrammer.changed.data.AccessorySlots;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.nitroless.changed_exposed.data.ExposedTags;

public class EquipmentChecks {
    // we go through each slot one by one
    // since i dont have a bajillion custom slots, there is no point in iterating through them
    // curios/accessories can fuck themselves lmao
    // changed mod provides its own method for checking equipment anyway
    // TODO: test this, i dont trust myself
    public static boolean isLightPaleProtected(Player player) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

        boolean isHeadWorn = helmet.is(ExposedTags.ItemTags.light_pale_protection);
        boolean isChestWorn = chestplate.is(ExposedTags.ItemTags.light_pale_protection);
        boolean isLegsWorn = leggings.is(ExposedTags.ItemTags.light_pale_protection);
        boolean isBootsWorn = boots.is(ExposedTags.ItemTags.light_pale_protection);

        // thanks ltxprogrammer, you program good, unlike me
        boolean accessories = AccessorySlots.isWearing(player, itemStack -> itemStack.is(ExposedTags.ItemTags.light_pale_protection));

        return  isHeadWorn || isChestWorn || isLegsWorn|| isBootsWorn || accessories;
    }

    public static boolean isHeavyPaleProtected(Player player) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

        boolean isHeadWorn = helmet.is(ExposedTags.ItemTags.heavy_pale_protection);
        boolean isChestWorn = chestplate.is(ExposedTags.ItemTags.heavy_pale_protection);
        boolean isLegsWorn = leggings.is(ExposedTags.ItemTags.heavy_pale_protection);
        boolean isBootsWorn = boots.is(ExposedTags.ItemTags.heavy_pale_protection);

        boolean accessories = AccessorySlots.isWearing(player, itemStack -> itemStack.is(ExposedTags.ItemTags.heavy_pale_protection));

        return  isHeadWorn || isChestWorn || isLegsWorn|| isBootsWorn || accessories;
    }
}
