package com.areeoh.dominate.game;

import com.areeoh.core.menu.IMenu;
import com.areeoh.core.menu.MenuManager;
import com.areeoh.core.utility.UtilMessage;
import com.areeoh.dominate.game.menu.GameSelectorMenu;
import com.areeoh.dominate.teams.Team;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Game {

    private final GameManager gameManager;
    private final List<Team> teams;
    private final World world;
    private final Map<String, List<Location>> spawnPoints;
    private GameState gameState;
    private final Set<UUID> spectators;

    public final int MIN_PLAYERS = 4;
    public final int MAX_PLAYERS = 16;

    public Game(GameManager gameManager, World world) {
        this.gameManager = gameManager;
        this.teams = new ArrayList<>();
        this.world = world;
        this.spawnPoints = new HashMap<>();
        this.gameState = GameState.LOBBY;
        this.spectators = new HashSet<>();
    }

    public void startGame() {
        setGameState(Game.GameState.IN_PROGRESS);
        for (UUID uuid : getPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) {
                continue;
            }
            player.setGameMode(GameMode.ADVENTURE);
        }
    }

    public void finishGame() {
    }

    public void addPlayer(Player player) {
        getLeastFilledTeam().getPlayers().add(player.getUniqueId());

        for (IMenu menu : getGameManager().getManager(MenuManager.class).getMenus()) {
            if (menu instanceof GameSelectorMenu) {
                menu.BuildPage();
                menu.Construct();
            }
        }
        player.teleport(new Location(Bukkit.getWorld("waitingroom"), 0, 102.5, 0));
    }

    public boolean isSpectating(Player player) {
        return isSpectating(player.getUniqueId());
    }

    public boolean isSpectating(UUID uuid) {
        return getSpectators().contains(uuid);
    }

    public Set<UUID> getSpectators() {
        return spectators;
    }

    public World getWorld() {
        return world;
    }

    public List<Team> getTeams() {
        return teams;
    }

    protected void addTeam(Team team) {
        this.getTeams().add(team);
    }

    public Team getRandomTeam() {
        return getTeams().get(ThreadLocalRandom.current().nextInt(0, getTeams().size() - 1));
    }

    public Team getTeam(byte color) {
        for (Team team : getTeams()) {
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
        for (Team team : getTeams()) {
            if (team.getPlayers().contains(uuid)) {
                return team;
            }
        }
        return null;
    }

    public Team getLeastFilledTeam() {
        Team team = getTeams().get(0);
        for (Team t : getTeams()) {
            if (t.getPlayers().size() < team.getPlayers().size()) {
                team = t;
            }
        }
        return team;
    }

    public Map<String, List<Location>> getSpawnPoints() {
        return spawnPoints;
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

    public Set<UUID> getPlayers() {
        Set<UUID> players = new HashSet<>();
        for (Team team : getTeams()) {
            players.addAll(team.getPlayers());
        }
        return players;
    }

    public enum GameState {
        LOBBY,
        IN_PROGRESS;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
