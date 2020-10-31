package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class ExtinguishCmd extends Cmd{
	public ExtinguishCmd(){
		super("extinguish");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("extinguished", "&7You have been extinguished.");
		addString("extinguished-to", "&7The player &a{PLAYER} &7has been extinguished.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "extinguish") || PermissionsUtils.hasPermission(p, "ext")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						player.setFireTicks(0);
						DirectMC.sendMessage(player, getString("extinguished"));
						DirectMC.sendMessage(p, getString("extinguished-to").replace("{PLAYER}", player.getName()));
						return true;
					}
				}catch(Exception e){
					p.setFireTicks(0);
					DirectMC.sendMessage(p, getString("extinguished"));
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