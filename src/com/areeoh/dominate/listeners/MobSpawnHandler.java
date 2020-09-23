package com.areeoh.dominate.listeners;

import com.areeoh.dominate.DominateManager;
import com.areeoh.framework.Module;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class MobSpawnHandler extends Module<DominateManager> implements Listener {

    public MobSpawnHandler(DominateManager manager) {
        super(manager, "MobSpawnHandler");
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if(!(event.getEntity() instanceof LivingEntity)) {
            return;
        }
        if(event.getEntity() instanceof ArmorStand) {
            return;
        }
        event.setCancelled(true);
    }
}
