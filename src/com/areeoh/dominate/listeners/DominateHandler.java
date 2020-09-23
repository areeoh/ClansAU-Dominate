package com.areeoh.dominate.listeners;

import com.areeoh.client.Client;
import com.areeoh.client.ClientManager;
import com.areeoh.dominate.DominateManager;
import com.areeoh.dominate.events.DominateStartEvent;
import com.areeoh.framework.Module;
import com.areeoh.teams.Team;
import com.areeoh.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DominateHandler extends Module<DominateManager> implements Listener {

    public DominateHandler(DominateManager manager) {
        super(manager, "DominateHandler");
    }

    @EventHandler
    public void onDominateGameStart(DominateStartEvent event) {
        for (Player player : event.getDominateGame().getPlayers()) {
            player.setGameMode(GameMode.ADVENTURE);
        }
    }

    @EventHandler
    public void handleTeams(DominateStartEvent event) {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        Collections.shuffle(players);

        for (Team team : event.getDominateGame().getTeams()) {
            for (int i = players.size() / event.getDominateGame().getTeams().size() * (event.getDominateGame().getTeams().indexOf(team)); i < (players.size() / event.getDominateGame().getTeams().size()) * (event.getDominateGame().getTeams().indexOf(team) + 1); i++) {
                final Player player = players.get(i);
                team.getPlayers().add(player.getUniqueId());
            }
        }
        for (Player player : players) {
            if (event.getDominateGame().getTeam(player) == null) {
                event.getDominateGame().getRandomTeam().getPlayers().add(player.getUniqueId());
            }
        }
        for (Team team : event.getDominateGame().getTeams()) {
            for (UUID uuid : team.getPlayers()) {
                final Client client = getManager(ClientManager.class).getClient(uuid);
                UtilMessage.broadcast("Dominate", "Added " + ChatColor.YELLOW + client.getName() + ChatColor.GRAY + " to " + team.getTag(true) + ChatColor.GRAY + ".");
                final Player player = client.getPlayer();
                if (player != null) {
                    player.teleport(event.getDominateGame().getDominateWorld().getFirstSpawnPoint(team));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().setGameMode(GameMode.ADVENTURE);
    }
}
