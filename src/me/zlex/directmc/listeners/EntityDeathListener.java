package me.zlex.directmc.listeners;
import java.util.HashMap;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import me.zlex.directmc.main.DirectMC;
public class EntityDeathListener implements Listener{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDeath(EntityDeathEvent event){
		LivingEntity e = (LivingEntity) event.getEntity();
		if(e.getLastDamageCause() instanceof EntityDamageByEntityEvent){
			EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) e.getLastDamageCause();
			if(nEvent.getDamager() instanceof Player){
				Player p = (Player) nEvent.getDamager();
				if(!DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("auto-pickup").getBoolean("enabled") || !DirectMC.getAutoPickups().contains(p) || !DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("auto-pickup").getBoolean("entities"))
					return;
				for (ItemStack item : event.getDrops()){
					HashMap<Integer, ItemStack> leftOver = p.getInventory().addItem(item);
					for(ItemStack leftoverItem : leftOver.values())
						p.getWorld().dropItem(p.getLocation(),leftoverItem);
				}
		        event.getDrops().clear();
			}
		}
	}
}