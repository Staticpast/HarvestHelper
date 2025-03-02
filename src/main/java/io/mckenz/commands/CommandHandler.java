package io.mckenz.commands;

import io.mckenz.HarvestHelper;
import io.mckenz.stats.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CommandHandler implements CommandExecutor, TabCompleter {
    
    private final HarvestHelper plugin;
    
    public CommandHandler(HarvestHelper plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelpMessage(sender);
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "toggle":
                return handleToggleCommand(sender);
            case "reload":
                return handleReloadCommand(sender);
            case "stats":
                return handleStatsCommand(sender, args);
            default:
                sendHelpMessage(sender);
                return true;
        }
    }
    
    private boolean handleToggleCommand(CommandSender sender) {
        if (!sender.hasPermission("harvesthelper.admin")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }
        
        boolean newState = !plugin.getConfigManager().isEnabled();
        plugin.getConfigManager().setEnabled(newState);
        
        if (newState) {
            sender.sendMessage(ChatColor.GREEN + "HarvestHelper has been enabled!");
        } else {
            sender.sendMessage(ChatColor.RED + "HarvestHelper has been disabled!");
        }
        
        return true;
    }
    
    private boolean handleReloadCommand(CommandSender sender) {
        if (!sender.hasPermission("harvesthelper.admin")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }
        
        plugin.reload();
        sender.sendMessage(ChatColor.GREEN + "HarvestHelper configuration reloaded!");
        
        // Check for updates after reload if enabled
        if (plugin.getConfigManager().isUpdateCheckerEnabled() && plugin.getUpdateChecker() != null) {
            if (plugin.getUpdateChecker().isUpdateAvailable()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                    "&7[&aHarvestHelper&7] &7A new update is available: &bv" + plugin.getUpdateChecker().getLatestVersion()));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                    "&7[&aHarvestHelper&7] &7You are currently running: &bv" + plugin.getDescription().getVersion()));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                    "&7[&aHarvestHelper&7] &7Download it at: &bhttps://www.spigotmc.org/resources/" + 
                    plugin.getConfigManager().getUpdateCheckerResourceId()));
            } else {
                sender.sendMessage(ChatColor.GREEN + "You are running the latest version of HarvestHelper!");
            }
        }
        
        return true;
    }
    
    private boolean handleStatsCommand(CommandSender sender, String[] args) {
        if (!plugin.getConfigManager().isTrackStatistics()) {
            sender.sendMessage(ChatColor.RED + "Statistics tracking is disabled in the configuration.");
            return true;
        }
        
        if (args.length > 1 && args[1].equalsIgnoreCase("server")) {
            if (!sender.hasPermission("harvesthelper.admin")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to view server statistics.");
                return true;
            }
            
            displayServerStats(sender);
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only players can view their own statistics. Use /harvesthelper stats server for server statistics.");
                return true;
            }
            
            Player player = (Player) sender;
            displayPlayerStats(player);
        }
        
        return true;
    }
    
    private void displayPlayerStats(Player player) {
        if (plugin.getStatsManager() == null) {
            player.sendMessage(ChatColor.RED + "Statistics tracking is not available.");
            return;
        }
        
        PlayerStats stats = plugin.getStatsManager().getPlayerStats(player.getUniqueId());
        
        player.sendMessage(ChatColor.GREEN + "=== Your HarvestHelper Statistics ===");
        player.sendMessage(ChatColor.YELLOW + "Wheat harvested: " + ChatColor.WHITE + stats.getWheatHarvested());
        player.sendMessage(ChatColor.YELLOW + "Carrots harvested: " + ChatColor.WHITE + stats.getCarrotsHarvested());
        player.sendMessage(ChatColor.YELLOW + "Potatoes harvested: " + ChatColor.WHITE + stats.getPotatoesHarvested());
        player.sendMessage(ChatColor.YELLOW + "Beetroot harvested: " + ChatColor.WHITE + stats.getBeetrootHarvested());
        player.sendMessage(ChatColor.YELLOW + "Nether Wart harvested: " + ChatColor.WHITE + stats.getNetherWartHarvested());
        player.sendMessage(ChatColor.YELLOW + "Cocoa Beans harvested: " + ChatColor.WHITE + stats.getCocoaBeansHarvested());
        player.sendMessage(ChatColor.YELLOW + "Sweet Berries harvested: " + ChatColor.WHITE + stats.getSweetBerriesHarvested());
        player.sendMessage(ChatColor.GREEN + "Total harvests: " + ChatColor.WHITE + stats.getTotalHarvests());
    }
    
    private void displayServerStats(CommandSender sender) {
        if (plugin.getStatsManager() == null) {
            sender.sendMessage(ChatColor.RED + "Statistics tracking is not available.");
            return;
        }
        
        Map<UUID, PlayerStats> allStats = plugin.getStatsManager().getAllPlayerStats();
        
        int totalWheat = 0;
        int totalCarrots = 0;
        int totalPotatoes = 0;
        int totalBeetroot = 0;
        int totalNetherWart = 0;
        int totalCocoaBeans = 0;
        int totalSweetBerries = 0;
        
        for (PlayerStats stats : allStats.values()) {
            totalWheat += stats.getWheatHarvested();
            totalCarrots += stats.getCarrotsHarvested();
            totalPotatoes += stats.getPotatoesHarvested();
            totalBeetroot += stats.getBeetrootHarvested();
            totalNetherWart += stats.getNetherWartHarvested();
            totalCocoaBeans += stats.getCocoaBeansHarvested();
            totalSweetBerries += stats.getSweetBerriesHarvested();
        }
        
        int totalHarvests = totalWheat + totalCarrots + totalPotatoes + totalBeetroot + 
                            totalNetherWart + totalCocoaBeans + totalSweetBerries;
        
        sender.sendMessage(ChatColor.GREEN + "=== Server HarvestHelper Statistics ===");
        sender.sendMessage(ChatColor.YELLOW + "Wheat harvested: " + ChatColor.WHITE + totalWheat);
        sender.sendMessage(ChatColor.YELLOW + "Carrots harvested: " + ChatColor.WHITE + totalCarrots);
        sender.sendMessage(ChatColor.YELLOW + "Potatoes harvested: " + ChatColor.WHITE + totalPotatoes);
        sender.sendMessage(ChatColor.YELLOW + "Beetroot harvested: " + ChatColor.WHITE + totalBeetroot);
        sender.sendMessage(ChatColor.YELLOW + "Nether Wart harvested: " + ChatColor.WHITE + totalNetherWart);
        sender.sendMessage(ChatColor.YELLOW + "Cocoa Beans harvested: " + ChatColor.WHITE + totalCocoaBeans);
        sender.sendMessage(ChatColor.YELLOW + "Sweet Berries harvested: " + ChatColor.WHITE + totalSweetBerries);
        sender.sendMessage(ChatColor.GREEN + "Total harvests: " + ChatColor.WHITE + totalHarvests);
        sender.sendMessage(ChatColor.GREEN + "Total players: " + ChatColor.WHITE + allStats.size());
    }
    
    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "=== HarvestHelper Commands ===");
        
        // Basic commands for all users
        if (plugin.getConfigManager().isTrackStatistics()) {
            sender.sendMessage(ChatColor.YELLOW + "/harvesthelper stats" + ChatColor.WHITE + " - View your harvest statistics");
        }
        
        // Admin commands
        if (sender.hasPermission("harvesthelper.admin")) {
            sender.sendMessage(ChatColor.YELLOW + "/harvesthelper toggle" + ChatColor.WHITE + " - Enable or disable the plugin");
            sender.sendMessage(ChatColor.YELLOW + "/harvesthelper reload" + ChatColor.WHITE + " - Reload the configuration");
            
            if (plugin.getConfigManager().isTrackStatistics()) {
                sender.sendMessage(ChatColor.YELLOW + "/harvesthelper stats server" + ChatColor.WHITE + " - View server-wide statistics");
            }
            
            // Show update information if available
            if (plugin.getConfigManager().isUpdateCheckerEnabled() && 
                plugin.getUpdateChecker() != null && 
                plugin.getUpdateChecker().isUpdateAvailable()) {
                
                sender.sendMessage(ChatColor.GREEN + "=== Update Available ===");
                sender.sendMessage(ChatColor.YELLOW + "Current version: " + ChatColor.AQUA + "v" + 
                    plugin.getDescription().getVersion());
                sender.sendMessage(ChatColor.YELLOW + "Latest version: " + ChatColor.AQUA + "v" + 
                    plugin.getUpdateChecker().getLatestVersion());
                sender.sendMessage(ChatColor.YELLOW + "Download at: " + ChatColor.AQUA + 
                    "https://www.spigotmc.org/resources/" + plugin.getConfigManager().getUpdateCheckerResourceId());
            }
        }
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            List<String> commands = new ArrayList<>();
            
            if (sender.hasPermission("harvesthelper.admin")) {
                commands.add("toggle");
                commands.add("reload");
            }
            
            commands.add("stats");
            
            for (String s : commands) {
                if (s.toLowerCase().startsWith(args[0].toLowerCase())) {
                    completions.add(s);
                }
            }
            
            return completions;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("stats")) {
            if (sender.hasPermission("harvesthelper.admin")) {
                List<String> completions = new ArrayList<>();
                if ("server".startsWith(args[1].toLowerCase())) {
                    completions.add("server");
                }
                return completions;
            }
        }
        
        return new ArrayList<>();
    }
} 