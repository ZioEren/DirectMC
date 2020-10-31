package me.zlex.directmc.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.managers.home.Home;
import me.zlex.directmc.utils.PermissionsUtils;
public class HomeCmd extends Cmd{
	public HomeCmd(){
		super("home");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-homes", "&7You do not have any home set.");
		addString("home", "&7You have been teleported to your home.");
		addString("home-name", "&7You have been teleported to your home: &a{HOME}&7.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "home")){
				if (DirectMC.getHomeManager().getHomesByPlayer(p).size() <= 0){
					DirectMC.sendMessage(p, getString("no-homes"));
					return true;
				}else{
					if (args.length > 0){
						Home h = null;
						for (Home home: DirectMC.getHomeManager().getHomesByPlayer(p)){
							if (home.getName().equalsIgnoreCase(args[0])){
								h = home;
							}
						}
						if (h != null){
							DirectMC.getHomeManager().teleportToHome(p, h);
							DirectMC.sendMessage(p, getString("home-name").replace("{HOME}", h.getName()));
							return true;	
						}else{
							DirectMC.sendMessage(p, getString("no-homes"));
							return true;
						}
					}else{
						Home h = null;
						for (Home home: DirectMC.getHomeManager().getHomesByPlayer(p)){
							if (home.getName().equalsIgnoreCase("")){
								h = home;
							}
						}
						if (h != null){
							DirectMC.getHomeManager().teleportToHome(p, h);
							DirectMC.sendMessage(p, getString("home"));
							return true;	
						}else{
							DirectMC.sendMessage(p, getString("no-homes"));
							return true;
						}
					}
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