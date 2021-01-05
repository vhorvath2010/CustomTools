package com.vhbob.customtools.events;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.vhbob.customtools.CustomTools;
import com.vhbob.customtools.util.ToolUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class SmeltPickEvents implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled() || !ToolUtils.enabledFlag(CustomTools.getSmeltFlag(), BukkitAdapter.adapt(e.getBlock().getLocation()))) {
            return;
        }
        if (ToolUtils.checkPick(e.getPlayer().getInventory().getItemInMainHand(), "preset-tools.smelters.lore")) {
            FileConfiguration config = CustomTools.getPlugin().getConfig();
            ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
            // Arrange new drops
            for (ItemStack item : e.getBlock().getDrops(hand)) {
                if (config.contains("preset-tools.smelters.smelts." + item.getType().toString())) {
                    Material dropType = Material.valueOf(config.getString("preset-tools.smelters.smelts." + item.getType().toString()));
                    int amt = 1;
                    // Apply fortune if needed
                    if (config.getStringList("preset-tools.smelters.fortune").contains(dropType.toString())) {
                        amt += ToolUtils.fortuneSim(hand);
                    }
                    e.getBlock().getWorld().dropItem(e.getBlock().getLocation().add(0.5, 0, 0.5), new ItemStack(dropType, amt));
                } else {
                    e.getBlock().getWorld().dropItem(e.getBlock().getLocation().add(0.5, 0, 0.5), item);
                }
            }
            e.setDropItems(false);
        }
    }

}
