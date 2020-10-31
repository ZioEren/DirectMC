package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class BroadcastCmd extends Cmd{
	public BroadcastCmd(){
		super("broadcast");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("broadcast", "&9Broadcast> &7{MESSAGE}");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "broadcast") || PermissionsUtils.hasPermission(p, getString("bc"))){
				for (Player player: Bukkit.getOnlinePlayers()){
					String lol = "";
					for (String s: args){
						if (lol == ""){
							lol = s;
						}else{
							lol += " " + s;
						}
					}
					player.sendMessage(getString("broadcast").replace("{MESSAGE}", lol).replace("&", "§"));
				}
				return true;
			}else{
				DirectMC.sendMessage(p, getString("no-permission"));
				return true;
			}
		}else{
			sender.sendMessage(getString("no-player"));
			return true;
		}
	}
}