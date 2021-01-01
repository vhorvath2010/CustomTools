package com.vhbob.customtools;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.vhbob.customtools.events.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomTools extends JavaPlugin {

    private static CustomTools plugin;
    private static StateFlag smeltFlag, lumberFlag, weirdFlag;

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

    @Override
    public void onLoad() {
        // Load WorldGuardAPI
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        smeltFlag = new StateFlag("smelters-pick", false);
        lumberFlag = new StateFlag("lumber-axe", false);
        weirdFlag = new StateFlag("weird-pick", false);
        registry.register(smeltFlag);
        registry.register(lumberFlag);
        registry.register(weirdFlag);
    }

    public static CustomTools getPlugin() {
        return plugin;
    }

    public static StateFlag getLumberFlag() {
        return lumberFlag;
    }

    public static StateFlag getSmeltFlag() {
        return smeltFlag;
    }

    public static StateFlag getWeirdFlag() {
        return weirdFlag;
    }
}
