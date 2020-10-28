package com.areeoh.dominate.game.data;

import com.areeoh.core.framework.Manager;
import com.areeoh.core.framework.Module;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class CustomItem<T extends Manager<Module>> {

    private Item item;
    private final T manager;

    public CustomItem(T manager, Location location, Material material) {
        this.item = location.getWorld().dropItem(location, new ItemStack(material));
        this.item.setPickupDelay(Integer.MAX_VALUE);
        this.manager = manager;
    }

    public T getManager() {
        return manager;
    }

    public abstract void onPickup(Player player);

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}