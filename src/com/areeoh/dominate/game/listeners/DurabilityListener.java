package com.areeoh.dominate.game.listeners;

import com.areeoh.core.framework.Module;
import com.areeoh.dominate.game.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class DurabilityListener extends Module<GameManager> implements Listener {

    public DurabilityListener(GameManager manager) {
        super(manager, "DurabilityListener");
    }

    @EventHandler
    public void onDurability(PlayerItemDamageEvent event) {
        event.setCancelled(true);
    }
}
