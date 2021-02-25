package com.vhbob.customtools.events;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.vhbob.customtools.CustomTools;
import com.vhbob.customtools.util.ToolUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class CustomEvents implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled() || !ToolUtils.enabledFlag(CustomTools.getWeirdFlag(), BukkitAdapter.adapt(e.getBlock().getLocation()))) {
            return;
        }
        FileConfiguration config = CustomTools.getPlugin().getConfig();
        Player p = e.getPlayer();
        ItemStack hand = p.getInventory().getItemInMainHand();
        // Check for custom tool
        for (String tool : config.getConfigurationSection("custom-tools").getKeys(false)) {
            if (ToolUtils.checkPick(hand, "custom-tools." + tool + ".lore")) {
                if (config.getConfigurationSection("custom-tools." + tool + ".command-groups") == null)
                    return;
                String particle = config.getString("custom-tools." + tool + ".particle");
                String sound = config.getString("custom-tools." + tool + ".sound");
                for (String cmdGroup : config.getConfigurationSection("custom-tools." + tool + ".command-groups").getKeys(false)) {
                    // Try to run commands
                    if (Math.random() * 100 < config.getDouble("custom-tools." + tool + ".command-groups." + cmdGroup + ".chance")) {
                        // Play sounds and particles
                        if (!particle.equalsIgnoreCase("none")) {
                            p.getWorld().spawnParticle(Particle.valueOf(particle), e.getBlock().getLocation(), 3);
                        }
                        if (!sound.equalsIgnoreCase("none")) {
                            p.getWorld().playSound(e.getBlock().getLocation(), Sound.valueOf(sound), SoundCategory.RECORDS,1, 1);
                        }
                        for (String cmd : config.getStringList("custom-tools." + tool + ".command-groups." + cmdGroup + ".commands")) {
                            String placedCmd = PlaceholderAPI.setPlaceholders(p, cmd);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), placedCmd);
                        }
                    }
                }
            }
        }
    }

}
