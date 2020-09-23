package com.areeoh.dominate.listeners;

import com.areeoh.dominate.DominateManager;
import com.areeoh.framework.Manager;
import com.areeoh.framework.Module;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class HungerHandler extends Module<DominateManager> implements Listener {

    public HungerHandler(DominateManager manager) {
        super(manager, "HungerHandler");
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