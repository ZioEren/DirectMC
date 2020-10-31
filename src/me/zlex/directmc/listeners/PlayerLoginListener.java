package me.zlex.directmc.listeners;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import me.zlex.directmc.main.DirectMC;
public class PlayerLoginListener implements Listener{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onLogin(PlayerLoginEvent e){
		Player p = e.getPlayer();
		for (Player player: Bukkit.getOnlinePlayers()){
			if (p.getName().equalsIgnoreCase(player.getName())){
				e.setResult(Result.KICK_OTHER);
				e.setKickMessage(DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("custom-messages").getString("already-online"));
				break;
			}
		}
	}
}