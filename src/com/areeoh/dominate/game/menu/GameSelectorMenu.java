package com.areeoh.dominate.game.menu;

import com.areeoh.core.menu.Button;
import com.areeoh.core.menu.Menu;
import com.areeoh.core.menu.MenuManager;
import com.areeoh.dominate.game.dominate.DominateGame;
import com.areeoh.dominate.game.GameManager;
import com.areeoh.dominate.game.buttons.ChampionsButton;
import org.bukkit.entity.Player;

import java.util.List;

public class GameSelectorMenu extends Menu {

    public GameSelectorMenu(MenuManager menuManager, Player player) {
        super(menuManager, player, 54, "Game Selector", new Button[] { });
    }

    @Override
    public void BuildPage() {
        List<DominateGame> games = getMenuManager().getManager(GameManager.class).getGames(DominateGame.class);
        for (int i = 0; i < games.size(); i++) {
            int slot = 10 + i;
            if(slot == 17 || slot == 18) {
                slot = 19;
            }
            addButton(new ChampionsButton(slot, games.get(i)));
        }
    }
}
