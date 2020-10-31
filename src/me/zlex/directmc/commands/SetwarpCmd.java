package me.zlex.directmc.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.database.Database;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class SetwarpCmd extends Cmd{
	public SetwarpCmd(){
		super("setwarp");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("usage", "&7Usage: &c'/setwarp <warp>'&7.");
		addString("setwarp", "&7The warp &a{WARP} &7has been set to your location.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "setwarp")){
				try{
					String warp = args[0];
					String warps = "";
					String line = "";
					boolean alreadyExists = false;
					for (Database database: DirectMC.getDatabaseManager().getDatabases()){
						if (database.getName().equalsIgnoreCase("warps")){
							try{
								database.reload();
							}catch(Exception e){}
							warps = database.getData();
							try{
								for (String s: database.getLines()){
									if (s.toLowerCase().startsWith(warp.toLowerCase() + " ")){
										alreadyExists = true;
										line = s;
										break;
									}
								}
							}catch(Exception e){}
						}
					}
					if (!alreadyExists){
						if (warps == ""){
							warps = warp + " " + p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ() + " " + p.getLocation().getYaw() + " " + p.getLocation().getPitch() + " " + p.getWorld().getName();
						}else{
							warps += "\n" + warp + " " + p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ() + " " + p.getLocation().getYaw() + " " + p.getLocation().getPitch() + " " + p.getWorld().getName();
						}
						for (Database database: DirectMC.getDatabaseManager().getDatabases()){
							if (database.getName().equalsIgnoreCase("warps")){
								database.setData(warps);
								database.save();
								database.reload();
							}
						}
						DirectMC.sendMessage(p, getString("setwarp").replace("{WARP}", warp));
						return true;
					}else{
						if (!(line == "null") && !(line == "")){
							warps = warps.replace(line, warp + " " + p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ() + " " + p.getLocation().getYaw() + " " + p.getLocation().getPitch() + " " + p.getWorld().getName());
						}
						for (Database database: DirectMC.getDatabaseManager().getDatabases()){
							if (database.getName().equalsIgnoreCase("warps")){
								database.setData(warps);
								database.save();
								database.reload();
								break;
							}
						}
						DirectMC.sendMessage(p, getString("setwarp").replace("{WARP}", warp));
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