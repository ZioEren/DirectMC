package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class WorkbenchCmd extends Cmd{
	public WorkbenchCmd(){
		super("workbench");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("workbench", "&7You have opened the workbench.");
		addString("workbench-to", "&7The workbench of the player &a{PLAYER} &7has been opened.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "workbench") || PermissionsUtils.hasPermission(p, "wb")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						player.openWorkbench(null, true);
						DirectMC.sendMessage(player, getString("workbench"));
						DirectMC.sendMessage(p, getString("workbench-to").replace("{PLAYER}", player.getName()));
						return true;
					}
				}catch(Exception e){
					p.openWorkbench(null, true);
					DirectMC.sendMessage(p, getString("workbench"));
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