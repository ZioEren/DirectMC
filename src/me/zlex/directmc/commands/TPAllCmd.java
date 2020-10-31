package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class TPAllCmd extends Cmd{
	public TPAllCmd(){
		super("tpall");
		addBoolean("apply-tp-toggle", true);
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("no-players", "&7There is no player that can be teleported to you.");
		addString("tpall", "&7All players have been teleported to you.");
		addString("tpall-to", "&7You have been teleported to &a{PLAYER}&7.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "tpall")){
				try{
					try{
						Player player = Bukkit.getPlayerExact(args[0]);
						if (player == null){
							DirectMC.sendMessage(p, getString("no-online"));
							return true;
						}else{
							boolean isSomeoneOnline = false;
							for (Player thePlayer: Bukkit.getOnlinePlayers()){
								if (!(thePlayer == p)){
									if (getBoolean("apply-tp-toggle") && DirectMC.hasTPToggle(thePlayer)){}else{
										isSomeoneOnline = true;
										thePlayer.teleport(player.getLocation());
										DirectMC.sendMessage(thePlayer, getString("tpall-to").replace("{PLAYER}", p.getName()));
									}
								}
							}
							if (!isSomeoneOnline){
								DirectMC.sendMessage(p, getString("no-players"));
							}else{
								DirectMC.sendMessage(player, getString("tpall"));
							}
							return true;
						}
					}catch(Exception e){
						boolean isSomeoneOnline = false;
						for (Player thePlayer: Bukkit.getOnlinePlayers()){
							if (!(thePlayer == p)){
								if (getBoolean("apply-tp-toggle") && DirectMC.hasTPToggle(thePlayer)){}else{
									isSomeoneOnline = true;
									thePlayer.teleport(p.getLocation());
									DirectMC.sendMessage(thePlayer, getString("tpall-to").replace("{PLAYER}", p.getName()));
								}
							}
						}
						if (!isSomeoneOnline){
							DirectMC.sendMessage(p, getString("no-players"));
						}else{
							DirectMC.sendMessage(p, getString("tpall"));
						}
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