package com.areeoh.dominate.listeners;

import com.areeoh.client.ClientManager;
import com.areeoh.dominate.DominateManager;
import com.areeoh.framework.Module;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class UnequipArmorHandler extends Module<DominateManager> implements Listener {

    public UnequipArmorHandler(DominateManager manager) {
        super(manager, "UnequipArmorHandler");
    }

    @EventHandler
    public void onUnequipEvent(InventoryClickEvent event) {
        if(getManager().getDominateGame() == null) {
            return;
        }
        if(getManager().getDominateGame().getDominateWorld() == null) {
            return;
        }
        if(getManager(ClientManager.class).getClient(event.getWhoClicked().getUniqueId()).isAdministrating()) {
            return;
        }
        event.setResult(Event.Result.DENY);
        event.setCancelled(true);
    }
}
