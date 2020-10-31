package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class TPPosCmd extends Cmd{
	public TPPosCmd(){
		super("tppos");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("usage", "&7Usage: &c'/tppos <x> <y> <z> <player>'&7.");
		addString("tppos", "&7You have been teleported to &e{X}&7, &e{Y}&7, &e{Z}&7.");
		addString("tppos-to", "&7The player &a{PLAYER} &7has been teleported to &e{X}&7, &e{Y}&7, &e{Z}&7.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "tppos")){
				try{
					Player player = Bukkit.getPlayerExact(args[3]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						try{
							double x = Double.parseDouble(args[0]);
							double y = Double.parseDouble(args[1]);
							double z = Double.parseDouble(args[2]);
							player.teleport(new Location(player.getWorld(), x, y, z));
							DirectMC.sendMessage(player, getString("tppos").replace("{X}", String.valueOf(x)).replace("{Y}", String.valueOf(y)).replace("{Z}", String.valueOf(z)));
							DirectMC.sendMessage(p, getString("tppos-to").replace("{X}", String.valueOf(x)).replace("{Y}", String.valueOf(y)).replace("{Z}", String.valueOf(z)).replace("{PLAYER}", player.getName()));
							return true;
						}catch(Exception e1){
							DirectMC.sendMessage(p, getString("usage"));
							return true;
						}
					}
				}catch(Exception e){
					try{
						double x = Double.parseDouble(args[0]);
						double y = Double.parseDouble(args[1]);
						double z = Double.parseDouble(args[2]);
						p.teleport(new Location(p.getWorld(), x, y, z));
						DirectMC.sendMessage(p, getString("ttppos").replace("{X}", String.valueOf(x)).replace("{Y}", String.valueOf(y)).replace("{Z}", String.valueOf(z)));
						return true;
					}catch(Exception e1){
						DirectMC.sendMessage(p, getString("usage"));
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