package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class WorldCmd extends Cmd{
	public WorldCmd(){
		super("world");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("no-args", "&7Usage: &c'/world <world> <player>'&7.");
		addString("no-world", "&7This world does not exist.");
		addString("world", "&7You have been teleported to the world &c{WORLD}&7.");
		addString("world-to", "&7The player &a{PLAYER} &7has been teleported to the world &c{WORLD}&7.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "world")){
				try{
					Player player = Bukkit.getPlayerExact(args[1]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						World w = Bukkit.getWorld(args[0]);
						if (w == null){
							DirectMC.sendMessage(p, getString("no-world"));
							return true;
						}else{
							player.teleport(w.getSpawnLocation());
							DirectMC.sendMessage(player, getString("world").replace("{WORLD}", w.getName()));
							DirectMC.sendMessage(p, getString("world-to").replace("{WORLD}", w.getName()).replace("{PLAYER}", player.getName()));
							return true;
						}
					}
				}catch(Exception e){
					try{
						World w = Bukkit.getWorld(args[0]);
						if (w == null){
							DirectMC.sendMessage(p, getString("no-world"));
							return true;
						}else{
							p.teleport(w.getSpawnLocation());
							DirectMC.sendMessage(p, getString("world").replace("{WORLD}", w.getName()));
							return true;
						}
					}catch(Exception e1){
						DirectMC.sendMessage(p, getString("no-args"));
						return true;
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