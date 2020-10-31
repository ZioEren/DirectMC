package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class GmaCmd extends Cmd{
	public GmaCmd(){
		super("gma");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("adventure", "&7Your &egame mode &7has been set to &cadventure&7.");
		addString("adventure-to", "&7The &egame mode &7of the player &a{PLAYER} &7has been set to &cadventure&7.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "gma") || PermissionsUtils.hasPermission(p, "gamemode")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						player.setGameMode(GameMode.ADVENTURE);
						DirectMC.sendMessage(player, getString("adventure"));
						DirectMC.sendMessage(p, getString("adventure-to").replace("{PLAYER}", player.getName()));
						return true;
					}
				}catch(Exception e){
					p.setGameMode(GameMode.ADVENTURE);
					DirectMC.sendMessage(p, getString("adventure"));
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