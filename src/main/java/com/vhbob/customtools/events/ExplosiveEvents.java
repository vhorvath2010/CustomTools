package com.vhbob.customtools.events;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.vhbob.customtools.CustomTools;
import com.vhbob.customtools.util.ToolUtils;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class ExplosiveEvents implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled() || !ToolUtils.enabledFlag(CustomTools.getWeirdFlag(), BukkitAdapter.adapt(e.getBlock().getLocation()))) {
            return;
        }
        if (ToolUtils.checkPick(e.getPlayer().getInventory().getItemInMainHand(), "preset-tools.explosive.lore")) {
            // Loop through and break blocks
            BlockFace targetFace = getBlockFace(e.getPlayer());
            int x0, x, y0, y, z0, z;
            // Calculate X range
            if (targetFace == BlockFace.EAST || targetFace == BlockFace.WEST) {
                x0 = 0;
                x = 0;
            } else {
                x0 = -1;
                x = 1;
            }
            // Calculate Y range
            if (targetFace == BlockFace.UP || targetFace == BlockFace.DOWN) {
                y0 = 0;
                y = 0;
            } else {
                y0 = -1;
                y = 1;
            }
            // Calculate Z range
            if (targetFace == BlockFace.NORTH || targetFace == BlockFace.SOUTH) {
                z0 = 0;
                z = 0;
            } else {
                z0 = -1;
                z = 1;
            }
            for (int xd = x0; xd <= x; ++xd) {
                for (int yd = y0; yd <= y; ++yd) {
                    for (int zd = z0; zd <= z; ++zd) {
                        if (!e.getBlock().getRelative(xd, yd, zd).getDrops(e.getPlayer().getInventory().getItemInMainHand()).isEmpty()) {
                            e.getBlock().getRelative(xd, yd, zd).breakNaturally(e.getPlayer().getInventory().getItemInMainHand());
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets the BlockFace of the block the player is currently targeting.
     *
     * @param player the player's whos targeted blocks BlockFace is to be checked.
     * @return the BlockFace of the targeted block, or null if the targeted block is non-occluding.
     */
    public BlockFace getBlockFace(Player player) {
        List<Block> lastTwoTargetBlocks = player.getLastTwoTargetBlocks(null, 100);
        if (lastTwoTargetBlocks.size() != 2 || !lastTwoTargetBlocks.get(1).getType().isOccluding()) return null;
        Block targetBlock = lastTwoTargetBlocks.get(1);
        Block adjacentBlock = lastTwoTargetBlocks.get(0);
        return targetBlock.getFace(adjacentBlock);
    }


}
