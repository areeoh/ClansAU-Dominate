package com.areeoh.dominate.game.listeners;

import com.areeoh.champions.role.RoleManager;
import com.areeoh.champions.role.roles.Assassin;
import com.areeoh.champions.role.roles.Ranger;
import com.areeoh.core.framework.Module;
import com.areeoh.core.utility.UtilItem;
import com.areeoh.dominate.game.GameManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ClassSelectListener extends Module<GameManager> implements Listener {

    public ClassSelectListener(GameManager manager) {
        super(manager, "ClassSelectListener");
    }

    @EventHandler
    public void onArmorStandInteract(PlayerArmorStandManipulateEvent event) {
        ItemStack itemStack = event.getArmorStandItem();
        Player player = event.getPlayer();
        for (RoleManager roleManager : getPlugin().getManagers(RoleManager.class)) {
            if (Arrays.asList(roleManager.getPieces()).contains(itemStack.getType())) {
                event.setCancelled(true);
                player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
                player.getInventory().clear();

                getPlugin().getManagers(RoleManager.class).forEach(role -> role.getUsers().remove(player.getUniqueId()));

                player.getInventory().setHelmet(UtilItem.updateNames(new ItemStack(roleManager.getPieces()[0])));
                player.getInventory().setChestplate(UtilItem.updateNames(new ItemStack(roleManager.getPieces()[1])));
                player.getInventory().setLeggings(UtilItem.updateNames(new ItemStack(roleManager.getPieces()[2])));
                player.getInventory().setBoots(UtilItem.updateNames(new ItemStack(roleManager.getPieces()[3])));

                player.getInventory().setItem(0, UtilItem.updateNames(new ItemStack(Material.IRON_SWORD)));
                player.getInventory().setItem(1, UtilItem.updateNames(new ItemStack(Material.IRON_AXE)));
                if (roleManager instanceof Assassin || roleManager instanceof Ranger) {
                    player.getInventory().setItem(2, UtilItem.updateNames(new ItemStack(Material.BOW)));
                    player.getInventory().setItem(29, UtilItem.updateNames(new ItemStack(Material.ARROW, 16)));
                }
                for (int i = 0; i < 3; i++) {
                    player.getInventory().setItem(((roleManager instanceof Assassin || roleManager instanceof Ranger) ? 3 : 2) + i, UtilItem.updateNames(new ItemStack(Material.MUSHROOM_SOUP)));
                }
                roleManager.doEquip(player);
                break;
            }
        }
    }
}
