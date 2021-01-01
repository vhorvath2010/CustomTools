package com.vhbob.customtools.events;

import com.vhbob.customtools.util.ToolUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class InvPickEvents implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!e.isCancelled() && ToolUtils.checkPick(e.getPlayer().getInventory().getItemInMainHand(), "preset-tools.inventory.lore")) {
            Collection<ItemStack> drops = e.getBlock().getDrops(e.getPlayer().getInventory().getItemInMainHand());
            for (ItemStack drop : drops)
                e.getPlayer().getInventory().addItem(drop);
            e.getBlock().setType(Material.AIR);
        }
    }

}
