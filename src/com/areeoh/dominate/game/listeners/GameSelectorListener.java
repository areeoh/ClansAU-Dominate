package com.areeoh.dominate.game.listeners;

import com.areeoh.core.framework.Module;
import com.areeoh.core.menu.MenuManager;
import com.areeoh.dominate.game.GameManager;
import com.areeoh.dominate.game.entity.GameSelectorVillager;
import com.areeoh.dominate.game.menu.GameSelectorMenu;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class GameSelectorListener extends Module<GameManager> implements Listener {

    public GameSelectorListener(GameManager manager) {
        super(manager, "GameSelectorListener");
    }

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        if(entity == null) {
            return;
        }
        if(!(((CraftLivingEntity)entity).getHandle() instanceof GameSelectorVillager)) {
            return;
        }
        event.getPlayer().openInventory(new GameSelectorMenu(getManager(MenuManager.class), event.getPlayer()).getInventory());
    }
}
