package io.mckenz;

import io.mckenz.commands.CommandHandler;
import io.mckenz.config.ConfigManager;
import io.mckenz.listeners.CropHarvestListener;
import io.mckenz.stats.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class HarvestHelper extends JavaPlugin {
    
    private ConfigManager configManager;
    private StatsManager statsManager;
    
    @Override
    public void onEnable() {
        // Initialize config
        saveDefaultConfig();
        configManager = new ConfigManager(this);
        configManager.loadConfig();
        
        // Initialize stats manager if enabled
        if (configManager.isTrackStatistics()) {
            statsManager = new StatsManager(this);
        }
        
        // Register event listeners
        getServer().getPluginManager().registerEvents(new CropHarvestListener(this), this);
        
        // Register commands
        getCommand("harvesthelper").setExecutor(new CommandHandler(this));
        
        getLogger().info("HarvestHelper has been enabled!");
    }
    
    @Override
    public void onDisable() {
        // Save stats if enabled
        if (configManager.isTrackStatistics() && statsManager != null) {
            statsManager.saveStats();
        }
        
        getLogger().info("HarvestHelper has been disabled!");
    }
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    public StatsManager getStatsManager() {
        return statsManager;
    }
    
    public void reload() {
        reloadConfig();
        configManager.loadConfig();
        getLogger().info("HarvestHelper configuration reloaded!");
    }
} 