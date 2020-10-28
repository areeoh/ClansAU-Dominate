package com.areeoh.dominate.game.dominate;

import com.areeoh.dominate.game.maps.dominate.*;

public enum DominateMap {
    NIIHAU_ISLAND("Niihau_Island", NiihauIsland.class),
    GULLEY("Gulley", Gulley.class),
    TWIN_PEAKS("Twin_Peaks", TwinPeaks.class),
    HIDDEN_VALLEY("Hidden_Valley", HiddenValley.class),
    PYRAMID("Pyramid", Pyramid.class);

    String worldName;
    Class<? extends DominateGame> clazz;

    DominateMap(String worldName, Class<? extends DominateGame> clazz) {
        this.worldName = worldName;
        this.clazz = clazz;
    }

    public Class<? extends DominateGame> getClazz() {
        return clazz;
    }

    public String getWorldName() {
        return worldName;
    }
}
