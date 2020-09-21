package com.areeoh.dominate;

import com.areeoh.ClansAUCore;
import com.areeoh.framework.Manager;
import com.areeoh.framework.Module;
import com.areeoh.dominate.listeners.CapturePointListener;
import com.areeoh.dominate.listeners.SidebarHandler;
import com.areeoh.dominate.maps.*;

public class DominateManager extends Manager<Module> {

    private DominateGame dominateGame;

    public DominateManager(ClansAUCore plugin) {
        super(plugin, "DominateManager");
    }

    @Override
    public void registerModules() {
        addModule(new CapturePointListener(this));
        addModule(new SidebarHandler(this));
    }

    public DominateGame getDominateGame() {
        return dominateGame;
    }

    public void setDominateGame(DominateGame dominateGame) {
        this.dominateGame = dominateGame;
    }

    public enum DominateMap {
        NIIHAU_ISLAND("NiihauIsland",NiihauIsland.class),
        GULLEY("Gulley", Gulley.class),
        TWIN_PEAKS("Twin_Peaks", TwinPeaks.class),
        HIDDEN_VALLEY("Hidden_Valley", HiddenValley.class),
        PYRAMID("Pyramid", Pyramid.class);

        String worldName;
        Class<? extends DominateWorld> clazz;

        DominateMap(String worldName, Class<? extends DominateWorld> clazz) {
            this.worldName = worldName;
            this.clazz = clazz;
        }

        public Class<? extends DominateWorld> getClazz() {
            return clazz;
        }
    }
}
