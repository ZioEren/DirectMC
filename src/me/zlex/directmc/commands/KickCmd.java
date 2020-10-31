package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class KickCmd extends Cmd{
	public KickCmd(){
		super("kick");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("usage", "&7Usage: &c'/kick <player> <reason>'&7.");
		addBoolean("broadcast", true);
		addString("broadcast-msg", "&7The player &a{PLAYER} &7has been kicked by &e{KICKER} &7for: &c'{REASON}'&7.");
		addString("kick", "&7You kicked &a{PLAYER} &7for: &c'{REASON}'&7.");
		addString("kick-to", "&fYou have been kicked!\nReason: {REASON}\n&fBy: {KICKER}");
		addString("default-reason", "&fKicked for the justice!");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "kick")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						String reason = "";
						for (int i = 0; i < args.length; i++){
							if (!(i == 0)){
								if (reason == ""){
									reason = args[i];
								}else{
									reason += " " + args[i];
								}
							}
						}
						if (getBoolean("broadcast")){
							for (Player play: Bukkit.getOnlinePlayers()){
								DirectMC.sendMessage(play, getString("broadcast-msg").replace("{PLAYER}", player.getName()).replace("{KICKER}", p.getName()).replace("{REASON}", reason));
							}
						}
						DirectMC.sendMessage(p, getString("kick").replace("{PLAYER}", player.getName()).replace("{REASON}", reason));
						player.kickPlayer(getString("kick-to").replace("{REASON}", reason.replace("&", "§")).replace("{KICKER}", p.getName()));
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