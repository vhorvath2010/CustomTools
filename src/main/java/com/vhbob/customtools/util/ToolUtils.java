package com.vhbob.customtools.util;

import com.vhbob.customtools.CustomTools;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public abstract class ToolUtils {

    public static boolean checkPick(ItemStack item, String loreLoc) {
        FileConfiguration config = CustomTools.getPlugin().getConfig();
        // Stop if item has no lore
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore())
            return false;
        // Stop if lore missing
        for (String line : config.getStringList(loreLoc))
            if (!item.getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', line)))
                return false;
        return true;
    }

    public static int fortuneSim(ItemStack item) {
        int fortune = item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
        int bonus = (int) (Math.random() * (fortune + 2)) - 1;
        if (bonus < 0)
            bonus = 0;
        return bonus;
    }

}
