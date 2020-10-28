package com.areeoh.dominate.game.listeners;

import com.areeoh.core.combat.events.CustomDamageEvent;
import com.areeoh.core.framework.Module;
import com.areeoh.dominate.game.GameManager;
import com.areeoh.dominate.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerPvpListener extends Module<GameManager> implements Listener {

    public PlayerPvpListener(GameManager manager) {
        super(manager, "PlayerPvpListener");
    }

    @EventHandler
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
        if(!gameDamagee.getTeam(damager).equals(gameDamager.getTeam(damagee))) {
            return;
        }
        event.setCancelled(true);
    }
}
