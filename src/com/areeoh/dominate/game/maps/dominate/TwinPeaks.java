package com.areeoh.dominate.game.maps.dominate;

import com.areeoh.dominate.game.dominate.DominateGame;
import com.areeoh.dominate.game.dominate.DominateMap;
import com.areeoh.dominate.game.GameManager;
import org.bukkit.World;

public class TwinPeaks extends DominateGame {

    public TwinPeaks(World world, GameManager gameManager) {
        super(world, DominateMap.TWIN_PEAKS, gameManager);
    }
}
