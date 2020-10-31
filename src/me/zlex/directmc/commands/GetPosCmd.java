package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class GetPosCmd extends Cmd{
	public GetPosCmd(){
		super("getpos");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("getpos", "&7Your position: &e{X}&7, &e{Y}&7, &e{Z}&7.");
		addString("getpos-of", "&7Position of &a{PLAYER}&7: &e{X}&7, &e{Y}&7, &e{Z}&7.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "getpos")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						int x = (int) player.getLocation().getX();
						int y = (int) player.getLocation().getY();
						int z = (int) player.getLocation().getZ();
						DirectMC.sendMessage(p, getString("getpos-of").replace("{X}", String.valueOf(x)).replace("{Y}", String.valueOf(y)).replace("{Z}", String.valueOf(z)).replace("{PLAYER}", player.getName()));
						return true;
					}
				}catch(Exception e){
					int x = (int) p.getLocation().getX();
					int y = (int) p.getLocation().getY();
					int z = (int) p.getLocation().getZ();
					DirectMC.sendMessage(p, getString("getpos").replace("{X}", String.valueOf(x)).replace("{Y}", String.valueOf(y)).replace("{Z}", String.valueOf(z)));
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