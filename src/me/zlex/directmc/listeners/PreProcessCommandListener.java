package me.zlex.directmc.listeners;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class PreProcessCommandListener implements Listener{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onCommand(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		if (DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("command-blocking").getBoolean("enabled") && !p.isOp() && !PermissionsUtils.hasPermission(p, "*") && !PermissionsUtils.hasPermission(p, "commandblock.*") && !PermissionsUtils.hasPermission(p, "commandblock.bypass")){
			String cmd = e.getMessage().toLowerCase().substring(1, e.getMessage().length());
			String blocked = DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("command-blocking").getString("blocked-commands");
			String[] blockedcommands = blocked.split("\\s+");
			cmd += " ";
			for (String theCmd: blockedcommands){
				String thatBlock = theCmd.toLowerCase();
				thatBlock = thatBlock.replace(" ", "");
				if (cmd.startsWith(thatBlock + " ")){
					e.setCancelled(true);
					DirectMC.sendMessage(p, DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("command-blocking").getString("command-blocked-message"));
					break;
				}
			}
		}
		if (DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("custom-messages").getBoolean("enabled")){
	        String cmd = e.getMessage().split(" ")[0];
	        HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(cmd);
	        if (topic == null){
	            p.sendMessage(DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("custom-messages").getString("unknown-cmd").replace("&", "§"));
	            e.setCancelled(true);
	        }
		}
		if (DirectMC.getCombatManager().getBoolean("disable-commands")){
			if (DirectMC.getCombatManager().isPlayerPresent(p)){
				DirectMC.sendMessage(p, DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("combat-log").getString("no-commands").replace("&", "§"));
				e.setCancelled(true);
			}
		}
	}
}