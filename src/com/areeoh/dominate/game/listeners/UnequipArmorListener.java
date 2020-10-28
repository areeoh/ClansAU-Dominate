package com.areeoh.dominate.game.listeners;

import com.areeoh.core.client.ClientManager;
import com.areeoh.core.framework.Module;
import com.areeoh.dominate.game.dominate.DominateGame;
import com.areeoh.dominate.game.GameManager;
import com.areeoh.dominate.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class UnequipArmorListener extends Module<GameManager> implements Listener {

    public UnequipArmorListener(GameManager manager) {
        super(manager, "UnequipArmorListener");
    }

    @EventHandler
    public void onUnequipEvent(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        Game game = getManager(GameManager.class).getGame(player);
        if(game == null) {
            return;
        }
        event.setResult(Event.Result.DENY);
        event.setCancelled(true);
    }
}
