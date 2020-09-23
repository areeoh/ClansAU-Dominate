package com.areeoh.dominate;

import com.areeoh.dominate.capturepoint.CapturePoint;
import com.areeoh.role.RoleManager;
import com.areeoh.teams.Team;
import com.areeoh.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class DominateWorld {

    private final DominateManager dominateManager;
    private final List<CapturePoint> capturePoints;
    private final Map<String, List<Location>> spawnPoints;
    private final World world;
    private final DominateManager.DominateMap dominateMap;
    private final Map<ArmorStand, RoleManager> roleManagerMap;

    public DominateWorld(DominateManager dominateManager, DominateManager.DominateMap dominateMap, String worldName) {
        this.dominateManager = dominateManager;
        this.dominateMap = dominateMap;
        this.world = Bukkit.getWorld(worldName);
        this.capturePoints = new ArrayList<>();
        this.spawnPoints = new HashMap<>();
        this.roleManagerMap = new HashMap<>();

        for (Entity entity : getWorld().getEntities()) {
            if(entity instanceof Player) {
                continue;
            }
            entity.remove();
        }
    }

    public void addSpawnPoint(String team, Location location) {
        final List<Location> spawnPoints = getSpawnPoints(team);
        spawnPoints.add(location);
    }

    public Location getRandomSpawnPoint(Team team) {
        List<Location> spawns = getSpawnPoints(team.getName());
        if (spawns.isEmpty()) {
            UtilMessage.broadcast("Dominate", team.getChatColor() + "Team " + team.getTag(false) + ChatColor.GRAY + " has no spawn points.");
            return null;
        }
        return spawns.get(ThreadLocalRandom.current().nextInt(0, spawns.size()));
    }

    public Location getFirstSpawnPoint(Team team) {
        List<Location> spawns = getSpawnPoints(team.getName());
        if (spawns.isEmpty()) {
            UtilMessage.broadcast("Dominate", team.getChatColor() + "Team " + team.getTag(false) + ChatColor.GRAY + " has no spawn points.");
            return null;
        }
        return spawns.get(0);
    }

    public List<Location> getSpawnPoints(String team) {
        if (!getSpawnPoints().containsKey(team)) {
            getSpawnPoints().put(team, new ArrayList<>());
        }
        return getSpawnPoints().get(team);
    }

    public Map<String, List<Location>> getSpawnPoints() {
        return spawnPoints;
    }

    public void addCapturePoint(CapturePoint capturePoint) {
        this.capturePoints.add(capturePoint);
    }

    public CapturePoint getCapturePoint(Location location) {
        for (CapturePoint capturePoint : capturePoints) {
            if (capturePoint.getLocation().equals(location)) {
                return capturePoint;
            }
        }
        return null;
    }

    protected void addArmorStand(Location spawnLoc, RoleManager roleManager) {
        ArmorStand armorStand = (ArmorStand) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.ARMOR_STAND);
        armorStand.setVisible(true);
        armorStand.setBasePlate(false);

        armorStand.setHelmet(new ItemStack(roleManager.getPieces()[0]));
        armorStand.setChestplate(new ItemStack(roleManager.getPieces()[1]));
        armorStand.setLeggings(new ItemStack(roleManager.getPieces()[2]));
        armorStand.setBoots(new ItemStack(roleManager.getPieces()[3]));

        roleManagerMap.put(armorStand, roleManager);
    }

    public Map<ArmorStand, RoleManager> getRoleManagerMap() {
        return roleManagerMap;
    }

    public List<CapturePoint> getCapturePoints() {
        return capturePoints;
    }

    public World getWorld() {
        return world;
    }

    public DominateManager.DominateMap getDominateMap() {
        return dominateMap;
    }

    public DominateManager getDominateManager() {
        return dominateManager;
    }
}