package me.zlex.directmc.listeners;
import java.util.HashMap;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.zlex.directmc.main.DirectMC;
public class BlockBreakListener implements Listener{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event){	
		if(event.isCancelled() || !DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("auto-pickup").getBoolean("enabled") || !DirectMC.getAutoPickups().contains(event.getPlayer()) || !DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("auto-pickup").getBoolean("blocks"))
			return;
		Player player = event.getPlayer();
		Block block = event.getBlock();	
		if(player.getGameMode() == GameMode.CREATIVE)
			return;
		for (ItemStack item : block.getDrops(player.getInventory().getItemInHand())){
			HashMap<Integer, ItemStack> leftOver = player.getInventory().addItem(item);
			for(ItemStack leftoverItem : leftOver.values())
				player.getWorld().dropItem(player.getLocation(),leftoverItem);
		}
		event.setCancelled(true);
		block.setType(Material.AIR);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void onSpawnerBreak(BlockBreakEvent e){
		if (DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("mobspawner-silktouch").getBoolean("enabled")){
			Block s = e.getBlock();
			if (e.getPlayer().getInventory().getItemInHand().getType().equals(Material.DIAMOND_PICKAXE) && e.getPlayer()
					.getInventory().getItemInHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
				if (s.getType().equals(Material.MOB_SPAWNER)) {
					CreatureSpawner cs = (CreatureSpawner) s.getState();
					ItemStack spawner = new ItemStack(Material.MOB_SPAWNER, 1);
					ItemMeta swmeta = spawner.getItemMeta();
					swmeta.setDisplayName(DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("mobspawner-silktouch").getString("mobspawner-name").replace("{TYPE}", cs.getSpawnedType().name()).replace("&", "§"));
					e.setExpToDrop(0);
					spawner.setItemMeta(swmeta);
					s.getWorld().dropItemNaturally(s.getLocation(), spawner);
				}
			}
		}
	}
}