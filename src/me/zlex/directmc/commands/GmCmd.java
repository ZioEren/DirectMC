package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class GmCmd extends Cmd{
	public GmCmd(){
		super("gm");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("usage", "&7Usage: &c'/gm <gm> <player>'&7.");
		addString("invalid-range", "&7Game mode can be from 0 to 3.");
		addString("survival", "&7Your &egame mode &7has been set to &csurvival&7.");
		addString("survival-to", "&7The &egame mode &7of the player &a{PLAYER} &7has been set to &csurvival&7.");
		addString("creative", "&7Your &egame mode &7has been set to &ccreative&7.");
		addString("creative-to", "&7The &egame mode &7of the player &a{PLAYER} &7has been set to &ccreative&7.");
		addString("adventure", "&7Your &egame mode &7has been set to &cadventure&7.");
		addString("adventure-to", "&7The &egame mode &7of the player &a{PLAYER} &7has been set to &cadventure&7.");
		addString("spectator", "&7Your &egame mode &7has been set to &cspectator&7.");
		addString("spectator-to", "&7The &egame mode &7of the player &a{PLAYER} &7has been set to &cspectator&7.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "gm") || PermissionsUtils.hasPermission(p, "gamemode")){
				try{
					try{
						Player player = Bukkit.getPlayerExact(args[1]);
						if (player == null){
							DirectMC.sendMessage(p, getString("no-online"));
							return true;
						}else{
							int gm = Integer.parseInt(args[0]);
							if (gm == 0){
								player.setGameMode(GameMode.SURVIVAL);
								DirectMC.sendMessage(player, getString("survival"));
								DirectMC.sendMessage(p, getString("survival-to").replace("{PLAYER}", player.getName()));
								return true;
							}else if (gm == 1){
								player.setGameMode(GameMode.CREATIVE);
								DirectMC.sendMessage(player, getString("creative"));
								DirectMC.sendMessage(p, getString("creative-to").replace("{PLAYER}", player.getName()));
								return true;
							}else if (gm == 2){
								player.setGameMode(GameMode.ADVENTURE);
								DirectMC.sendMessage(player, getString("adventure"));
								DirectMC.sendMessage(p, getString("adventure-to").replace("{PLAYER}", player.getName()));
								return true;
							}else if (gm == 3){
								player.setGameMode(GameMode.SPECTATOR);
								DirectMC.sendMessage(player, getString("spectator"));
								DirectMC.sendMessage(p, getString("spectator-to").replace("{PLAYER}", player.getName()));
								return true;
							}else{
								DirectMC.sendMessage(p, getString("invalid-range"));
								return true;
							}
						}
					}catch(Exception e){
						int gm = Integer.parseInt(args[0]);
						if (gm == 0){
							p.setGameMode(GameMode.SURVIVAL);
							DirectMC.sendMessage(p, getString("survival"));
							return true;
						}else if (gm == 1){
							p.setGameMode(GameMode.CREATIVE);
							DirectMC.sendMessage(p, getString("creative"));
							return true;
						}else if (gm == 2){
							p.setGameMode(GameMode.ADVENTURE);
							DirectMC.sendMessage(p, getString("adventure"));
							return true;
						}else if (gm == 3){
							p.setGameMode(GameMode.SPECTATOR);
							DirectMC.sendMessage(p, getString("spectator"));
							return true;
						}else{
							DirectMC.sendMessage(p, getString("invalid-range"));
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