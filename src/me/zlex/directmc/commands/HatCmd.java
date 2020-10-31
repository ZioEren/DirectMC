package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class HatCmd extends Cmd{
	public HatCmd(){
		super("hat");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("no-item", "&7There is no item in your hand.");
		addString("no-item-to", "&7This player has no item in hand.");
		addString("hat", "&7Your hat has been changed.");
		addString("hat-to", "&7The hat of the player &a{PLAYER} &7has been changed.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "hat")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR){
							DirectMC.sendMessage(player, getString("no-item"));
							DirectMC.sendMessage(p, getString("no-item-to").replace("{PLAYER}", player.getName()));
						}else{
							player.getInventory().setHelmet(p.getItemInHand());
							player.getInventory().remove(p.getItemInHand());
							DirectMC.sendMessage(player, getString("hat"));
							DirectMC.sendMessage(p, getString("hat-to").replace("{PLAYER}", player.getName()));
						}
						return true;
					}
				}catch(Exception e){
					if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR){
						DirectMC.sendMessage(p, getString("no-item"));
					}else{
						p.getInventory().setHelmet(p.getItemInHand());
						p.getInventory().remove(p.getItemInHand());
						DirectMC.sendMessage(p, getString("hat"));
					}
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