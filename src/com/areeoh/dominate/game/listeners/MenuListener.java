package com.areeoh.dominate.game.listeners;

import com.areeoh.core.framework.Module;
import com.areeoh.core.menu.events.ButtonClickEvent;
import com.areeoh.core.utility.UtilMessage;
import com.areeoh.dominate.game.Game;
import com.areeoh.dominate.game.GameManager;
import com.areeoh.dominate.game.buttons.ChampionsButton;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MenuListener extends Module<GameManager> implements Listener {

    public MenuListener(GameManager manager) {
        super(manager, "MenuListener");
    }

    @EventHandler
    public void onButtonClick(ButtonClickEvent event) {
        if (!(event.getButton() instanceof ChampionsButton)) {
            return;
        }
        ChampionsButton championsButton = (ChampionsButton) event.getButton();
        if (championsButton.getDominateGame().getPlayers().size() >= championsButton.getDominateGame().MAX_PLAYERS) {
            return;
        }
        if (championsButton.getDominateGame().getGameState() != Game.GameState.LOBBY) {
            UtilMessage.message(event.getPlayer(), "Game", "That game has already started!");
            return;
        }
        championsButton.getDominateGame().addPlayer(event.getPlayer());
    }
}