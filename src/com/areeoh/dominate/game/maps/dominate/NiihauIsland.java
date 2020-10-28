package com.areeoh.dominate.game.maps.dominate;

import com.areeoh.champions.role.roles.*;
import com.areeoh.dominate.game.dominate.DominateGame;
import com.areeoh.dominate.game.GameManager;
import com.areeoh.dominate.game.dominate.DominateMap;
import com.areeoh.dominate.game.capturepoint.CapturePoint;
import com.areeoh.dominate.game.data.ChestPickup;
import com.areeoh.dominate.game.data.PointPickup;
import org.bukkit.Location;
import org.bukkit.World;

public class NiihauIsland extends DominateGame {

    public NiihauIsland(World world, GameManager gameManager) {
        super(world, DominateMap.NIIHAU_ISLAND, gameManager);

        addCapturePoint(new CapturePoint("Center Point", new Location(getWorld(), -67, 27, 58)));
        addCapturePoint(new CapturePoint("North Docks", new Location(getWorld(), -66, 24, -2)));
        addCapturePoint(new CapturePoint("South Docks", new Location(getWorld(), -68, 24, 119)));
        addCapturePoint(new CapturePoint("Castle Point", new Location(getWorld(), -12, 37, 71)));
        addCapturePoint(new CapturePoint("Jungle Point", new Location(getWorld(), -122, 37, 45)));

        addArmorStand(new Location(getWorld(), -9.5D, 26, 0.5D), getGameManager().getManager(Assassin.class));
        addArmorStand(new Location(getWorld(), -6.5D, 26, -0.5D), getGameManager().getManager(Gladiator.class));
        addArmorStand(new Location(getWorld(), -3.5D, 26, 0.5D), getGameManager().getManager(Knight.class));
        addArmorStand(new Location(getWorld(), -2.5D, 26, 3.5D), getGameManager().getManager(Paladin.class));
        addArmorStand(new Location(getWorld(), -3.5D, 26, 6.5D), getGameManager().getManager(Ranger.class));

        addArmorStand(new Location(getWorld(), -125.5D, 26, 116.5D), getGameManager().getManager(Assassin.class));
        addArmorStand(new Location(getWorld(), -128.5D, 26, 117.5D), getGameManager().getManager(Gladiator.class));
        addArmorStand(new Location(getWorld(), -131.5D, 26, 116.5D), getGameManager().getManager(Knight.class));
        addArmorStand(new Location(getWorld(), -132.5D, 26, 113.5D), getGameManager().getManager(Paladin.class));
        addArmorStand(new Location(getWorld(), -131.5D, 26, 110.5D), getGameManager().getManager(Ranger.class));

        addCustomItem(new ChestPickup(getGameManager(), new Location(getWorld(), -88.5, 22, 96.5)));
        addCustomItem(new ChestPickup(getGameManager(), new Location(getWorld(), -46.5, 22, 20.5)));

        addCustomItem(new PointPickup(getGameManager(), new Location(getWorld(), -67.5, 21, 58.5)));
        addCustomItem(new PointPickup(getGameManager(), new Location(getWorld(), -23.5, 27, 80.5)));
        addCustomItem(new PointPickup(getGameManager(), new Location(getWorld(), -111.5, 27, 36.5)));

        addSpawnPoint("Red", new Location(getWorld(), -6.5, 26, 3.5, 45, 0));
        addSpawnPoint("Blue", new Location(getWorld(), -128.5, 26, 113.5, -135, 0));
    }
}