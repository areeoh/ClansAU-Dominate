package com.areeoh.dominate.game.entity;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class GameSelectorVillager extends EntityVillager {

    public GameSelectorVillager(World world) {
        super(world);

        goalSelector.a(0, new PathfinderGoalFloat(this));
        goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 0.6D));
        goalSelector.a(9, new PathfinderGoalInteract(this, EntityHuman.class, 3.0F, 1.0F));
        goalSelector.a(9, new PathfinderGoalInteract(this, EntityVillager.class, 5.0F, 0.02F));
        goalSelector.a(9, new PathfinderGoalRandomStroll(this, 0.6D));
        goalSelector.a(10, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));

        setCustomName(ChatColor.GREEN + ChatColor.BOLD.toString() + "Champions");
    }

    @Override
    public void onLightningStrike(EntityLightning entitylightning) {
    }

    @Override
    public void die(){
    }

    @Override
    public void move(double d0, double d1, double d2) {
    }

    @Override
    public void collide(Entity entity) {
    }

    @Override
    public boolean damageEntity(DamageSource damagesource, float f) {
        return false;
    }

    @Override
    public void g(double d0, double d1, double d2) {
    }
    @Override
    protected String z() {
        return "";
    }
    @Override
    protected String bo() {
        return "";
    }
    @Override
    protected String bp() {
        return "";
    }

    @Override
    public void b(EntityLiving entityliving) {
    }

    @Override
    public boolean a(EntityHuman entityhuman) {
        return false;
    }

    public Villager spawn(Location loc) {
        setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        getWorld().addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (Villager) getBukkitEntity();
    }
}
