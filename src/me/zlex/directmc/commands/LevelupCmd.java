package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class LevelupCmd extends Cmd{
	public LevelupCmd(){
		super("levelup");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("usage", "&7Usage: &c'/levelup <levels> <player>'&7.");
		addString("levelup", "&7You have been level up of &e{LEVELS} &7levels!");
		addString("levelup-to", "&a{PLAYER}&7has been level up of &e{LEVELS} &7levels!");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "levelup")){
				try{
					Player player = Bukkit.getPlayerExact(args[1]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						try{
							int levels = Integer.parseInt(args[0]);
							player.setLevel(p.getLevel() + levels);
							DirectMC.sendMessage(player, getString("levelup").replace("{LEVELS}", args[0]));
							DirectMC.sendMessage(p, getString("levelup").replace("{PLAYER}", player.getName()).replace("{LEVELS}", args[0]));
							return true;
						}catch(Exception e){
							DirectMC.sendMessage(p, getString("usage"));
							return true;
						}
					}
				}catch(Exception e){
					try{
						int levels = Integer.parseInt(args[0]);
						p.setLevel(p.getLevel() + levels);
						DirectMC.sendMessage(p, getString("levelup").replace("{LEVELS}", args[0]));
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