package io.mckenz.listeners;

import io.mckenz.HarvestHelper;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CropHarvestListener implements Listener {
    
    private final HarvestHelper plugin;
    private final Map<Material, CropInfo> supportedCrops;
    
    public CropHarvestListener(HarvestHelper plugin) {
        this.plugin = plugin;
        this.supportedCrops = new HashMap<>();
        initializeCrops();
    }
    
    private void initializeCrops() {
        // Regular crops
        supportedCrops.put(Material.WHEAT, new CropInfo(Material.WHEAT_SEEDS, "wheat"));
        supportedCrops.put(Material.CARROTS, new CropInfo(Material.CARROT, "carrots"));
        supportedCrops.put(Material.POTATOES, new CropInfo(Material.POTATO, "potatoes"));
        supportedCrops.put(Material.BEETROOTS, new CropInfo(Material.BEETROOT_SEEDS, "beetroot"));
        supportedCrops.put(Material.NETHER_WART, new CropInfo(Material.NETHER_WART, "nether_wart"));
        
        // Special crops
        supportedCrops.put(Material.COCOA, new CropInfo(Material.COCOA_BEANS, "cocoa_beans"));
        supportedCrops.put(Material.SWEET_BERRY_BUSH, new CropInfo(Material.SWEET_BERRIES, "sweet_berries"));
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Check if the plugin is enabled
        if (!plugin.getConfigManager().isEnabled()) {
            return;
        }
        
        // Check if it's a right-click on a block
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) {
            return;
        }
        
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        
        // Check permission if required
        if (plugin.getConfigManager().isRequirePermission() && 
            !player.hasPermission(plugin.getConfigManager().getPermissionNode())) {
            return;
        }
        
        // Check if the block is a supported crop
        if (!supportedCrops.containsKey(block.getType())) {
            return;
        }
        
        // Check if the player is in creative mode (optional skip)
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        
        // Check tool requirements
        if (plugin.getConfigManager().isToolRequirementsEnabled()) {
            ItemStack handItem = event.getItem();
            
            if (plugin.getConfigManager().isRequireHoe()) {
                if (handItem == null || !isHoe(handItem.getType())) {
                    return;
                }
            }
        }
        
        // Handle the crop harvest
        handleCropHarvest(player, block);
        
        // Cancel the event to prevent normal interaction
        event.setCancelled(true);
    }
    
    private void handleCropHarvest(Player player, Block block) {
        Material blockType = block.getType();
        CropInfo cropInfo = supportedCrops.get(blockType);
        
        // Check if the crop is fully grown
        if (!isFullyGrown(block)) {
            return;
        }
        
        // Get drops from the crop
        Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());
        
        // Special handling for seeds (keep one seed for replanting)
        boolean seedKept = false;
        Material seedType = cropInfo.getSeedType();
        
        // Replant the crop
        if (blockType == Material.COCOA) {
            // Special handling for cocoa beans
            Directional directional = (Directional) block.getBlockData();
            BlockFace facing = directional.getFacing();
            
            // Reset the crop age
            Ageable ageable = (Ageable) block.getBlockData();
            ageable.setAge(0);
            block.setBlockData(ageable);
            
            // Restore the facing direction
            Directional newDirectional = (Directional) block.getBlockData();
            newDirectional.setFacing(facing);
            block.setBlockData(newDirectional);
            
            // Remove one cocoa bean from drops for replanting
            for (ItemStack item : drops) {
                if (item.getType() == Material.COCOA_BEANS) {
                    if (item.getAmount() > 1) {
                        item.setAmount(item.getAmount() - 1);
                    } else {
                        drops.remove(item);
                    }
                    seedKept = true;
                    break;
                }
            }
        } else if (blockType == Material.SWEET_BERRY_BUSH) {
            // Special handling for sweet berry bush
            Ageable ageable = (Ageable) block.getBlockData();
            ageable.setAge(1); // Set to age 1 to keep the bush but reset growth
            block.setBlockData(ageable);
            
            // No need to keep seeds for sweet berries as the bush remains
            seedKept = true;
        } else {
            // Regular crops
            // Reset the crop age
            Ageable ageable = (Ageable) block.getBlockData();
            ageable.setAge(0);
            block.setBlockData(ageable);
            
            // Remove one seed from drops for replanting
            for (ItemStack item : drops) {
                if (item.getType() == seedType) {
                    if (item.getAmount() > 1) {
                        item.setAmount(item.getAmount() - 1);
                    } else {
                        drops.remove(item);
                    }
                    seedKept = true;
                    break;
                }
            }
        }
        
        // If we couldn't find a seed in the drops, take one from the player's inventory
        if (!seedKept && blockType != Material.SWEET_BERRY_BUSH) {
            if (!player.getInventory().contains(seedType)) {
                // If the player doesn't have seeds, revert the block to air
                block.setType(Material.AIR);
                return;
            }
            
            // Remove one seed from the player's inventory
            ItemStack seedItem = new ItemStack(seedType, 1);
            player.getInventory().removeItem(seedItem);
        }
        
        // Give drops to the player
        for (ItemStack item : drops) {
            // Add items to inventory or drop them if inventory is full
            HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(item);
            for (ItemStack leftoverItem : leftover.values()) {
                player.getWorld().dropItemNaturally(player.getLocation(), leftoverItem);
            }
        }
        
        // Apply effects if enabled
        if (plugin.getConfigManager().isParticlesEnabled()) {
            player.getWorld().spawnParticle(Particle.COMPOSTER, 
                    block.getLocation().add(0.5, 0.5, 0.5), 10, 0.3, 0.3, 0.3, 0.1);
        }
        
        if (plugin.getConfigManager().isSoundsEnabled()) {
            player.getWorld().playSound(block.getLocation(), Sound.ITEM_CROP_PLANT, 1.0f, 1.0f);
        }
        
        // Track statistics if enabled
        if (plugin.getConfigManager().isTrackStatistics() && plugin.getStatsManager() != null) {
            plugin.getStatsManager().incrementCropHarvest(player, cropInfo.getStatName());
        }
    }
    
    private boolean isFullyGrown(Block block) {
        BlockData blockData = block.getBlockData();
        
        if (blockData instanceof Ageable) {
            Ageable ageable = (Ageable) blockData;
            return ageable.getAge() == ageable.getMaximumAge();
        }
        
        return false;
    }
    
    private boolean isHoe(Material material) {
        return material == Material.WOODEN_HOE || 
               material == Material.STONE_HOE || 
               material == Material.IRON_HOE || 
               material == Material.GOLDEN_HOE || 
               material == Material.DIAMOND_HOE ||
               material == Material.NETHERITE_HOE;
    }
    
    private static class CropInfo {
        private final Material seedType;
        private final String statName;
        
        public CropInfo(Material seedType, String statName) {
            this.seedType = seedType;
            this.statName = statName;
        }
        
        public Material getSeedType() {
            return seedType;
        }
        
        public String getStatName() {
            return statName;
        }
    }
} 