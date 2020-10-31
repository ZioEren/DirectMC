package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class TPHereCmd extends Cmd{
	public TPHereCmd(){
		super("tphere");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("no-yourself", "&7You can not teleport yourself to yourself.");
		addString("usage", "&7Usage: &c'/tphere <player>'&7.");
		addString("tphere", "&7You teleported &a{PLAYER} &7to you.");
		addString("tphere-to", "You have been teleported to &a{PLAYER}&7.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "tphere")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						if (player == p){
							DirectMC.sendMessage(p, getString("no-yourself"));
							return true;
						}else{
							player.teleport(p.getLocation());
							DirectMC.sendMessage(player, getString("tphere-to").replace("{PLAYER}", p.getName()));
							DirectMC.sendMessage(p, getString("tphere").replace("{PLAYER}", player.getName()));
							return true;
						}
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