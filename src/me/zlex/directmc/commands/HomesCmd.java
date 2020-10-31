package me.zlex.directmc.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.managers.home.Home;
import me.zlex.directmc.utils.PermissionsUtils;
public class HomesCmd extends Cmd{
	public HomesCmd(){
		super("homes");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-homes", "&7You do not have any home set.");
		addString("homes", "&7Here are your homes: ");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "homes") || PermissionsUtils.hasPermission(p, "home.list")){
				if (DirectMC.getHomeManager().getHomesByPlayer(p).size() <= 0){
					DirectMC.sendMessage(p, getString("no-homes"));
					return true;
				}else{
					String homes = "";
					for (Home home: DirectMC.getHomeManager().getHomes()){
						if (home.getPlayer().equals(p)){
							homes += "&a" + home.getName() + "&7, ";
						}
					}
					if (homes.endsWith(", ")){
						homes = homes.substring(0, homes.length() - 2);
						homes += ".";
					}
					DirectMC.sendMessage(p, getString("homes") + homes);
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