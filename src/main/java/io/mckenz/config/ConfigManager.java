package io.mckenz.config;

import io.mckenz.HarvestHelper;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    
    private final HarvestHelper plugin;
    private boolean enabled;
    private boolean toolRequirementsEnabled;
    private boolean requireHoe;
    private boolean supportFortune;
    private boolean particlesEnabled;
    private boolean soundsEnabled;
    private boolean requirePermission;
    private String permissionNode;
    private boolean trackStatistics;
    private boolean dropItemsOnGround;
    private boolean debug;
    // Update checker settings
    private boolean updateCheckerEnabled;
    private int updateCheckerResourceId;
    private boolean updateCheckerNotifyAdmins;
    
    public ConfigManager(HarvestHelper plugin) {
        this.plugin = plugin;
    }
    
    public void loadConfig() {
        FileConfiguration config = plugin.getConfig();
        
        // Load configuration values
        enabled = config.getBoolean("enabled", true);
        debug = config.getBoolean("debug", false);
        
        // Tool requirements
        toolRequirementsEnabled = config.getBoolean("tool-requirements.enabled", true);
        requireHoe = config.getBoolean("tool-requirements.require-hoe", false);
        supportFortune = config.getBoolean("tool-requirements.support-fortune", false);
        
        // Effects
        particlesEnabled = config.getBoolean("effects.particles", true);
        soundsEnabled = config.getBoolean("effects.sounds", true);
        
        // Permissions
        requirePermission = config.getBoolean("permissions.require-permission", false);
        permissionNode = config.getString("permissions.permission-node", "harvesthelper.use");
        
        // Statistics
        trackStatistics = config.getBoolean("statistics.enabled", true);
        
        // Drops
        dropItemsOnGround = config.getBoolean("drops.drop-on-ground", false);
        
        // Update checker
        updateCheckerEnabled = config.getBoolean("update-checker.enabled", true);
        updateCheckerResourceId = config.getInt("update-checker.resource-id", 122863);
        updateCheckerNotifyAdmins = config.getBoolean("update-checker.notify-admins", true);
        
        if (debug) {
            plugin.getLogger().info("Configuration loaded:");
            plugin.getLogger().info("Enabled: " + enabled);
            plugin.getLogger().info("Debug: " + debug);
            plugin.getLogger().info("Tool Requirements Enabled: " + toolRequirementsEnabled);
            plugin.getLogger().info("Require Hoe: " + requireHoe);
            plugin.getLogger().info("Support Fortune: " + supportFortune);
            plugin.getLogger().info("Particles Enabled: " + particlesEnabled);
            plugin.getLogger().info("Sounds Enabled: " + soundsEnabled);
            plugin.getLogger().info("Require Permission: " + requirePermission);
            plugin.getLogger().info("Permission Node: " + permissionNode);
            plugin.getLogger().info("Track Statistics: " + trackStatistics);
            plugin.getLogger().info("Drop Items On Ground: " + dropItemsOnGround);
            plugin.getLogger().info("Update Checker Enabled: " + updateCheckerEnabled);
            plugin.getLogger().info("Update Checker Resource ID: " + updateCheckerResourceId);
            plugin.getLogger().info("Update Checker Notify Admins: " + updateCheckerNotifyAdmins);
        }
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        plugin.getConfig().set("enabled", enabled);
        plugin.saveConfig();
    }
    
    public boolean isToolRequirementsEnabled() {
        return toolRequirementsEnabled;
    }
    
    public boolean isRequireHoe() {
        return requireHoe;
    }

    public boolean isSupportFortune() { return supportFortune; }
    
    public boolean isParticlesEnabled() {
        return particlesEnabled;
    }
    
    public boolean isSoundsEnabled() {
        return soundsEnabled;
    }
    
    public boolean isRequirePermission() {
        return requirePermission;
    }
    
    public String getPermissionNode() {
        return permissionNode;
    }
    
    public boolean isTrackStatistics() {
        return trackStatistics;
    }
    
    public boolean isDropItemsOnGround() {
        return dropItemsOnGround;
    }
    
    public boolean isDebug() {
        return debug;
    }
    
    public boolean isUpdateCheckerEnabled() {
        return updateCheckerEnabled;
    }
    
    public int getUpdateCheckerResourceId() {
        return updateCheckerResourceId;
    }
    
    public boolean isUpdateCheckerNotifyAdmins() {
        return updateCheckerNotifyAdmins;
    }
} 