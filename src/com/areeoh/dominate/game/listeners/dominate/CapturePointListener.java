package com.areeoh.dominate.game.listeners.dominate;

import com.areeoh.core.framework.Module;
import com.areeoh.core.framework.updater.Update;
import com.areeoh.core.framework.updater.Updater;
import com.areeoh.core.scoreboard.ScoreboardManager;
import com.areeoh.core.utility.UtilTitle;
import com.areeoh.dominate.game.events.DominateCaptureEvent;
import com.areeoh.dominate.teams.Team;
import com.areeoh.dominate.game.dominate.DominateGame;
import com.areeoh.dominate.game.GameManager;
import com.areeoh.dominate.game.dominate.DominateScoreboard;
import com.areeoh.dominate.game.capturepoint.CapturePoint;
import com.areeoh.dominate.utility.InstantFirework;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CapturePointListener extends Module<GameManager> implements Listener, Updater {

    public CapturePointListener(GameManager manager) {
        super(manager, "CapturePointHandler");
    }

    @Update(ticks = 10)
    public void onMessageUpdate() {
        for (DominateGame dominateGame : getManager(GameManager.class).getGames(DominateGame.class)) {
            for (CapturePoint capturePoint : dominateGame.getCapturePoints()) {
                if (capturePoint.getTeam() != null) {
                    capturePoint.getTeam().addScore(4);
                    if (capturePoint.getTeam().getScore() >= 15000) {
                        dominateGame.finishGame();
                    }
                }
            }
            for (UUID uuid : dominateGame.getPlayers()) {
                Player player = Bukkit.getPlayer(uuid);
                if(player == null) {
                    continue;
                }
                getManager(ScoreboardManager.class).getModule(DominateScoreboard.class).giveNewScoreboard(player);
            }
        }
    }

    @Update(ticks = 10)
    public void onUpdate() {
        for (DominateGame dominateGame : getManager(GameManager.class).getGames(DominateGame.class)) {
            for (CapturePoint capturePoint : dominateGame.getCapturePoints()) {
                final Set<Team> teams = capturePoint.getNearbyPlayers().stream().filter(player -> !dominateGame.isSpectating(player)).map(dominateGame::getTeam).filter(Objects::nonNull).collect(Collectors.toSet());
                if (teams.isEmpty() && capturePoint.getTeam() == null) {
                    handleBlocks(dominateGame, capturePoint, DyeColor.WHITE, null, block -> block.getType() == Material.STAINED_GLASS && block.getData() != DyeColor.WHITE.getData());
                    continue;
                }
                if (teams.size() != 1) {
                    continue;
                }
                final Team team = teams.stream().findFirst().get();

                if (capturePoint.getTeam() != null && !capturePoint.getTeam().equals(team)) {
                    handleBlocks(dominateGame, capturePoint, DyeColor.WHITE, null, block -> block.getType() == Material.STAINED_GLASS && block.getData() != DyeColor.WHITE.getData());
                    continue;
                }
                handleBlocks(dominateGame, capturePoint, team.getDyeColor(), team, block -> block.getType() == Material.STAINED_GLASS && block.getData() != team.getDyeColor().getData());
            }
        }
    }

    public void BlockBreak(Location location, float offsetX, float offsetY, float offsetZ, int blockID) {
        for (Player player : location.getWorld().getPlayers())
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.a(37), true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), offsetX, offsetY, offsetZ, 5, 80, blockID));
    }

    @EventHandler
    public void onCapturePoint(DominateCaptureEvent event) {
        final CapturePoint capturePoint = event.getCapturePoint();
        if (capturePoint.getTeam() != null) {
            InstantFirework.spawn(capturePoint.getRealLocation().add(0, 15, 0), FireworkEffect.builder().withColor(new Color[]{capturePoint.getTeam().getColor()}).with(FireworkEffect.Type.BALL_LARGE).trail(false), new Player[0]);
            for (Team t : event.getDominateGame().getTeams()) {
                for (UUID uuid : t.getPlayers()) {
                    final Player player = Bukkit.getPlayer(uuid);
                    if (player == null) {
                        continue;
                    }
                    getManager(ScoreboardManager.class).getModule(DominateScoreboard.class).giveNewScoreboard(player);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
                }
            }
            for (UUID uuid : event.getDominateGame().getPlayers()) {
                Player online = Bukkit.getPlayer(uuid);
                if(online == null) continue;
                UtilTitle.sendTitle(online, "", capturePoint.getTeam().getChatColor() + capturePoint.getTeam().getName() + " captured " + capturePoint.getName(), 20, 20, 20);
            }
        }
    }

    private void handleBlocks(DominateGame dominateGame, CapturePoint capturePoint, DyeColor dyeColor, Team team, Predicate<Block> predicate) {
        final List<Block> blocks = capturePoint.getCuboid().getBlocks().stream().filter(predicate).filter(block -> !capturePoint.getRealLocation().add(0, -1, 0).getBlock().equals(block)).collect(Collectors.toList());

        if (blocks.isEmpty()) {
            if (capturePoint.getTeam() != team) {
                capturePoint.setTeam(team);
                capturePoint.getRealLocation().add(0, -1, 0).getBlock().setData(dyeColor.getData());
                Bukkit.getServer().getPluginManager().callEvent(new DominateCaptureEvent(capturePoint, dominateGame));
                for (Block block : capturePoint.getCuboid()) {
                    if (!block.getRelative(BlockFace.UP).getType().name().contains("GLASS")) {
                        if (block.getType() == Material.GLASS || block.getType() == Material.WOOL) {
                            block.setData(dyeColor.getWoolData());
                        }
                    }
                }
            }
            return;
        }
        final Block block = blocks.get(ThreadLocalRandom.current().nextInt(0, blocks.size()));
        block.setData(dyeColor.getData());
        if (block.getRelative(BlockFace.DOWN).getType() == Material.WOOL) {
            block.getRelative(BlockFace.DOWN).setData(dyeColor.getWoolData());
        }
        block.getWorld().playEffect(block.getLocation(), Effect.TILE_BREAK, 1, 1);

        for (Block cBlock : capturePoint.getCuboid()) {
            if (cBlock.getType() == Material.WOOL) {
                if (cBlock.getRelative(BlockFace.UP).getType().name().contains("GLASS")) {
                    continue;
                }
                for (int i = 0; i < 4; i++) {
                    BlockFace blockFace = BlockFace.values()[i];
                    if (cBlock.getRelative(blockFace).getType() != Material.AIR) {
                        continue;
                    }
                    BlockBreak(cBlock.getLocation().add(cBlock.getX() > 0 ? -0.5 : 0.5, 0.5, 0.5f), blockFace.getModX() / 2.0f, 0.0f, blockFace.getModZ() / 2.0f, team == null ? 35 : team.getParticleColor());
                }
                cBlock.getWorld().playSound(cBlock.getLocation(), Sound.STEP_WOOL, 1.0F, 1.0F);
            }
        }
    }
}

