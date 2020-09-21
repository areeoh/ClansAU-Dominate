package com.areeoh.dominate.capturepoint;

import com.areeoh.teams.Team;
import com.areeoh.utility.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CapturePoint {

    private final String name;
    private Team team = null;
    private final Location location;
    private final double radius = 3.5;
    private final Cuboid cuboid;

    public CapturePoint(String name, Location location) {
        this.name = name;
        this.location = location;
        this.cuboid = new Cuboid(location.clone().add(radius, radius, radius), location.clone().add(-radius, -radius, -radius));
        for (Block block : this.cuboid.getBlocks()) {
            if(block.getType() == Material.WOOL) {
                block.setData(DyeColor.WHITE.getWoolData());
            }
            if(block.getType() == Material.STAINED_GLASS) {
                block.setData(DyeColor.WHITE.getData());
            }
        }
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public double getRadius() {
        return radius;
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Location getRealLocation() {
        return location.clone().add(location.getX() > 0 ? 0.5 : -0.5, 0.0, location.getZ() > 0 ? 0.5 : -0.5);
    }

    public List<Player> getNearbyPlayers() {
        List<Player> players = new ArrayList<>();
        getLocation().getWorld().getPlayers().forEach(player -> {
            if(getOffset(getLocation(), player.getLocation()) < radius) {
                players.add(player);
            }
        });
        return players;
    }

    public double getProgress() {
        final List<Block> blocks = getCuboid().getBlocks().stream().filter(block -> block.getType() == Material.STAINED_GLASS && block.getData() != DyeColor.WHITE.getData()).filter(block -> !getRealLocation().add(0, -1, 0).getBlock().equals(block)).collect(Collectors.toList());
        return blocks.size();
    }

    private double getOffset(Location a, Location b) {
        return a.toVector().subtract(b.toVector()).length();
    }
}