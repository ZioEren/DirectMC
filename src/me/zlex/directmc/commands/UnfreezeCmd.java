package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class UnfreezeCmd extends Cmd{
	public UnfreezeCmd(){
		super("unfreeze");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("usage", "&7Usage: &c'/freeze <player>'&7.");
		addString("unfreeze", "&7You have been unfrozen.");
		addString("unfreeze-to", "&7The player &a{PLAYER} &7has been unfrozen.");
		addString("already-unfrozen", "&7This player is already unfrozen.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "unfreeze")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						if (!DirectMC.hasFreeze(player)){
							DirectMC.sendMessage(p, getString("already-unfrozen"));
							return true;
						}else{
							DirectMC.getFreezes().remove(player);
							player.setWalkSpeed(0.2F);
							player.setFlySpeed(0.1F);
							DirectMC.sendMessage(player, getString("unfreeze"));
							DirectMC.sendMessage(p, getString("unfreeze-to").replace("{PLAYER}", player.getName()));
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