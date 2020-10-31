package me.zlex.directmc.commands;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.DistanceUtils;
import me.zlex.directmc.utils.PermissionsUtils;
public class NearCmd extends Cmd{
	public NearCmd(){
		super("near");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("no-nearby", "&7There are no nearby players in a radius of &e{RADIUS} &7blocks.");
		addString("no-nearby-of", "&7There are no nearby players in a radius of &e{RADIUS} &7blocks for the player &c{PLAYER}&7.");
		addString("usage", "&7Usage: &c'/near <radius> <player>'&7.");
		addString("nearby", "&7Nearby players in a radius of &e{RADIUS} &7blocks: {PLAYERS}.");
		addString("nearby-of", "&7Nearby players in a radius of &e{RADIUS} &7blocks for the player &c{PLAYER}&7: {PLAYERS}.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "near")){
				try{
					Player player = Bukkit.getPlayerExact(args[1]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						try{
							DirectMC.sendMessage(p, getString("usage"));
							return true;
						}catch(Exception e1){
							List<Player> players = DistanceUtils.getPlayersWithin(player, Integer.parseInt(args[0]));
							if (players == null || players.isEmpty()){
								DirectMC.sendMessage(p, getString("no-nearby").replace("{RADIUS}", args[1]));
								return true;
							}else{
								String thePlayers = "";
								for (Player tp: players){
									thePlayers += "&a" + tp.getName() + "&7, ";
								}
								thePlayers = thePlayers.replace("&7, .", "&7.");
								String complete = getString("nearby").replace("{PLAYERS}", thePlayers).replace("{RADIUS}", args[0]);
								complete = complete.replace("&7, .", "&7.");
								DirectMC.sendMessage(p, complete);
								return true;
							}
						}
					}
				}catch(Exception e){
					try{
						DirectMC.sendMessage(p, getString("usage"));
						return true;
					}catch(Exception e1){
						List<Player> players = DistanceUtils.getPlayersWithin(p, Integer.parseInt(args[0]));
						if (players == null || players.isEmpty()){
							DirectMC.sendMessage(p, getString("no-nearby"));
							return true;
						}else{
							String thePlayers = "";
							for (Player tp: players){
								thePlayers += "&a" + tp.getName() + "&7, ";
							}
							thePlayers = thePlayers.replace("&7, .", "&7.");
							String complete = getString("nearby").replace("{PLAYERS}", thePlayers).replace("{RADIUS}", args[0]);
							complete = complete.replace("&7, .", "&7.");
							DirectMC.sendMessage(p, complete);
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