package com.areeoh.dominate.game.listeners;

import com.areeoh.core.framework.Module;
import com.areeoh.dominate.game.Game;
import com.areeoh.dominate.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener extends Module<GameManager> implements Listener {

    public PlayerJoinListener(GameManager manager) {
        super(manager, "PlayerJoinListener");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Game game = getManager().getGame(player);
        if(game != null) {
            player.teleport(game.getFirstSpawnPoint(game.getTeam(player)));
            return;
        }
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
    }
}
