package me.zlex.directmc.listeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import me.zlex.directmc.main.DirectMC;
public class PlayerKickListener implements Listener{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onKick(PlayerKickEvent e){
		if (DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("custom-messages").getBoolean("enabled")){
			String reason = e.getReason();
			if (reason.equalsIgnoreCase("Flying is not enabled on this server")){
				e.setReason(getString("flying-not-enabled"));
			}else if (reason.equalsIgnoreCase("disconnect.spam")){
				e.setReason(getString("disconnect-spam"));
			}else if (reason.equalsIgnoreCase("Illegal characters in chat")){
				e.setReason(getString("illegal-characters"));
			}else if (reason.equalsIgnoreCase("You are sending too many packets!")){
				e.setReason(getString("too-many-packets"));
			}
		}
	}
	public String getString(String name){
		return DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("custom-messages").getString(name).replace("&", "§");
	}
}