package com.areeoh.dominate;

import com.areeoh.client.Client;
import com.areeoh.client.ClientManager;
import com.areeoh.utility.UtilMessage;
import com.areeoh.teams.Team;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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

        setupGame();
    }

    public DominateWorld getDominateWorld() {
        return dominateWorld;
    }

    public void setupGame() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        Collections.shuffle(players);

        for (Team team : getTeams()) {
            for (int i = players.size() / getTeams().size() * (getTeams().indexOf(team)); i < (players.size() / getTeams().size()) * (getTeams().indexOf(team) + 1); i++) {
                final Player player = players.get(i);
                team.getPlayers().add(player.getUniqueId());
            }
        }
        for (Player player : players) {
            if (getTeam(player) == null) {
                getRandomTeam().getPlayers().add(player.getUniqueId());
            }
        }
        for (Team team : getTeams()) {
            for (UUID uuid : team.getPlayers()) {
                final Client client = dominateManager.getManager(ClientManager.class).getClient(uuid);
                UtilMessage.broadcast("Dominate", "Added " + ChatColor.YELLOW + client.getName() + ChatColor.GRAY + " to " + team.getTag(true) + ChatColor.GRAY + ".");
                final Player player = client.getPlayer();
                if(player != null) {
                    player.teleport(getDominateWorld().getFirstSpawnPoint(team));
                }
            }
        }
    }

    public Team getRandomTeam() {
        return teams.get(ThreadLocalRandom.current().nextInt(0, teams.size() - 1));
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public Team getTeam(byte color) {
        for (Team team : teams) {
            if(team.getDyeColor().getData() == color) {
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
            if(team.getPlayers().contains(uuid)) {
                return team;
            }
        }
        return null;
    }

    public Team getTeam(String name) {
        for (Team team : teams) {
            if(team.getName().equalsIgnoreCase(name)) {
                return team;
            }
        }
        return null;
    }

    public List<Team> getTeams() {
        return teams;
    }
}