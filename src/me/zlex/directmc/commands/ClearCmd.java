package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class ClearCmd extends Cmd{
	public ClearCmd(){
		super("clear");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("cleared", "&7Your inventory has been cleared.");
		addString("cleared-to", "&7The inventory of the player &a{PLAYER} &7has been cleared.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "clear") || PermissionsUtils.hasPermission(p, "cl")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						player.getInventory().clear();
						DirectMC.sendMessage(player, getString("cleared"));
						DirectMC.sendMessage(p, getString("cleared-to").replace("{PLAYER}", player.getName()));
						return true;
					}
				}catch(Exception e){
					p.getInventory().clear();
					DirectMC.sendMessage(p, getString("cleared"));
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