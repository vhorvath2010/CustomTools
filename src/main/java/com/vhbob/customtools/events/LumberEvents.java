package com.vhbob.customtools.events;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.vhbob.customtools.CustomTools;
import com.vhbob.customtools.util.ToolUtils;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class LumberEvents implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled() || !ToolUtils.enabledFlag(CustomTools.getLumberFlag(), BukkitAdapter.adapt(e.getBlock().getLocation()))) {
            return;
        }
        if (ToolUtils.checkPick(e.getPlayer().getInventory().getItemInMainHand(), "preset-tools.lumber.lore")) {
            // Find blocks to remove
            int delay_inc = CustomTools.getPlugin().getConfig().getInt("preset-tools.lumber.delay");
            int delay = delay_inc;
            for(int x = -1; x < 2; ++x) {
                for(int y = -1; y < 2; ++y) {
                    for(int z = -1; z < 2; ++z) {
                        if (!(x == 0 && y == 0 && z == 0)) {
                            // Break block if valid
                            final Block b = e.getBlock().getRelative(x, y,z);
                            if (b != null && b.getType() != null) {
                                if (CustomTools.getPlugin().getConfig().getStringList("preset-tools.lumber.effective")
                                        .contains(b.getType().toString())) {
                                    new BukkitRunnable() {
                                        public void run() {
                                            if (b != null)
                                                b.breakNaturally();
                                        }
                                    }.runTaskLater(CustomTools.getPlugin(), delay);
                                    delay += delay_inc;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
