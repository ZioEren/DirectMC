package me.zlex.directmc.listeners;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;

import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
import me.zlex.directmc.utils.ProjectileUtils;
public class PlayerInteractListener implements Listener{
    private final Map<String, Long> lastThrow = new HashMap<String, Long>();
    private final static Set<Material> interactables =
            new HashSet<>(Arrays.asList(
                Material.ANVIL, Material.COMMAND, Material.BED, Material.BEACON, Material.BED_BLOCK, Material.BREWING_STAND, Material.BURNING_FURNACE, Material.CAKE_BLOCK, Material.CHEST,
                Material.DIODE, Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON, Material.DISPENSER, Material.DROPPER, Material.ENCHANTMENT_TABLE, Material.ENDER_CHEST, Material.FENCE_GATE,
                Material.FENCE_GATE, Material.FURNACE, Material.HOPPER, Material.IRON_DOOR, Material.IRON_DOOR_BLOCK, Material.ITEM_FRAME, Material.LEVER, Material. REDSTONE_COMPARATOR,
                Material.REDSTONE_COMPARATOR_OFF, Material.REDSTONE_COMPARATOR_ON, Material.STONE_BUTTON, Material.TRAP_DOOR, Material.TRAPPED_CHEST,
                Material.WOODEN_DOOR, Material.WOOD_BUTTON, Material.WOOD_DOOR, Material.WORKBENCH
            ));
	@EventHandler(priority = EventPriority.MONITOR)
	public void onInteract(PlayerInteractEvent e){
		if (DirectMC.hasZeus(e.getPlayer())){
			if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
				Player p = e.getPlayer();
				World world = p.getWorld();
			    BlockIterator iter = new BlockIterator(p, 200);
			    Block lastBlock = iter.next();
			    while (iter.hasNext()){
			        lastBlock = iter.next();
			        if (lastBlock.getType() == Material.AIR){
			            continue;
			        }
			        break;
			    }
			    Block b = lastBlock;
				if (!b.getType().isSolid()){
					world.strikeLightning(b.getLocation().subtract(0, getDistance(b), 0));
				}else{
					world.strikeLightning(b.getLocation());
					world.strikeLightning(b.getLocation().add(5, 0, 5));
					world.strikeLightning(b.getLocation().add(0, 0, 5));
					world.strikeLightning(b.getLocation().add(5, 0, 5));
					world.strikeLightning(b.getLocation().add(5, 5, 5));
					world.createExplosion(b.getLocation(), 1.0F, true);
					world.createExplosion(b.getLocation().add(5, 0, 5), 1.0F, true);
					world.createExplosion(b.getLocation().add(0, 0, 5), 1.0F, true);
					world.createExplosion(b.getLocation().add(5, 0, 5), 1.0F, true);
					world.createExplosion(b.getLocation().add(5, 5, 5), 1.0F, true);
				}	
			}
		}
		if (Bukkit.getBukkitVersion().contains("1.8") || Bukkit.getBukkitVersion().contains("1.7")){
			if (DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("creative-enderpearl").getBoolean("enabled")){
				if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
					if (e.getMaterial().equals(Material.ENDER_PEARL)){
						Player p = e.getPlayer();
						if (p.getGameMode() == GameMode.CREATIVE){
							ProjectileUtils.LaunchEnderPearl(p);
						}
					}
				}
			}
		}else{
			if (!DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("creative-enderpearl").getBoolean("enabled")){
				e.setCancelled(true);
			}
		}
		if (DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("enderpearl-cooldown").getBoolean("enabled")){
	        if (e.isCancelled()) return;
	        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getItem() == null || e.getItem().getType() != Material.ENDER_PEARL){
	            return;
	        }
	        if (e.getClickedBlock() != null && !e.getPlayer().isSneaking()){
	            Material clickedMat = e.getClickedBlock().getType();
	            if (interactables.contains(clickedMat)) return;
	        }
	        Player player = e.getPlayer();
	        Long now = System.currentTimeMillis();
	        if (validthrow(player, now)){
	            /*if (!pay(player)){
	                e.setCancelled(true);
	            }else{
	                lastThrow.put(player.getName(), now);
	            }*/
	        	lastThrow.put(player.getName(), now);
	        }else{
	            e.setCancelled(true);
	        }
		}
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getItem() == null || e.getItem().getType() != Material.ENDER_PEARL){
            return;
        }
        if (!PermissionsUtils.hasPermission(e.getPlayer(), "enderpearl.use")){
            e.setCancelled(true);
            return;
        }
	}
    private boolean validthrow(Player player, long throwTime){
        if (!player.hasPermission("enderpearl.cooldown")){
            return true;
        }
        if (player.getGameMode() == GameMode.CREATIVE)
            return false;
        Long lastPlayerPearl = lastThrow.get(player.getName());
        if (lastPlayerPearl == null || (throwTime - lastPlayerPearl) >= DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("enderpearl-cooldown").getLong("cooldown")){
            return true;
        }
        DirectMC.sendMessage(player, DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("enderpearl-cooldown").getString("remaining-delay").replace("{SECONDS}", String.format("%.1f", remainingCooldown(player, throwTime))));
        return false;
    }
    private double remainingCooldown(Player player, long throwTime) {
        Long lastPlayerPearl = lastThrow.get(player.getName());
        return (DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("enderpearl-cooldown").getLong("cooldown") - (throwTime - lastPlayerPearl)) / 1000.0;
    }
    public static int getDistance(Entity e){
        Location loc = e.getLocation().clone();
        int distance = 0;
        while (!loc.getBlock().getType().isSolid()){
            loc.subtract(0, 1, 0);
            distance++;
        }
        return distance;
    }
    public static int getDistance(Block b){
        Location loc = b.getLocation().clone();
        int distance = 0;
        while (!loc.getBlock().getType().isSolid()){
            loc.subtract(0, 1, 0);
            distance++;
        }
        return distance;
    }
}