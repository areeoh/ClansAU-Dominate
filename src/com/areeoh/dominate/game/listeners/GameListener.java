package com.areeoh.dominate.game.listeners;

import com.areeoh.core.framework.Module;
import com.areeoh.core.framework.updater.Update;
import com.areeoh.core.framework.updater.Updater;
import com.areeoh.dominate.game.Game;
import com.areeoh.dominate.game.GameManager;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GameListener extends Module<GameManager> implements Listener, Updater {

    public GameListener(GameManager manager) {
        super(manager, "GameListener");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().setGameMode(GameMode.ADVENTURE);
    }

    @Update
    public void onUpdate() {
        for (Game game : getManager().getGames()) {
            if (game.getGameState() != Game.GameState.LOBBY) {
                continue;
            }
            if (game.getPlayers().size() < game.MIN_PLAYERS) {
                continue;
            }
            game.startGame();
        }
    }
}
