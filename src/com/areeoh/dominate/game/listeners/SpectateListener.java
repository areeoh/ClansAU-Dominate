package com.areeoh.dominate.game.listeners;

import com.areeoh.core.combat.events.CustomDamageEvent;
import com.areeoh.core.framework.Module;
import com.areeoh.dominate.game.Game;
import com.areeoh.dominate.game.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class SpectateListener extends Module<GameManager> implements Listener {

    public SpectateListener(GameManager manager) {
        super(manager, "SpectateListener");
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onCustomDamage(CustomDamageEvent event) {
        if(event.isCancelled()) {
            return;
        }
        Player damager = event.getDamagerPlayer();
        if(damager == null) {
            return;
        }
        Player damagee = event.getDamageePlayer();
        if(damagee == null) {
            return;
        }
        Game gameDamagee = getManager(GameManager.class).getGame(damagee);
        if(gameDamagee == null) {
            return;
        }
        Game gameDamager = getManager(GameManager.class).getGame(damager);
        if(gameDamager == null) {
            return;
        }
        if(gameDamagee.getTeam(damager).equals(gameDamager.getTeam(damagee))) {
            return;
        }
        if(gameDamager.isSpectating(damager) || gameDamagee.isSpectating(damagee)) {
            event.setCancelled(true);
        }
    }
}
