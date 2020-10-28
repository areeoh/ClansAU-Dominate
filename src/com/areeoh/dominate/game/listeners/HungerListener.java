package com.areeoh.dominate.game.listeners;

import com.areeoh.core.framework.Module;
import com.areeoh.dominate.game.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class HungerListener extends Module<GameManager> implements Listener {

    public HungerListener(GameManager manager) {
        super(manager, "HungerListener");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().setFoodLevel(20);
    }

    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}