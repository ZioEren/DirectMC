package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class WorldsCmd extends Cmd{
	public WorldsCmd(){
		super("worlds");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("worlds", "&7Worlds: {WORLDS}.");
		addString("no-worlds", "&7There are no worlds.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "worlds")){
				boolean isWorldPresent = false;
				String worlds = "";
				for (World w: Bukkit.getWorlds()){
					isWorldPresent = true;
					worlds += "&a" + w.getName() + "&7, ";
				}
				//worlds += "&7.";
				worlds = worlds.replace("&7, &7.", "&7.");
				worlds = worlds.replace(", .", "&7.");
				worlds = worlds.replace("&7, .", "&7.");
				worlds = worlds.replace("&7.&7.", "");
				worlds = worlds.replace("..", "&7.");
				worlds = worlds.replace("&7..", "&7.");
				worlds = worlds.replace(".&7.", "&7.");
				String theWorlds = getString("worlds").replace("{WORLDS}", worlds);
				theWorlds = theWorlds.replace("&7, &7.", "&7.");
				theWorlds = theWorlds.replace(", .", "&7.");
				theWorlds = theWorlds.replace("&7, .", "&7.");
				theWorlds = theWorlds.replace("&7.&7.", "");
				theWorlds = theWorlds.replace("..", "&7.");
				theWorlds = theWorlds.replace("&7..", "&7.");
				theWorlds = theWorlds.replace(".&7.", "&7.");
				if (!isWorldPresent){
					DirectMC.sendMessage(p, getString("no-worlds"));
				}else{
					DirectMC.sendMessage(p, theWorlds);
				}
				return true;
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