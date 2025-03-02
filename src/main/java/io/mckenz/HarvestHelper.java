package io.mckenz;

import io.mckenz.commands.CommandHandler;
import io.mckenz.config.ConfigManager;
import io.mckenz.listeners.CropHarvestListener;
import io.mckenz.stats.StatsManager;
import io.mckenz.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class HarvestHelper extends JavaPlugin {
    
    private ConfigManager configManager;
    private StatsManager statsManager;
    private UpdateChecker updateChecker;
    
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
        
        // Initialize update checker if enabled
        if (configManager.isUpdateCheckerEnabled()) {
            updateChecker = new UpdateChecker(this, 
                configManager.getUpdateCheckerResourceId(), 
                configManager.isUpdateCheckerNotifyAdmins());
            updateChecker.checkForUpdates();
        }
        
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
    
    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }
    
    public void reload() {
        reloadConfig();
        configManager.loadConfig();
        
        // Re-initialize update checker if settings changed
        if (configManager.isUpdateCheckerEnabled()) {
            if (updateChecker == null) {
                updateChecker = new UpdateChecker(this, 
                    configManager.getUpdateCheckerResourceId(), 
                    configManager.isUpdateCheckerNotifyAdmins());
                updateChecker.checkForUpdates();
            } else {
                // Re-check for updates with current settings
                updateChecker.checkForUpdates();
            }
        } else {
            // Disable update checker if it's disabled in config
            updateChecker = null;
        }
        
        getLogger().info("HarvestHelper configuration reloaded!");
    }
} 