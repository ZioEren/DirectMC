package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.database.Database;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class WarpCmd extends Cmd{
	public WarpCmd(){
		super("warp");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-warp", "&7This warp does not exists.");
		addString("no-warp-permission", "&7You do not have permission to go to this warp.");
		addString("usage", "&7Usage: &c'/warp <warp>'&7.");
		addString("warp", "&7You have been teleported to the warp &a{WARP}&7.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "warp")){
				try{
					String warp = args[0];
					if (PermissionsUtils.hasPermission(p, "warps.*") || PermissionsUtils.hasPermission(p, "warps." + warp)){
						String line = "";
						boolean alreadyExists = false;
						for (Database database: DirectMC.getDatabaseManager().getDatabases()){
							if (database.getName().equalsIgnoreCase("warps")){
								try{
									database.reload();
								}catch(Exception e){}
								try{
									for (String s: database.getLines()){
										if (s.startsWith(warp + " ")){
											alreadyExists = true;
											line = s;
											break;
										}
									}
								}catch(Exception e){}
							}
						}
						if (!alreadyExists){
							DirectMC.sendMessage(p, getString("no-warp"));
							return true;
						}else{
							for (Database database: DirectMC.getDatabaseManager().getDatabases()){
								if (database.getName().equalsIgnoreCase("warps")){
									line = line.replace(warp + " ", "");
									if (!(line == "")){
										String[] spaces = line.split("\\s+");
										double x = Double.parseDouble(spaces[0]);
										double y = Double.parseDouble(spaces[1]);
										double z = Double.parseDouble(spaces[2]);
										float yaw = Float.parseFloat(spaces[3]);
										float pitch = Float.parseFloat(spaces[4]);
										World world = Bukkit.getWorld(spaces[5]);
										Location loc = new Location(world, x, y, z, yaw, pitch);
										p.teleport(loc);
										DirectMC.sendMessage(p, getString("warp").replace("{WARP}", warp));
										return true;
									}
								}
							}
							return true;
						}
					}else{
						DirectMC.sendMessage(p, getString("no-warp-permission"));
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