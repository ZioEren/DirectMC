package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class FeedCmd extends Cmd{
	public FeedCmd(){
		super("feed");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("feeded", "&7You have been feeded.");
		addString("feeded-to", "&7The player &a{PLAYER} &7has been feeded.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "feed")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						player.setFoodLevel(Integer.MAX_VALUE);
						DirectMC.sendMessage(player, getString("feeded"));
						DirectMC.sendMessage(p, getString("feeded-to").replace("{PLAYER}", player.getName()));
						return true;
					}
				}catch(Exception e){
					p.setFoodLevel(Integer.MAX_VALUE);
					DirectMC.sendMessage(p, getString("feeded"));
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