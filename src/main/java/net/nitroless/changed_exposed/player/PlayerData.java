package net.nitroless.changed_exposed.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class PlayerData implements IPlayerData, INBTSerializable<CompoundTag> {
    private int exposure;
    private int exposurePrev;
    private boolean isCured;

    @Override
    public boolean getCuredState() {return isCured;}
    @Override
    public int getExposurePrev() {return exposurePrev;}
    @Override
    public int getExposure() {return exposure;}

    @Override
    public void setCuredState(boolean isCured) {this.isCured = isCured;}
    @Override
    public void setExposurePrev(int exposurePrev) {this.exposurePrev = exposurePrev;}
    @Override
    public void setExposure(int exposure) {this.exposure = exposure;}


    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("exposure", this.exposure);
        tag.putInt("exposurePrev", this.exposurePrev);
        tag.putBoolean("isCured", this.isCured);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.exposure = nbt.getInt("exposure");
        this.exposurePrev = nbt.getInt("exposurePrev");
        this.isCured = nbt.getBoolean("isCured");
    }
}
