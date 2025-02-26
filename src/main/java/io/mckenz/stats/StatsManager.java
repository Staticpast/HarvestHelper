package io.mckenz.stats;

import io.mckenz.HarvestHelper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatsManager {
    
    private final HarvestHelper plugin;
    private final File statsFile;
    private FileConfiguration statsConfig;
    private final Map<UUID, PlayerStats> playerStats;
    
    public StatsManager(HarvestHelper plugin) {
        this.plugin = plugin;
        this.statsFile = new File(plugin.getDataFolder(), "stats.yml");
        this.playerStats = new HashMap<>();
        
        loadStats();
    }
    
    private void loadStats() {
        if (!statsFile.exists()) {
            try {
                statsFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create stats file: " + e.getMessage());
            }
        }
        
        statsConfig = YamlConfiguration.loadConfiguration(statsFile);
        
        // Load player stats from config
        if (statsConfig.contains("players")) {
            for (String uuidString : statsConfig.getConfigurationSection("players").getKeys(false)) {
                UUID uuid = UUID.fromString(uuidString);
                int wheatHarvested = statsConfig.getInt("players." + uuidString + ".wheat", 0);
                int carrotsHarvested = statsConfig.getInt("players." + uuidString + ".carrots", 0);
                int potatoesHarvested = statsConfig.getInt("players." + uuidString + ".potatoes", 0);
                int beetrootHarvested = statsConfig.getInt("players." + uuidString + ".beetroot", 0);
                int netherWartHarvested = statsConfig.getInt("players." + uuidString + ".nether_wart", 0);
                int cocoaBeansHarvested = statsConfig.getInt("players." + uuidString + ".cocoa_beans", 0);
                int sweetBerriesHarvested = statsConfig.getInt("players." + uuidString + ".sweet_berries", 0);
                
                PlayerStats stats = new PlayerStats();
                stats.setWheatHarvested(wheatHarvested);
                stats.setCarrotsHarvested(carrotsHarvested);
                stats.setPotatoesHarvested(potatoesHarvested);
                stats.setBeetrootHarvested(beetrootHarvested);
                stats.setNetherWartHarvested(netherWartHarvested);
                stats.setCocoaBeansHarvested(cocoaBeansHarvested);
                stats.setSweetBerriesHarvested(sweetBerriesHarvested);
                
                playerStats.put(uuid, stats);
            }
        }
    }
    
    public void saveStats() {
        if (statsConfig == null || statsFile == null) {
            return;
        }
        
        // Save player stats to config
        for (Map.Entry<UUID, PlayerStats> entry : playerStats.entrySet()) {
            UUID uuid = entry.getKey();
            PlayerStats stats = entry.getValue();
            
            statsConfig.set("players." + uuid.toString() + ".wheat", stats.getWheatHarvested());
            statsConfig.set("players." + uuid.toString() + ".carrots", stats.getCarrotsHarvested());
            statsConfig.set("players." + uuid.toString() + ".potatoes", stats.getPotatoesHarvested());
            statsConfig.set("players." + uuid.toString() + ".beetroot", stats.getBeetrootHarvested());
            statsConfig.set("players." + uuid.toString() + ".nether_wart", stats.getNetherWartHarvested());
            statsConfig.set("players." + uuid.toString() + ".cocoa_beans", stats.getCocoaBeansHarvested());
            statsConfig.set("players." + uuid.toString() + ".sweet_berries", stats.getSweetBerriesHarvested());
        }
        
        try {
            statsConfig.save(statsFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save stats file: " + e.getMessage());
        }
    }
    
    public PlayerStats getPlayerStats(UUID uuid) {
        return playerStats.computeIfAbsent(uuid, k -> new PlayerStats());
    }
    
    public void incrementCropHarvest(Player player, String cropType) {
        PlayerStats stats = getPlayerStats(player.getUniqueId());
        
        switch (cropType.toLowerCase()) {
            case "wheat":
                stats.incrementWheatHarvested();
                break;
            case "carrots":
                stats.incrementCarrotsHarvested();
                break;
            case "potatoes":
                stats.incrementPotatoesHarvested();
                break;
            case "beetroot":
                stats.incrementBeetrootHarvested();
                break;
            case "nether_wart":
                stats.incrementNetherWartHarvested();
                break;
            case "cocoa_beans":
                stats.incrementCocoaBeansHarvested();
                break;
            case "sweet_berries":
                stats.incrementSweetBerriesHarvested();
                break;
        }
        
        // Save stats periodically (every 50 harvests per player)
        int totalHarvests = stats.getTotalHarvests();
        if (totalHarvests % 50 == 0) {
            saveStats();
        }
    }
    
    public Map<UUID, PlayerStats> getAllPlayerStats() {
        return playerStats;
    }
} 