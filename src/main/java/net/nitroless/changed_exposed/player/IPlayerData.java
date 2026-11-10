package net.nitroless.changed_exposed.player;

public interface IPlayerData {
    boolean getCuredState();
    void setCuredState(boolean isCured);

    int getExposure();
    void setExposure(int exposure);

    int getExposurePrev();
    void setExposurePrev(int exposurePrev);
}
