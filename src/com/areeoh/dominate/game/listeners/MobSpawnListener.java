package com.areeoh.dominate.game.listeners;

import com.areeoh.core.framework.Module;
import com.areeoh.dominate.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Objects;

public class MobSpawnListener extends Module<GameManager> implements Listener {

    public MobSpawnListener(GameManager manager) {
        super(manager, "MobSpawnListener");
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof ArmorStand) {
            return;
        }
        if(event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) {
            return;
        }
        event.setCancelled(true);
    }
}
