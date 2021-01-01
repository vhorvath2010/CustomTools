package com.vhbob.customtools.events;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.vhbob.customtools.CustomTools;
import com.vhbob.customtools.util.ToolUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BountifulEvents implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!ToolUtils.enabledFlag(CustomTools.getWeirdFlag(), BukkitAdapter.adapt(e.getBlock().getLocation()))) {
            return;
        }
        FileConfiguration config = CustomTools.getPlugin().getConfig();
        if (ToolUtils.checkPick(e.getPlayer().getInventory().getItemInMainHand(), "preset-tools.bountiful.lore")) {
            // Check if we broke a trigger block
            if (config.getStringList("preset-tools.bountiful.trigger").contains(e.getBlock().getType().toString())) {
                // Loop through each value block and find match in nearby area
                for (String value : config.getStringList("preset-tools.bountiful.value")) {
                    for(int x = -1; x < 2; ++x) {
                        for(int y = -1; y < 2; ++y) {
                            for(int z = -1; z < 2; ++z) {
                                if (!(x == 0 && y == 0 && z == 0)) {
                                    Block b = e.getBlock().getRelative(x, y,z);
                                    if (b != null && b.getType() != null && b.getType().toString().equalsIgnoreCase(value)) {
                                        for (ItemStack drop : b.getDrops(e.getPlayer().getInventory().getItemInMainHand())) {
                                            e.getBlock().getWorld().dropItem(e.getBlock().getLocation().add(0.5, 0, 0.5), drop);
                                        }
                                        // Chance to 'degrade'
                                        if (Math.random() * 100 < config.getDouble("preset-tools.bountiful.degrade-chance")) {
                                            b.setType(e.getBlock().getType());
                                        }
                                        e.getBlock().setType(Material.AIR);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
