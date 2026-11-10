package net.nitroless.changed_exposed.player;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<IPlayerData> PLAYER_DATA = CapabilityManager.get(new CapabilityToken<>() {});

    private final PlayerData playerdata = new PlayerData();
    private final LazyOptional<IPlayerData> optional = LazyOptional.of(() -> playerdata);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_DATA)
            return optional.cast();
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return playerdata.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        playerdata.deserializeNBT(nbt);
    }
}
