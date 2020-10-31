package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class TopCmd extends Cmd{
	public TopCmd(){
		super("top");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("top", "&7You have been teleported to the top block location.");
		addString("top-to", "&7The player &a{PLAYER} &7has been teleported to the block location.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "top")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						player.teleport(player.getWorld().getHighestBlockAt(player.getLocation()).getLocation());
						DirectMC.sendMessage(player, getString("top"));
						DirectMC.sendMessage(p, getString("top-to"));
						return true;
					}
				}catch(Exception e){
					p.teleport(p.getWorld().getHighestBlockAt(p.getLocation()).getLocation());
					DirectMC.sendMessage(p, getString("top"));
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