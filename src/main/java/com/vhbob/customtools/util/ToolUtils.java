package com.vhbob.customtools.util;

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionQuery;
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

    public static boolean enabledFlag(StateFlag flag, Location loc) {
        RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(loc);
        return set.testState(null, flag);
    }

}
