package com.areeoh.dominate.game.listeners;

import com.areeoh.core.framework.Module;
import com.areeoh.dominate.game.GameManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerSoupListener extends Module<GameManager> implements Listener {
    public PlayerSoupListener(GameManager manager) {
        super(manager, "PlayerSoupListener");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) {
            return;
        }
        if (!event.getItem().getType().equals(Material.MUSHROOM_SOUP)) {
            return;
        }
        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }
        Player player = event.getPlayer();
        event.setCancelled(true);
        player.getInventory().setItemInHand(new ItemStack(Material.AIR));
        player.playSound(player.getLocation(), Sound.EAT, 1.0F, 1.0F);
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2));
    }
}
