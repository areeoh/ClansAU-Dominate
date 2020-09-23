package com.areeoh.dominate.listeners;

import com.areeoh.combat.events.CustomDamageEvent;
import com.areeoh.dominate.DominateGame;
import com.areeoh.dominate.DominateManager;
import com.areeoh.framework.Module;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArmorStandHandler extends Module<DominateManager> implements Listener {

    public ArmorStandHandler(DominateManager manager) {
        super(manager, "ArmorStandHandler");
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
        DominateGame dominateGame = getManager().getDominateGame();
        if(dominateGame == null) {
            return;
        }
        if(dominateGame.getDominateWorld() == null) {
            return;
        }
        if(!dominateGame.getDominateWorld().getRoleManagerMap().containsKey(damagee)) {
            return;
        }
        event.setCancelled(true);
    }
}
