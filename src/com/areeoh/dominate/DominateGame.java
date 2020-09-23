package com.areeoh.dominate;

import com.areeoh.client.Client;
import com.areeoh.client.ClientManager;
import com.areeoh.teams.Team;
import com.areeoh.utility.UtilMessage;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class DominateGame {

    private final DominateManager dominateManager;
    private final List<Team> teams;
    private final DominateWorld dominateWorld;

    public DominateGame(DominateManager dominateManager, DominateWorld dominateWorld) {
        this.dominateManager = dominateManager;
        this.dominateWorld = dominateWorld;
        this.teams = new ArrayList<>();

        addTeam(new Team("Red", ChatColor.RED, Color.RED, DyeColor.RED, Material.REDSTONE_BLOCK.getId()));
        addTeam(new Team("Blue", ChatColor.AQUA, Color.BLUE, DyeColor.BLUE, Material.LAPIS_BLOCK.getId()));
    }

    public DominateWorld getDominateWorld() {
        return dominateWorld;
    }

    public Team getRandomTeam() {
        return teams.get(ThreadLocalRandom.current().nextInt(0, teams.size() - 1));
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public Team getTeam(byte color) {
        for (Team team : teams) {
            if (team.getDyeColor().getData() == color) {
                return team;
            }
        }
        return null;
    }

    public Team getTeam(Player player) {
        return getTeam(player.getUniqueId());
    }

    public Team getTeam(UUID uuid) {
        for (Team team : teams) {
            if (team.getPlayers().contains(uuid)) {
                return team;
            }
        }
        return null;
    }

    public Team getTeam(String name) {
        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(name)) {
                return team;
            }
        }
        return null;
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        for (Team team : getTeams()) {
            for (UUID uuid : team.getPlayers()) {
                Player player = Bukkit.getPlayer(uuid);
                if(player != null) {
                    players.add(player);
                }
            }
        }
        return players;
    }

    public List<Team> getTeams() {
        return teams;
    }
}