package com.areeoh.dominate.game.dominate;

import com.areeoh.champions.role.RoleManager;
import com.areeoh.champions.role.roles.Assassin;
import com.areeoh.champions.role.roles.Ranger;
import com.areeoh.core.utility.UtilItem;
import com.areeoh.dominate.game.Game;
import com.areeoh.dominate.game.GameManager;
import com.areeoh.dominate.game.capturepoint.CapturePoint;
import com.areeoh.dominate.game.data.CustomItem;
import com.areeoh.dominate.teams.Team;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class DominateGame extends Game {

    private final List<CapturePoint> capturePoints;
    private final Set<CustomItem<GameManager>> customItems;
    private final DominateMap dominateMap;
    private final Map<ArmorStand, RoleManager> roleManagerMap;

    public DominateGame(World world, DominateMap dominateMap, GameManager gameManager) {
        super(gameManager, world);

        this.dominateMap = dominateMap;
        this.capturePoints = new ArrayList<>();
        this.customItems = new HashSet<>();
        this.roleManagerMap = new HashMap<>();

        addTeam(new Team("Red", ChatColor.RED, Color.RED, DyeColor.RED, Material.REDSTONE_BLOCK.getId()));
        addTeam(new Team("Blue", ChatColor.AQUA, Color.BLUE, DyeColor.BLUE, Material.LAPIS_BLOCK.getId()));
    }

    @Override
    public void startGame() {
        super.startGame();

        for (Team team : getTeams()) {
            for (UUID uuid : team.getPlayers()) {
                final Player player = Bukkit.getPlayer(uuid);
                if (player != null) {
                    player.teleport(getFirstSpawnPoint(team));
                    List<RoleManager> managers = new ArrayList<>(getGameManager().getPlugin().getManagers(RoleManager.class));
                    Collections.shuffle(managers);
                    for (RoleManager roleManager : managers) {
                        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
                        player.getInventory().clear();

                        managers.forEach(role -> role.getUsers().remove(player.getUniqueId()));

                        player.getInventory().setHelmet(UtilItem.updateNames(new ItemStack(roleManager.getPieces()[0])));
                        player.getInventory().setChestplate(UtilItem.updateNames(new ItemStack(roleManager.getPieces()[1])));
                        player.getInventory().setLeggings(UtilItem.updateNames(new ItemStack(roleManager.getPieces()[2])));
                        player.getInventory().setBoots(UtilItem.updateNames(new ItemStack(roleManager.getPieces()[3])));

                        player.getInventory().setItem(0, UtilItem.updateNames(new ItemStack(Material.IRON_SWORD)));
                        player.getInventory().setItem(1, UtilItem.updateNames(new ItemStack(Material.IRON_AXE)));
                        if (roleManager instanceof Assassin || roleManager instanceof Ranger) {
                            player.getInventory().setItem(2, UtilItem.updateNames(new ItemStack(Material.BOW)));
                            player.getInventory().setItem(29, UtilItem.updateNames(new ItemStack(Material.ARROW, 16)));
                        }
                        for (int i = 0; i < 3; i++) {
                            player.getInventory().setItem(((roleManager instanceof Assassin || roleManager instanceof Ranger) ? 3 : 2) + i, UtilItem.updateNames(new ItemStack(Material.MUSHROOM_SOUP)));
                        }
                        roleManager.doEquip(player);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void finishGame() {
        super.finishGame();
    }

    protected void addCustomItem(CustomItem<GameManager> customItem) {
        getCustomItems().add(customItem);
    }

    public void addCapturePoint(CapturePoint capturePoint) {
        this.capturePoints.add(capturePoint);
    }

    public CapturePoint getCapturePoint(Location location) {
        for (CapturePoint capturePoint : capturePoints) {
            if (capturePoint.getLocation().equals(location)) {
                return capturePoint;
            }
        }
        return null;
    }

    protected void addArmorStand(Location spawnLoc, RoleManager roleManager) {
        ArmorStand armorStand = (ArmorStand) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.ARMOR_STAND);
        armorStand.setVisible(true);
        armorStand.setBasePlate(false);

        armorStand.setHelmet(new ItemStack(roleManager.getPieces()[0]));
        armorStand.setChestplate(new ItemStack(roleManager.getPieces()[1]));
        armorStand.setLeggings(new ItemStack(roleManager.getPieces()[2]));
        armorStand.setBoots(new ItemStack(roleManager.getPieces()[3]));

        roleManagerMap.put(armorStand, roleManager);
    }

    public List<CapturePoint> getCapturePoints() {
        return capturePoints;
    }

    public Set<CustomItem<GameManager>> getCustomItems() {
        return customItems;
    }

    public DominateMap getDominateMap() {
        return dominateMap;
    }

    public Map<ArmorStand, RoleManager> getRoleManagerMap() {
        return roleManagerMap;
    }
}