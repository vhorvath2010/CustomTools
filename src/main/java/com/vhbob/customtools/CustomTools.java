package com.vhbob.customtools;

import com.vhbob.customtools.events.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomTools extends JavaPlugin {

    private static CustomTools plugin;

    @Override
    public void onEnable() {
        // Enable PlaceholderAPI
        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            throw new RuntimeException("Could not find PlaceholderAPI!!! Plugin can not work without it!");
        }
        saveDefaultConfig();
        // Register Events
        Bukkit.getPluginManager().registerEvents(new InvPickEvents(), this);
        Bukkit.getPluginManager().registerEvents(new SmeltPickEvents(), this);
        Bukkit.getPluginManager().registerEvents(new LumberEvents(), this);
        Bukkit.getPluginManager().registerEvents(new ExplosiveEvents(), this);
        Bukkit.getPluginManager().registerEvents(new BountifulEvents(), this);
        Bukkit.getPluginManager().registerEvents(new CustomEvents(), this);
        plugin = this;
    }

    public static CustomTools getPlugin() {
        return plugin;
    }
}
