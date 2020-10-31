package me.zlex.directmc.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.database.Database;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class WarpsCmd extends Cmd{
	public WarpsCmd(){
		super("warps");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-warps", "&7There are no warps.");
		addString("warps", "&7Warps: {WARPS}&7.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "warps")){
				String warps = "";
				for (Database database: DirectMC.getDatabaseManager().getDatabases()){
					if (database.getName().equalsIgnoreCase("warps")){
						try{
							database.reload();
						}catch(Exception e){}
						try{
							for (String s: database.getLines()){
								if (!(s == "") && !(s == "null") && !(s.contains("null")) && !(s.replace(" ", "") == "") && !(s == null) && !(s == " ") && !(s == "\n") && !(s == " \n")){
									String[] t = s.split("\\s+");
									if (!(t[0].equalsIgnoreCase("null"))){
										warps += "&a" + t[0] + "&7, ";
									}
								}
							}
						}catch(Exception e){
							DirectMC.sendMessage(p, getString("no-warps"));
							return true;
						}
						break;
					}
				}
				warps += ".";
				if (!warps.contains(",")){
					DirectMC.sendMessage(p, getString("no-warps"));
					return true;
				}
				warps = warps.replace("&7, .", "");
				warps = warps.replace(".", "");
				if (warps == "" || warps == "null" || warps == " " || warps == null){
					DirectMC.sendMessage(p, getString("no-warps"));
					return true;
				}else{
					DirectMC.sendMessage(p, getString("warps").replace("{WARPS}", warps));
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