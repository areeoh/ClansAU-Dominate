package com.areeoh.dominate.game.buttons;

import com.areeoh.core.menu.Button;
import com.areeoh.core.utility.UtilFormat;
import com.areeoh.dominate.game.dominate.DominateGame;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ChampionsButton extends Button {

    private final DominateGame dominateGame;

    public ChampionsButton(int slot, DominateGame dominateGame) {
        super(slot, new ItemStack(Material.PAPER), 1, (byte) 0, ChatColor.GREEN + ChatColor.BOLD.toString() + UtilFormat.cleanString(dominateGame.getDominateMap().getWorldName()), new String[] {
                "",
                ChatColor.YELLOW + "Map: " + ChatColor.WHITE + UtilFormat.cleanString(dominateGame.getDominateMap().getWorldName()),
                ChatColor.YELLOW + "Players: " + ChatColor.WHITE + dominateGame.getPlayers().size() + "/" + "16",
                "",
                ChatColor.YELLOW + "Status: " + ChatColor.WHITE + UtilFormat.cleanString(dominateGame.getGameState().name()),
                "",
                ChatColor.YELLOW + "Mode: " + ChatColor.WHITE + "Champions Dominate"
        }, dominateGame != null);
        this.dominateGame = dominateGame;
    }

    public DominateGame getDominateGame() {
        return dominateGame;
    }
}