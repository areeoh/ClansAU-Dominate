package com.areeoh.dominate.listeners;

import com.areeoh.client.ClientManager;
import com.areeoh.framework.Module;
import com.areeoh.framework.updater.Update;
import com.areeoh.framework.updater.Updater;
import com.areeoh.scoreboard.ScoreboardManager;
import com.areeoh.dominate.DominateManager;
import com.areeoh.dominate.capturepoint.CapturePoint;
import com.areeoh.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SidebarHandler extends Module<DominateManager> implements Listener, Updater {

    private String header = "ClansAU Dominate";
    private boolean shineDirection = true;
    private int shineIndex = 0;

    public SidebarHandler(DominateManager manager) {
        super(manager, "SidebarHandler");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        updateSideBar(event.getPlayer());
    }

    public void updateSideBar(Player player) {
        final Scoreboard scoreboard = getManager(ScoreboardManager.class).getScoreboard(player);
        Objective objective = scoreboard.getObjective("info");
        if (objective != null) {
            objective.unregister();
        }
        objective = scoreboard.registerNewObjective("info", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(getHeaderName());

        if (getManager().getDominateGame() == null) {
            Score blank = objective.getScore(getBlank(0));
            Score rRank = objective.getScore(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Rank");
            Score rank = objective.getScore(getManager(ClientManager.class).getClient(player.getUniqueId()).getRank().getPrefix());
            Score blank1 = objective.getScore(getBlank(1));
            Score pPlayers = objective.getScore(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Players Online");
            Score players = objective.getScore(Bukkit.getOnlinePlayers().stream().filter(player::canSee).count() + "");
            Score blank2 = objective.getScore(getBlank(2));
            blank.setScore(15);
            rRank.setScore(14);
            rank.setScore(13);
            blank1.setScore(12);
            pPlayers.setScore(11);
            players.setScore(10);
            blank2.setScore(9);
        } else {
            Score blank = objective.getScore(getBlank(0));
            Score info = objective.getScore(ChatColor.BOLD.toString() + "First to 15000");
            Score blank1 = objective.getScore(getBlank(1));
            blank.setScore(15);
            info.setScore(14);
            blank1.setScore(13);
            for (Team team : getManager().getDominateGame().getTeams()) {
                Score tTeam = objective.getScore(team.getTag(true) + " Team");
                final int i = getManager().getDominateGame().getTeams().indexOf(team);
                Score teamScore = objective.getScore(team.getScore() + getBlank(i));
                tTeam.setScore(12 - (3 * i));
                teamScore.setScore(11 - (3 * i));
                Score teamBlank = objective.getScore(getBlank(2 + i));
                teamBlank.setScore(10 - (3 * i));
            }
            for (CapturePoint capturePoint : getManager().getDominateGame().getDominateWorld().getCapturePoints()) {
                Score cCapturePoint;
                if(capturePoint.getTeam() == null) {
                    cCapturePoint = objective.getScore(ChatColor.BOLD + capturePoint.getName());
                    final Set<Byte> blocks = capturePoint.getCuboid().getBlocks().stream().filter(block -> block.getType() == Material.STAINED_GLASS && block.getData() != DyeColor.WHITE.getData()).filter(block -> !capturePoint.getRealLocation().add(0, -1, 0).getBlock().equals(block)).map(Block::getData).collect(Collectors.toSet());
                    final List<Team> teams = blocks.stream().map(block -> getManager().getDominateGame().getTeam(block)).collect(Collectors.toList());
                    if(blocks.size() == 1) {
                        cCapturePoint = objective.getScore(createProgressBarOnText(capturePoint.getName(), teams.get(0).getChatColor(), capturePoint.getProgress(), capturePoint.getName().length()));
                    }
                } else {
                    cCapturePoint = objective.getScore((capturePoint.getTeam().getChatColor()) + ChatColor.BOLD.toString() + capturePoint.getName());
                }
                cCapturePoint.setScore(6 - getManager().getDominateGame().getDominateWorld().getCapturePoints().indexOf(capturePoint));
            }
        }
    }

    @Update(ticks = 3)
    public void onUpdate() {
        for (Scoreboard scoreboard : getManager(ScoreboardManager.class).getScoreboardMap().values()) {
            scoreboard.getObjective("info").setDisplayName(getHeaderName());
        }
        this.shineIndex += 1;
        if (this.shineIndex == this.header.length() * 2) {
            this.shineIndex = 0;
            this.shineDirection = (!this.shineDirection);
        }
    }

    private String getBlank(int amount) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    public String getHeaderName() {
        String out;
        if (this.shineDirection) {
            out = ChatColor.GOLD + ChatColor.BOLD.toString();
        } else {
            out = ChatColor.WHITE + ChatColor.BOLD.toString();
        }
        for (int i = 0; i < this.header.length(); i++) {
            char c = this.header.charAt(i);
            if (this.shineDirection) {
                if (i == this.shineIndex) {
                    out = out + ChatColor.YELLOW + ChatColor.BOLD.toString();
                }
                if (i == this.shineIndex + 1) {
                    out = out + ChatColor.WHITE + ChatColor.BOLD.toString();
                }
            } else {
                if (i == this.shineIndex) {
                    out = out + ChatColor.YELLOW + ChatColor.BOLD.toString();
                }
                if (i == this.shineIndex + 1) {
                    out = out + ChatColor.GOLD + ChatColor.BOLD.toString();
                }
            }
            out = out + c;
        }
        return out;
    }

    private String createProgressBarOnText(String text, ChatColor color, double min, double max) {
        StringBuilder stringBuilder = new StringBuilder(color + ChatColor.BOLD.toString() + "");
        int progress = (int) (min * text.length() / 24);

        for (int i = 0; i < progress; i++) {
            stringBuilder.append(text.charAt(i));
        }
        stringBuilder.append(ChatColor.WHITE).append(ChatColor.BOLD);
        for (int i = progress; i < text.length(); i++) {
            stringBuilder.append(text.charAt(i));
        }
        return stringBuilder.toString();
    }
}
