package com.areeoh.dominate.game.maps.dominate;

import com.areeoh.dominate.game.dominate.DominateGame;
import com.areeoh.dominate.game.GameManager;
import com.areeoh.dominate.game.dominate.DominateMap;
import org.bukkit.World;

public class Gulley extends DominateGame {

    public Gulley(World world, GameManager gameManager) {
        super(world, DominateMap.GULLEY, gameManager);
    }
}