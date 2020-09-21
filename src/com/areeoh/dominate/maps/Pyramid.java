package com.areeoh.dominate.maps;

import com.areeoh.dominate.DominateManager;
import com.areeoh.dominate.DominateWorld;
import com.areeoh.dominate.capturepoint.CapturePoint;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Pyramid extends DominateWorld {
    public Pyramid() {
        super(DominateManager.DominateMap.PYRAMID, "Pyramid");

        addCapturePoint(new CapturePoint("Pyramid", new Location(getWorld(), -60, 6, -65)));
        addCapturePoint(new CapturePoint("Lake", new Location(getWorld(), -121, 4, -65)));
        addCapturePoint(new CapturePoint("Market", new Location(getWorld(), -118, 4, -119)));
        addCapturePoint(new CapturePoint("Shrine", new Location(getWorld(), -7, 10, -65)));
        addCapturePoint(new CapturePoint("Cactus Farm", new Location(getWorld(), -7, 4, -7)));

        addSpawnPoint("Red", new Location(getWorld(), -4, 6, -124, 45, 0));
        addSpawnPoint("Blue", new Location(getWorld(), -124, 6, -4, -135, 0));
    }
}
