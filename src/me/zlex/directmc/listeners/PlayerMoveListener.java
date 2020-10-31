package me.zlex.directmc.listeners;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import me.zlex.directmc.main.DirectMC;
public class PlayerMoveListener implements Listener{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onMove(PlayerMoveEvent e){
		if (DirectMC.hasFreeze(e.getPlayer())){
	        Location locFrom = e.getFrom();
	        Location locTo = e.getTo();
	        if (locFrom.getBlockX() != locTo.getBlockX() || locFrom.getBlockY() != locTo.getBlockY() || locFrom.getBlockZ() != locTo.getBlockZ()) {
	        	e.setCancelled(true);
	        }
		}
	}
}