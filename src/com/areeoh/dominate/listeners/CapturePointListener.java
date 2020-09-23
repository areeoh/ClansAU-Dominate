package com.areeoh.dominate.listeners;

import com.areeoh.dominate.DominateGame;
import com.areeoh.dominate.DominateManager;
import com.areeoh.dominate.DominateScoreboard;
import com.areeoh.dominate.capturepoint.CapturePoint;
import com.areeoh.dominate.events.DominateCaptureEvent;
import com.areeoh.framework.Module;
import com.areeoh.framework.updater.Update;
import com.areeoh.framework.updater.Updater;
import com.areeoh.scoreboard.ScoreboardManager;
import com.areeoh.teams.Team;
import com.areeoh.utility.InstantFirework;
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
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CapturePointListener extends Module<DominateManager> implements Listener, Updater {

    public CapturePointListener(DominateManager manager) {
        super(manager, "CapturePointHandler");
    }

    @Update(ticks = 10)
    public void onMessageUpdate() {
        final DominateGame dominateGame = getManager().getDominateGame();
        if (dominateGame == null) {
            return;
        }
        for (CapturePoint capturePoint : dominateGame.getDominateWorld().getCapturePoints()) {
            if (capturePoint.getTeam() != null) {
                capturePoint.getTeam().addScore(4);
                if (capturePoint.getTeam().getScore() >= 15000) {
                    finishGame();
                }
            }
        }
        for (Player online : Bukkit.getOnlinePlayers()) {
            getManager(ScoreboardManager.class).getModule(DominateScoreboard.class).giveNewScoreboard(online);
        }
    }

    public Player getPlayer() {
        return Bukkit.getOnlinePlayers().stream().findFirst().orElse(null);
    }

    @Update(ticks = 10)
    public void onUpdate() {
        final DominateGame dominateGame = getManager().getDominateGame();
        if (dominateGame == null) {
            return;
        }
        for (CapturePoint capturePoint : dominateGame.getDominateWorld().getCapturePoints()) {
            final List<Team> teams = capturePoint.getNearbyPlayers().stream().map(player -> getManager().getDominateGame().getTeam(player)).filter(Objects::nonNull).collect(Collectors.toList());
            if (teams.isEmpty() && capturePoint.getTeam() == null) {
                handleBlocks(capturePoint, DyeColor.WHITE, null, block -> block.getType() == Material.STAINED_GLASS && block.getData() != DyeColor.WHITE.getData());
                continue;
            }
            if (teams.size() != 1) {
                continue;
            }
            final Team team = teams.get(0);

            if (capturePoint.getTeam() != null && !capturePoint.getTeam().equals(team)) {
                handleBlocks(capturePoint, DyeColor.WHITE, null, block -> block.getType() == Material.STAINED_GLASS && block.getData() != DyeColor.WHITE.getData());
                continue;
            }
            handleBlocks(capturePoint, team.getDyeColor(), team, block -> block.getType() == Material.STAINED_GLASS && block.getData() != team.getDyeColor().getData());
        }
    }

    private void finishGame() {
    }

    public void BlockBreak(Location location, float offsetX, float offsetY, float offsetZ, int blockID) {
        for (Player player : Bukkit.getOnlinePlayers())
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.a(37), true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), offsetX, offsetY, offsetZ, 5, 80, blockID));
    }

    @EventHandler
    public void onCapturePoint(DominateCaptureEvent event) {
        final CapturePoint capturePoint = event.getCapturePoint();
        if (capturePoint.getTeam() != null) {
            InstantFirework.spawn(capturePoint.getRealLocation().add(0, 15, 0), FireworkEffect.builder().withColor(new Color[]{capturePoint.getTeam().getColor()}).with(FireworkEffect.Type.BALL_LARGE).trail(false), new Player[0]);
            for (Team t : getManager().getDominateGame().getTeams()) {
                for (UUID uuid : t.getPlayers()) {
                    final Player player = Bukkit.getPlayer(uuid);
                    if (player == null) {
                        continue;
                    }
                    getManager(ScoreboardManager.class).getModule(DominateScoreboard.class).giveNewScoreboard(player);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
                }
            }
        }
    }

    private void handleBlocks(CapturePoint capturePoint, DyeColor dyeColor, Team team, Predicate<Block> predicate) {
        final List<Block> blocks = capturePoint.getCuboid().getBlocks().stream().filter(predicate).filter(block -> !capturePoint.getRealLocation().add(0, -1, 0).getBlock().equals(block)).collect(Collectors.toList());

        if (blocks.isEmpty()) {
            if (capturePoint.getTeam() != team) {
                capturePoint.setTeam(team);
                capturePoint.getRealLocation().add(0, -1, 0).getBlock().setData(dyeColor.getData());
                Bukkit.getServer().getPluginManager().callEvent(new DominateCaptureEvent(capturePoint));
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

