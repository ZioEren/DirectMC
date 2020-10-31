package me.zlex.directmc.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class TPToggleCmd extends Cmd{
	public TPToggleCmd(){
		super("tptoggle");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("tptoggle-enabled", "&7You can not be teleported by nobody now.");
		addString("tptoggle-disabled", "&7You can now be teleported by everybody.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "tptoggle")){
				if (DirectMC.hasTPToggle(p)){
					DirectMC.getTPToggles().remove(p);
					DirectMC.sendMessage(p, getString("tptoggle-disabled"));
				}else{
					DirectMC.getTPToggles().add(p);
					DirectMC.sendMessage(p, getString("tptoggle-enabled"));
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