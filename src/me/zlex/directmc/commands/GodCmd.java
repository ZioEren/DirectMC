package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class GodCmd extends Cmd{
	public GodCmd(){
		super("god");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("enabled", "&7Your &agod &7has been enabled.");
		addString("disabled", "&7Your &agod &7has been disabled.");
		addString("enabled-to", "&7The &agod &7of the player &c{PLAYER} &7has been enabled.");
		addString("disabled-to", "&7The &agod &7of the player &c{PLAYER} &7has been disabled.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "god")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						if (!DirectMC.hasGodmode(player)){
							DirectMC.getGodmodes().add(player);
							DirectMC.sendMessage(player, getString("enabled"));
							DirectMC.sendMessage(p, getString("enabled-to").replace("{PLAYER}", player.getName()));
							return true;
						}else{
							DirectMC.getGodmodes().remove(player);
							DirectMC.sendMessage(player, getString("disabled"));
							DirectMC.sendMessage(p, getString("disabled-to").replace("{PLAYER}", player.getName()));
							return true;
						}
					}
				}catch(Exception e){
					if (!DirectMC.hasGodmode(p)){
						DirectMC.getGodmodes().add(p);
						DirectMC.sendMessage(p, getString("enabled"));
						return true;
					}else{
						DirectMC.getGodmodes().remove(p);
						DirectMC.sendMessage(p, getString("disabled"));
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