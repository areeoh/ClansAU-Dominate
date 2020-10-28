package com.areeoh.dominate.game.data;

import com.areeoh.core.scoreboard.ScoreboardManager;
import com.areeoh.dominate.game.dominate.DominateScoreboard;
import com.areeoh.dominate.game.GameManager;
import com.areeoh.dominate.game.Game;
import com.areeoh.dominate.utility.InstantFirework;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PointPickup extends CustomItem<GameManager> {

    public PointPickup(GameManager manager, Location location) {
        super(manager, location, Material.EMERALD);
    }

    @Override
    public void onPickup(Player player) {
        Game game = getManager().getGame(player);
        if(game == null) {
            return;
        }
        game.getTeam(player.getUniqueId()).addScore(300);
        InstantFirework.spawn(getItem().getLocation(), FireworkEffect.builder().withColor(new Color[]{game.getTeam(player.getUniqueId()).getColor()}).with(FireworkEffect.Type.BALL_LARGE).trail(false));

        for (UUID uuid : game.getPlayers()) {
            Player online = Bukkit.getPlayer(uuid);
            if(online == null) continue;
            getManager().getManager(ScoreboardManager.class).getModule(DominateScoreboard.class).giveNewScoreboard(online);
        }
        player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "You scored 300 Points for your team!");
    }
}
