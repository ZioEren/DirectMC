package me.zlex.directmc.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class SetspawnCmd extends Cmd{
	public SetspawnCmd(){
		super("setspawn");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("setspawn", "&7Succesfully set the spawn location.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "setspawn")){
				DirectMC.getPlugin().getConfig().getConfigurationSection("general").set("spawn-location", (p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ() + " " + p.getLocation().getYaw() + " " + p.getLocation().getPitch() + " " + p.getWorld().getName()).toString());
				DirectMC.getPlugin().saveConfig();
				DirectMC.getPlugin().reloadConfig();
				DirectMC.sendMessage(p, getString("setspawn"));
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