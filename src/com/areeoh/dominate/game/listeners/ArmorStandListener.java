package com.areeoh.dominate.game.listeners;

import com.areeoh.core.combat.events.CustomDamageEvent;
import com.areeoh.core.framework.Module;
import com.areeoh.dominate.game.GameManager;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArmorStandListener extends Module<GameManager> implements Listener {

    public ArmorStandListener(GameManager manager) {
        super(manager, "ArmorStandListener");
    }

    @EventHandler
    public void onCustomDamage(CustomDamageEvent event) {
        LivingEntity damagee = event.getDamageeLivingEntity();
        if(damagee == null) {
            return;
        }
        if(!(damagee instanceof ArmorStand)) {
            return;
        }
        event.setCancelled(true);
    }
}
