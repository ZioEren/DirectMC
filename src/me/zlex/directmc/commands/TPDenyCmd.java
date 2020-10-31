package me.zlex.directmc.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.managers.tpa.TPARequest;
import me.zlex.directmc.utils.PermissionsUtils;
public class TPDenyCmd extends Cmd{
	public TPDenyCmd(){
		super("tpdeny");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-request", "&7You do not have a teleport request to deny.");
		addString("tpdeny", "&7The teleport request has been denied.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "tpdeny")){
				if (DirectMC.getTPAManager().isReceiverPresent(p)){
					TPARequest request = DirectMC.getTPAManager().getTPARequestOfReceiver(p);
					DirectMC.sendMessage(p, getString("tpdeny"));
					DirectMC.sendMessage(request.getSender(), getString("tpdeny"));
					DirectMC.getTPAManager().deleteTPARequest(p);
					return true;
				}else{
					DirectMC.sendMessage(p, getString("no-request"));
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