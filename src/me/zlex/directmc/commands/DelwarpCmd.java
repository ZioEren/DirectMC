package me.zlex.directmc.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.database.Database;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class DelwarpCmd extends Cmd{
	public DelwarpCmd(){
		super("delwarp");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-warp", "&7This warp does not exists.");
		addString("usage", "&7Usage: &c'/delwarp <warp>'&7.");
		addString("delwarp", "&7You have been deleted the warp &a{WARP}&7.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "delwarp")){
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
						if (!(line == "null") && !(line == "")){
							warps = warps.replace(line, "");
						}
						for (Database database: DirectMC.getDatabaseManager().getDatabases()){
							if (database.getName().equalsIgnoreCase("warps")){
								database.setData(warps);
								database.save();
								database.reload();
								String all = "";
								for (String s: database.getLines()){
									if (!(s == "") && !(s == "null") && !(s.contains("null")) && !(s.replace(" ", "") == "") && !(s == null) && !(s == " ") && !(s == "\n") && !(s == " \n")){
										if (all == ""){
											all = s;
										}else{
											all += "\n" + s;
										}
									}
								}
								database.setData(all);
								database.save();
								database.reload();
								break;
							}
						}
						DirectMC.sendMessage(p, getString("delwarp").replace("{WARP}", warp));
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