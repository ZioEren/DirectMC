package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class SuicideCmd extends Cmd{
	public SuicideCmd(){
		super("suicide");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("suicide", "&7Bye bye, world...");
		addBoolean("broadcast", true);
		addString("broadcast-msg", "&7The player &a{PLAYER} &7has been suicided.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "suicide")){
				p.setHealth(0);
				DirectMC.sendMessage(p, getString("suicide"));
				if (getBoolean("broadcast")){
					for (Player player: Bukkit.getOnlinePlayers()){
						DirectMC.sendMessage(player, getString("broadcast-msg").replace("{PLAYER}", p.getName()));
					}
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