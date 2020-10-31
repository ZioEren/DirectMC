package me.zlex.directmc.listeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.managers.economy.EcoManager;
public class PlayerQuitListener implements Listener{
	@SuppressWarnings("static-access")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent e){
		if (DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("custom-messages").getBoolean("enabled")){
			e.setQuitMessage(DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("custom-messages").getString("player-left").replace("&", "§").replace("{PLAYER}", e.getPlayer().getName()));
		}
		if (DirectMC.getTPAManager().isPlayerPresent(e.getPlayer())){
			DirectMC.getTPAManager().deleteTPARequest(e.getPlayer());
		}
		if (DirectMC.getEcoManager().isEnabled()){
			DirectMC.getEcoManager().fixMoney(e.getPlayer(), EcoManager.getMinMoney(), EcoManager.getMaxMoney());
		}
		if (DirectMC.getCombatManager().getBoolean("kill-on-quit")){
			if (DirectMC.getCombatManager().isPlayerPresent(e.getPlayer())){
				e.getPlayer().setHealth(0);
			}
		}
		if (DirectMC.getCombatManager().getBoolean("remove-on-quit")){
			if (DirectMC.getCombatManager().isPlayerPresent(e.getPlayer())){
				DirectMC.getCombatManager().deleteCombat(e.getPlayer());
			}
		}
	}
}