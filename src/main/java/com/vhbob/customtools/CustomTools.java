package com.vhbob.customtools;

import com.vhbob.customtools.events.InvPickEvents;
import com.vhbob.customtools.events.LumberEvents;
import com.vhbob.customtools.events.SmeltPickEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomTools extends JavaPlugin {

    private static CustomTools plugin;

    @Override
    public void onEnable() {
        // Register Events
        Bukkit.getPluginManager().registerEvents(new InvPickEvents(), this);
        Bukkit.getPluginManager().registerEvents(new SmeltPickEvents(), this);
        Bukkit.getPluginManager().registerEvents(new LumberEvents(), this);
        plugin = this;
    }

    public static CustomTools getPlugin() {
        return plugin;
    }
}
