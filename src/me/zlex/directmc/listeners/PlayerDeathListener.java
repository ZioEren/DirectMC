package me.zlex.directmc.listeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import me.zlex.directmc.main.DirectMC;
public class PlayerDeathListener implements Listener{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onDeath(PlayerDeathEvent e){
		if (DirectMC.getCombatManager().getBoolean("remove-on-death")){
			if (DirectMC.getCombatManager().isPlayerPresent(e.getEntity())){
				DirectMC.getCombatManager().deleteCombat(e.getEntity());
			}
		}
		if (DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("keep-inventory").getBoolean("enabled")){
			if (DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("keep-inventory").getBoolean("inventory")){
				e.setKeepInventory(true);
			}
			if (DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("keep-inventory").getBoolean("exp")){
				e.setKeepLevel(true);
				e.setDroppedExp(0);
			}
		}
	}
}