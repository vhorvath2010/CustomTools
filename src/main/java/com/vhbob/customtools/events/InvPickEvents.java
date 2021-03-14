package com.vhbob.customtools.events;

import com.vhbob.customtools.util.ToolUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import org.bouncycastle.util.Arrays;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class InvPickEvents implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent e) {
        if (!e.isCancelled() && ToolUtils.checkPick(e.getPlayer().getInventory().getItemInMainHand(), "preset-tools.inventory.lore")) {
            Collection<ItemStack> drops = e.getBlock().getDrops(e.getPlayer().getInventory().getItemInMainHand());
            ArrayList<ItemStack> notAdded = new ArrayList<ItemStack>();
            for (ItemStack item : drops) {
                HashMap<Integer, ItemStack> items = e.getPlayer().getInventory().addItem(item);
                if (items.size() > 0) {
                    notAdded.addAll(items.values());
                }
            }
            e.getBlock().setType(Material.AIR);
            for (ItemStack itemStack : notAdded) {
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), itemStack);
            }
        }
    }

}
