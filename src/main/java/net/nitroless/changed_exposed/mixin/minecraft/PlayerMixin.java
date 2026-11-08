package net.nitroless.changed_exposed.mixin.minecraft;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

// im so fucking scared
@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Unique
    public boolean isCured = false;

    public boolean getCuredState() {
        return isCured;
    }

    public void setCuredState(boolean input) {
        isCured = input;
    }
}
