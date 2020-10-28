package com.areeoh.dominate.game.listeners;

import com.areeoh.core.framework.Module;
import com.areeoh.dominate.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerWorldListener extends Module<GameManager> implements Listener {

    public PlayerWorldListener(GameManager manager) {
        super(manager, "PlayerWorldListener");
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.hidePlayer(event.getPlayer());
            event.getPlayer().hidePlayer(online);
        }
        for (Player online : event.getPlayer().getWorld().getPlayers()) {
            online.showPlayer(event.getPlayer());
            event.getPlayer().showPlayer(online);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.hidePlayer(event.getPlayer());
            event.getPlayer().hidePlayer(online);
        }
        for (Player online : event.getPlayer().getWorld().getPlayers()) {
            online.showPlayer(event.getPlayer());
            event.getPlayer().showPlayer(online);
        }
    }
}