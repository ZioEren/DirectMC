package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class PingCmd extends Cmd{
	public PingCmd(){
		super("ping");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("no-ping", "&7Can not ping that player.");
		addString("ping", "&7Your ping: &e{PING}&7ms.");
		addString("ping-of", "&7Ping of &a{PLAYER}&7: &e{PING}&7ms.");
	}
	@SuppressWarnings("static-access")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "ping")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						DirectMC.sendMessage(p, getString("ping-of").replace("{PLAYER}", player.getName()).replace("{PING}", String.valueOf(DirectMC.getLagUtils().getPing(player))));
						return true;
					}
				}catch(Exception e){
					DirectMC.sendMessage(p, getString("ping").replace("{PING}", String.valueOf(DirectMC.getLagUtils().getPing(p))));
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