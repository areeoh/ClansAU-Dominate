package com.areeoh.dominate.game.listeners;

import com.areeoh.core.client.ClientManager;
import com.areeoh.core.framework.Module;
import com.areeoh.dominate.game.Game;
import com.areeoh.dominate.game.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakBlockListener extends Module<GameManager> implements Listener {

    public BreakBlockListener(GameManager manager) {
        super(manager, "BreakBlockListener");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Game game = getManager().getGame(event.getPlayer());
        if(game == null) {
            return;
        }
        if(!game.getWorld().equals(event.getPlayer().getWorld())) {
            return;
        }
        if(getManager(ClientManager.class).getClient(event.getPlayer().getUniqueId()).isAdministrating()) {
            return;
        }
        event.setCancelled(true);
    }
}
