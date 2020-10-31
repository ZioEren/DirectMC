package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class GmcCmd extends Cmd{
	public GmcCmd(){
		super("gmc");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("creative", "&7Your &egame mode &7has been set to &ccreative&7.");
		addString("creative-to", "&7The &egame mode &7of the player &a{PLAYER} &7has been set to &ccreative&7.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "gmc") || PermissionsUtils.hasPermission(p, "gamemode")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						player.setGameMode(GameMode.CREATIVE);
						DirectMC.sendMessage(player, getString("creative"));
						DirectMC.sendMessage(p, getString("creative-to").replace("{PLAYER}", player.getName()));
						return true;
					}
				}catch(Exception e){
					p.setGameMode(GameMode.CREATIVE);
					DirectMC.sendMessage(p, getString("creative"));
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