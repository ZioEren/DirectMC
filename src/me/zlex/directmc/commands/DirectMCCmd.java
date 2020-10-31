package me.zlex.directmc.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class DirectMCCmd extends Cmd{
	public DirectMCCmd(){
		super("directmc");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("usage", "&7Usage: &c'/directmc <reload/help>'&7.");
		addString("reload", "&7Config reloaded.");
		addString("help", "&7Commands: ");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "directmc") || PermissionsUtils.hasPermission(p, "dmc")){
				try{
					if (args[0].equalsIgnoreCase("reload")){
						DirectMC.getPlugin().reloadConfig();
						DirectMC.sendMessage(p, getString("reload"));
						return true;
					}else if (args[0].equalsIgnoreCase("help")){
						String list = "";
						for (Cmd cmdt: DirectMC.getCmdManager().getCommands()){
							list += cmdt.getName().toLowerCase() + ", "; 
						}
						list += ".";
						list = list.replace(", .", ".");
						DirectMC.sendMessage(p, getString("help") + list);
						return true;
					}else{
						DirectMC.sendMessage(p, getString("usage"));
						return true;
					}
				}catch(Exception e){
					DirectMC.sendMessage(p, getString("usage"));
					return true;
				}
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