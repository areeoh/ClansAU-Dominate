package com.areeoh.dominate.game.listeners;

import com.areeoh.core.framework.Module;
import com.areeoh.dominate.game.GameManager;
import com.areeoh.dominate.game.Game;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener extends Module<GameManager> implements Listener {

    public PlayerDropItemListener(GameManager manager) {
        super(manager, "PlayerDropItemListener");
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Game game = getManager(GameManager.class).getGame(event.getPlayer().getUniqueId());
        if (game == null) {
            return;
        }
        if (game.getTeam(event.getPlayer()) == null) {
            return;
        }
        if(event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }
        event.setCancelled(true);
    }
}
