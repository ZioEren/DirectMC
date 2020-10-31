package me.zlex.directmc.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.managers.home.Home;
import me.zlex.directmc.utils.PermissionsUtils;
public class SethomeCmd extends Cmd{
	public SethomeCmd(){
		super("sethome");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("sethome", "&7You succesfully set your home.");
		addString("sethome-name", "&7You succesfully set your home: &a{HOME}&7.");
		addString("no-more-homes", "&7You do not have permission to set more homes.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "sethome")){
				if (DirectMC.getHomeManager().getHomesByPlayer(p).size() > 0){
					if (PermissionsUtils.hasPermission(p, "sethome." + Integer.toString(DirectMC.getHomeManager().getHomesByPlayer(p).size() + 1))){
						if (args.length > 0){
							boolean exist = false;
							Home h = null;
							for (Home home: DirectMC.getHomeManager().getHomesByPlayer(p)){
								if (home.getName().equalsIgnoreCase(args[0])){
									exist = true;
									break;
								}
							}
							if (exist){
								DirectMC.getHomeManager().deleteHome(h);
								DirectMC.getHomeManager().addHome(p, args[0]);
								DirectMC.sendMessage(p, getString("sethome-name").replace("{HOME}", args[0]));
								return true;
							}else{
								DirectMC.getHomeManager().addHome(p, args[0]);
								DirectMC.sendMessage(p, getString("sethome-name").replace("{HOME}", args[0]));
								return true;
							}
						}else{
							boolean exist = false;
							Home h = null;
							for (Home home: DirectMC.getHomeManager().getHomesByPlayer(p)){
								if (home.getName().equalsIgnoreCase("")){
									exist = true;
									break;
								}
							}
							if (exist){
								DirectMC.getHomeManager().deleteHome(h);
								DirectMC.getHomeManager().addHome(p, "");
								DirectMC.sendMessage(p, getString("sethome"));
								return true;
							}else{
								DirectMC.getHomeManager().addHome(p, "");
								DirectMC.sendMessage(p, getString("sethome"));
								return true;
							}
						}
					}else{
						DirectMC.sendMessage(p, getString("no-more-homes"));
						return true;	
					}
				}else{
					if (args.length > 0){
						boolean exist = false;
						Home h = null;
						for (Home home: DirectMC.getHomeManager().getHomesByPlayer(p)){
							if (home.getName().equalsIgnoreCase(args[0])){
								exist = true;
								break;
							}
						}
						if (exist){
							DirectMC.getHomeManager().deleteHome(h);
							DirectMC.getHomeManager().addHome(p, args[0]);
							DirectMC.sendMessage(p, getString("sethome-name").replace("{HOME}", args[0]));
							return true;
						}else{
							DirectMC.getHomeManager().addHome(p, args[0]);
							DirectMC.sendMessage(p, getString("sethome-name").replace("{HOME}", args[0]));
							return true;
						}
					}else{
						boolean exist = false;
						Home h = null;
						for (Home home: DirectMC.getHomeManager().getHomesByPlayer(p)){
							if (home.getName().equalsIgnoreCase("")){
								exist = true;
								break;
							}
						}
						if (exist){
							DirectMC.getHomeManager().deleteHome(h);
							DirectMC.getHomeManager().addHome(p, "");
							DirectMC.sendMessage(p, getString("sethome"));
							return true;
						}else{
							DirectMC.getHomeManager().addHome(p, "");
							DirectMC.sendMessage(p, getString("sethome"));
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