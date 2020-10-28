package com.areeoh.dominate.game.dominate;

import com.areeoh.core.scoreboard.ScoreboardManager;
import com.areeoh.core.scoreboard.ScoreboardPriority;
import com.areeoh.core.scoreboard.data.PlayerScoreboard;
import com.areeoh.dominate.game.Game;
import com.areeoh.dominate.game.GameManager;
import com.areeoh.dominate.teams.Team;
import com.areeoh.dominate.game.capturepoint.CapturePoint;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class DominateScoreboard extends PlayerScoreboard {

    public DominateScoreboard(ScoreboardManager manager) {
        super(manager, "DominateScoreboard", "ClansAU Dominate");
    }

    @Override
    public void giveNewScoreboard(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("info");
        if (objective != null) {
            objective.unregister();
        }
        objective = scoreboard.registerNewObjective("info", "dummy");

        objective.setDisplayName(getDisplayName());
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        //TODO MAKE SCOREBOARD FOR EACH GAME TYPE
        Game game = getManager(GameManager.class).getGame(player.getUniqueId());
        if (game instanceof DominateGame) {
            DominateGame dominateGame = (DominateGame) game;
            Score blank = objective.getScore(getBlank(0));
            Score info = objective.getScore(ChatColor.BOLD.toString() + "First to 15000");
            Score blank1 = objective.getScore(getBlank(1));

            blank.setScore(15);
            info.setScore(14);
            blank1.setScore(13);
            for (Team team : dominateGame.getTeams()) {
                Score tTeam = objective.getScore(team.getTag(true) + " Team");
                final int i = dominateGame.getTeams().indexOf(team);
                Score teamScore = objective.getScore(team.getScore() + getBlank(i));
                tTeam.setScore(12 - (3 * i));
                teamScore.setScore(11 - (3 * i));
                Score teamBlank = objective.getScore(getBlank(2 + i));
                teamBlank.setScore(10 - (3 * i));
            }
            for (CapturePoint capturePoint : dominateGame.getCapturePoints()) {
                Score cCapturePoint;
                cCapturePoint = objective.getScore(ChatColor.BOLD + capturePoint.getName());
                final Set<Byte> blocks = capturePoint.getCuboid().getBlocks().stream().filter(block -> block.getType() == Material.STAINED_GLASS && block.getData() != DyeColor.WHITE.getData()).filter(block -> !capturePoint.getRealLocation().add(0, -1, 0).getBlock().equals(block)).map(Block::getData).collect(Collectors.toSet());
                final List<Team> teams = blocks.stream().map(dominateGame::getTeam).collect(Collectors.toList());
                if (blocks.size() == 1) {
                    cCapturePoint = objective.getScore(createProgressBarOnText(capturePoint.getName(), teams.get(0).getChatColor(), capturePoint.getProgress(), capturePoint.getName().length()));
                }
                cCapturePoint.setScore(6 - dominateGame.getCapturePoints().indexOf(capturePoint));
            }
        }
        player.setScoreboard(scoreboard);

        addPlayer(player);
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

    @Override
    public ScoreboardPriority getScoreboardPriority() {
        return ScoreboardPriority.LOW;
    }

    private void addPlayer(Player player) {
        Game dominateGame = getManager(GameManager.class).getGame(player);
        if (dominateGame == null) {
            return;
        }
        if(dominateGame instanceof DominateGame) {
            Team team = dominateGame.getTeam(player.getUniqueId());
            for (UUID uuid : dominateGame.getPlayers()) {
                Player online = Bukkit.getPlayer(uuid);
                if(online == null) continue;
                Scoreboard scoreboard = online.getScoreboard();
                if (scoreboard.getTeam(team.getName()) == null) {
                    scoreboard.registerNewTeam(team.getName());
                }
                scoreboard.getTeam(team.getName()).setPrefix(team.getChatColor() + "");
                scoreboard.getTeam(team.getName()).addPlayer(player);
            }
            Scoreboard scoreboard = player.getScoreboard();
            scoreboard.getTeams().forEach(org.bukkit.scoreboard.Team::unregister);
            for (UUID uuid : dominateGame.getPlayers()) {
                Player online = Bukkit.getPlayer(uuid);
                if(online == null) continue;
                Team t = dominateGame.getTeam(online.getUniqueId());
                if (t == null) {
                    continue;
                }
                if (scoreboard.getTeam(t.getName()) == null) {
                    scoreboard.registerNewTeam(t.getName());
                }
                scoreboard.getTeam(t.getName()).setPrefix(t.getChatColor() + "");
                scoreboard.getTeam(t.getName()).addPlayer(online);
            }
        }
    }
}