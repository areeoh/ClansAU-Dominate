package com.areeoh.dominate.game.data;

import com.areeoh.champions.role.RoleManager;
import com.areeoh.champions.role.roles.Assassin;
import com.areeoh.champions.role.roles.Ranger;
import com.areeoh.core.utility.UtilItem;
import com.areeoh.dominate.game.GameManager;
import com.areeoh.dominate.game.Game;
import com.areeoh.dominate.utility.InstantFirework;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ChestPickup extends CustomItem<GameManager> {

    public ChestPickup(GameManager dominateManager, Location location) {
        super(dominateManager, location, Material.CHEST);
    }

    @Override
    public void onPickup(Player player) {
        Game game = getManager().getGame(player);
        if(game == null) {
            return;
        }
        InstantFirework.spawn(getItem().getLocation(), FireworkEffect.builder().withColor(new Color[]{game.getTeam(player.getUniqueId()).getColor()}).with(FireworkEffect.Type.BALL_LARGE).trail(false));

        for (RoleManager roleManager : getManager().getPlugin().getManagers(RoleManager.class)) {
            if (!roleManager.getUsers().contains(player.getUniqueId())) {
                continue;
            }
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
            break;
        }
        player.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Your inventory was restocked!");
    }
}