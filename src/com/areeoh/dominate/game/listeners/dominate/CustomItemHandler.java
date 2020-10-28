package com.areeoh.dominate.game.listeners.dominate;

import com.areeoh.core.framework.Module;
import com.areeoh.core.framework.updater.Update;
import com.areeoh.core.framework.updater.Updater;
import com.areeoh.core.utility.UtilMath;
import com.areeoh.dominate.game.Game;
import com.areeoh.dominate.game.GameManager;
import com.areeoh.dominate.game.data.CustomItem;
import com.areeoh.dominate.game.dominate.DominateGame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.UUID;

public class CustomItemHandler extends Module<GameManager> implements Updater {

    public CustomItemHandler(GameManager manager) {
        super(manager, "CustomItemHandler");
    }

    @Update
    public void onUpdate() {
        for (Game game : getManager().getGames()) {
            if (!(game instanceof DominateGame)) {
                continue;
            }
            for (UUID uuid : game.getPlayers()) {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null) {
                    continue;
                }
                if (game.isSpectating(player)) {
                    continue;
                }

                for (Iterator<CustomItem<GameManager>> iterator = ((DominateGame) game).getCustomItems().iterator(); iterator.hasNext(); ) {
                    CustomItem<GameManager> next = iterator.next();
                    if (UtilMath.offset(next.getItem().getLocation(), player.getLocation()) <= 2.0D) {
                        next.onPickup(player);
                        next.getItem().remove();
                        iterator.remove();
                        break;
                    }
                }
            }
        }
    }
}