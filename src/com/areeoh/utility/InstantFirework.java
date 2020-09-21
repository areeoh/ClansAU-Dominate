package com.areeoh.utility;

import net.minecraft.server.v1_8_R3.EntityFireworks;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class InstantFirework extends EntityFireworks {

    private final Player[] players;
    private boolean gone = false;

    public InstantFirework(World world, Player... players) {
        super(world);
        this.players = players;
        a(0.25F, 0.25F);
    }


    public void t_() {
        if (this.gone) {
            return;
        }
        if (!this.world.isClientSide) {
            this.gone = true;
            if (this.players != null)
                if (this.players.length > 0) {
                    for (Player player : this.players)
                        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(new PacketPlayOutEntityStatus(this, (byte)17));
                } else {
                    this.world.broadcastEntityEffect(this, (byte)17);
                }   die();
        }
    }

    public static void spawn(Location location, FireworkEffect.Builder fireworkEffect, Player... players) {
        try {
            InstantFirework firework = new InstantFirework(((CraftWorld)location.getWorld()).getHandle(), players);
            FireworkMeta meta = ((Firework)firework.getBukkitEntity()).getFireworkMeta();
            meta.addEffect(fireworkEffect.build());
            ((Firework)firework.getBukkitEntity()).setFireworkMeta(meta);
            firework.setPosition(location.getX(), location.getY(), location.getZ());

            if (((CraftWorld)location.getWorld()).getHandle().addEntity(firework)) {
                firework.setInvisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
