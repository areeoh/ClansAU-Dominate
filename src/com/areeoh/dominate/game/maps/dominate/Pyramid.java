package com.areeoh.dominate.game.maps.dominate;

import com.areeoh.dominate.game.dominate.DominateGame;
import com.areeoh.dominate.game.dominate.DominateMap;
import com.areeoh.dominate.game.GameManager;
import com.areeoh.dominate.game.capturepoint.CapturePoint;
import org.bukkit.Location;
import org.bukkit.World;

public class Pyramid extends DominateGame {

    public Pyramid(World world, GameManager gameManager) {
        super(world, DominateMap.PYRAMID, gameManager);

        addCapturePoint(new CapturePoint("Pyramid", new Location(getWorld(), -60, 6, -65)));
        addCapturePoint(new CapturePoint("Lake", new Location(getWorld(), -121, 4, -65)));
        addCapturePoint(new CapturePoint("Market", new Location(getWorld(), -118, 4, -119)));
        addCapturePoint(new CapturePoint("Shrine", new Location(getWorld(), -7, 10, -65)));
        addCapturePoint(new CapturePoint("Cactus Farm", new Location(getWorld(), -7, 4, -7)));

        addSpawnPoint("Red", new Location(getWorld(), -4, 6, -124, 45, 0));
        addSpawnPoint("Blue", new Location(getWorld(), -124, 6, -4, -135, 0));
    }
}
