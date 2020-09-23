package com.areeoh.dominate.maps;

import com.areeoh.dominate.DominateManager;
import com.areeoh.dominate.DominateWorld;
import com.areeoh.dominate.capturepoint.CapturePoint;
import com.areeoh.role.roles.*;
import org.bukkit.Location;

public class NiihauIsland extends DominateWorld {

    public NiihauIsland(DominateManager dominateManager) {
        super(dominateManager, DominateManager.DominateMap.NIIHAU_ISLAND, "Niihau_Island");

        addCapturePoint(new CapturePoint("Center Point", new Location(getWorld(), -67, 27, 58)));
        addCapturePoint(new CapturePoint("North Docks", new Location(getWorld(), -66, 24, -2)));
        addCapturePoint(new CapturePoint("South Docks", new Location(getWorld(), -68, 24, 119)));
        addCapturePoint(new CapturePoint("Castle Point", new Location(getWorld(), -12, 37, 71)));
        addCapturePoint(new CapturePoint("Jungle Point", new Location(getWorld(), -122, 37, 45)));

        addArmorStand(new Location(getWorld(), -9.5D, 26, 0.5D), dominateManager.getManager(Assassin.class));
        addArmorStand(new Location(getWorld(), -6.5D, 26, -0.5D), dominateManager.getManager(Gladiator.class));
        addArmorStand(new Location(getWorld(), -3.5D, 26, 0.5D), dominateManager.getManager(Knight.class));
        addArmorStand(new Location(getWorld(), -2.5D, 26, 3.5D), dominateManager.getManager(Paladin.class));
        addArmorStand(new Location(getWorld(), -3.5D, 26, 6.5D), dominateManager.getManager(Ranger.class));

        addArmorStand(new Location(getWorld(), -125.5D, 26, 116.5D), dominateManager.getManager(Assassin.class));
        addArmorStand(new Location(getWorld(), -128.5D, 26, 117.5D), dominateManager.getManager(Gladiator.class));
        addArmorStand(new Location(getWorld(), -131.5D, 26, 116.5D), dominateManager.getManager(Knight.class));
        addArmorStand(new Location(getWorld(), -132.5D, 26, 113.5D), dominateManager.getManager(Paladin.class));
        addArmorStand(new Location(getWorld(), -131.5D, 26, 110.5D), dominateManager.getManager(Ranger.class));

        addSpawnPoint("Red", new Location(getWorld(), -6.5, 26, 3.5, 45, 0));
        addSpawnPoint("Blue", new Location(getWorld(), -128.5, 26, 113.5, -135, 0));
    }
}