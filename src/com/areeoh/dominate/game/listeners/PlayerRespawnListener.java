package com.areeoh.dominate.game.listeners;

import com.areeoh.core.combat.events.CustomDeathEvent;
import com.areeoh.core.countdown.CountdownManager;
import com.areeoh.core.countdown.TitleCountdown;
import com.areeoh.core.framework.Module;
import com.areeoh.core.utility.UtilItem;
import com.areeoh.core.utility.UtilPlayer;
import com.areeoh.dominate.game.Game;
import com.areeoh.dominate.game.GameManager;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class PlayerRespawnListener extends Module<GameManager> implements Listener {

    public PlayerRespawnListener(GameManager manager) {
        super(manager, "PlayerRespawnListener");
    }

    @EventHandler
    public void onCustomDeath(CustomDeathEvent event) {
        if (!(event.getEntityDamagee() instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) event.getEntityDamagee();
        final Player p = player.getBukkitEntity().getPlayer();

        Game game = getManager().getGame(p);
        if(game == null) {
            return;
        }
        if (p.isInsideVehicle()) {
            Entity mount = p.getVehicle();
            mount.eject();
        }
        game.getSpectators().add(p.getUniqueId());


        Location add = p.getLocation().add(0.0D, 1.0D, 0.0D);
        if(add.getBlock().getRelative(BlockFace.UP).getType() == Material.AIR) {
            p.teleport(add);
        }
        p.setAllowFlight(true);
        p.setFlying(true);
        p.setFlySpeed(0.1F);
        p.setHealth(p.getMaxHealth());
        event.setCancelled(true);

        for (Player online : p.getWorld().getPlayers()) {
            online.hidePlayer(p);
        }

        getManager(CountdownManager.class).addCountdown(new TitleCountdown(p, 10000, "", "Respawning in " + ChatColor.GREEN + "%time%") {
            final ItemStack[] armorContents = p.getInventory().getArmorContents();

            @Override
            public void finish() {
                Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), () -> {
                    PacketPlayInClientCommand packet = new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN);
                    ((CraftPlayer) p).getHandle().playerConnection.a(packet);
                    p.setVelocity(new Vector(0, 0, 0));
                    p.setAllowFlight(false);
                    p.setGameMode(GameMode.ADVENTURE);
                    p.setHealth(p.getMaxHealth());

                    for (Player online : p.getWorld().getPlayers()) {
                        online.showPlayer(p);
                    }
                    p.getInventory().setArmorContents(armorContents);

                    p.getInventory().setItem(0, UtilItem.updateNames(new ItemStack(Material.IRON_SWORD)));
                    p.getInventory().setItem(1, UtilItem.updateNames(new ItemStack(Material.IRON_AXE)));

                    if (armorContents[0].getType().name().contains("LEATHER_") || armorContents[0].getType().name().contains("CHAINMAIL_")) {
                        p.getInventory().setItem(2, UtilItem.updateNames(new ItemStack(Material.BOW)));
                        p.getInventory().setItem(29, UtilItem.updateNames(new ItemStack(Material.ARROW, 16)));
                    }
                    game.getSpectators().remove(p.getUniqueId());

                    p.teleport(game.getFirstSpawnPoint(game.getTeam(p)));
                }, 2L);
            }
        });
        UtilPlayer.clearInventory(p);
    }
}
