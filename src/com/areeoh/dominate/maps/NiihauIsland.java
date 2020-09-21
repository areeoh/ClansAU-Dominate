package com.areeoh.dominate.maps;

import com.areeoh.dominate.DominateManager;
import com.areeoh.dominate.DominateWorld;
import com.areeoh.dominate.capturepoint.CapturePoint;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class NiihauIsland extends DominateWorld {

    public NiihauIsland() {
        super(DominateManager.DominateMap.NIIHAU_ISLAND, "Niihau_Island");

        addCapturePoint(new CapturePoint("Center Point", new Location(getWorld(), -67, 27, 58)));
        addCapturePoint(new CapturePoint("North Docks", new Location(getWorld(), -66, 24, -2)));
        addCapturePoint(new CapturePoint("South Docks", new Location(getWorld(), -68, 24, 119)));
        addCapturePoint(new CapturePoint("Castle Point", new Location(getWorld(), -12, 37, 71)));
        addCapturePoint(new CapturePoint("Jungle Point", new Location(getWorld(), -122, 37, 45)));

        addSpawnPoint("Red", new Location(getWorld(), -6.5, 26, 3.5, 45, 0));
        addSpawnPoint("Blue", new Location(getWorld(), -128.5, 26, 113.5, -135, 0));
    }
}