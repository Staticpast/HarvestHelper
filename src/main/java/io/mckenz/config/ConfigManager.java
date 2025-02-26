package io.mckenz.config;

import io.mckenz.HarvestHelper;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    
    private final HarvestHelper plugin;
    private boolean enabled;
    private boolean toolRequirementsEnabled;
    private boolean requireHoe;
    private boolean particlesEnabled;
    private boolean soundsEnabled;
    private boolean requirePermission;
    private String permissionNode;
    private boolean trackStatistics;
    private boolean debug;
    
    public ConfigManager(HarvestHelper plugin) {
        this.plugin = plugin;
    }
    
    public void loadConfig() {
        FileConfiguration config = plugin.getConfig();
        
        // Load configuration values
        enabled = config.getBoolean("enabled", true);
        toolRequirementsEnabled = config.getBoolean("tool-requirements.enabled", true);
        requireHoe = config.getBoolean("tool-requirements.require-hoe", false);
        particlesEnabled = config.getBoolean("effects.particles", true);
        soundsEnabled = config.getBoolean("effects.sounds", true);
        requirePermission = config.getBoolean("require-permission", false);
        permissionNode = config.getString("permission-node", "harvesthelper.use");
        trackStatistics = config.getBoolean("track-statistics", true);
        debug = config.getBoolean("debug", false);
        
        if (debug) {
            plugin.getLogger().info("Configuration loaded:");
            plugin.getLogger().info("Enabled: " + enabled);
            plugin.getLogger().info("Tool Requirements Enabled: " + toolRequirementsEnabled);
            plugin.getLogger().info("Require Hoe: " + requireHoe);
            plugin.getLogger().info("Particles Enabled: " + particlesEnabled);
            plugin.getLogger().info("Sounds Enabled: " + soundsEnabled);
            plugin.getLogger().info("Require Permission: " + requirePermission);
            plugin.getLogger().info("Permission Node: " + permissionNode);
            plugin.getLogger().info("Track Statistics: " + trackStatistics);
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
    
    public boolean isDebug() {
        return debug;
    }
} 