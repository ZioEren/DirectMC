package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class HealCmd extends Cmd{
	public HealCmd(){
		super("heal");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("healed", "&7You have been healed.");
		addString("healed-to", "&7The player &a{PLAYER} &7has been healed.");
		addBoolean("heal", true);
		addBoolean("food", true);
		addBoolean("fire", true);
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "heal")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						if (getBoolean("heal")){
							player.setHealth(player.getMaxHealth());
						}
						if (getBoolean("food")){
							player.setFoodLevel(Integer.MAX_VALUE);
						}
						if (getBoolean("fire")){
							player.setFireTicks(0);
						}
						DirectMC.sendMessage(player, getString("healed"));
						DirectMC.sendMessage(p, getString("healed-to").replace("{PLAYER}", player.getName()));
						return true;
					}
				}catch(Exception e){
					if (getBoolean("heal")){
						p.setHealth(p.getMaxHealth());
					}
					if (getBoolean("food")){
						p.setFoodLevel(Integer.MAX_VALUE);
					}
					if (getBoolean("fire")){
						p.setFireTicks(0);
					}
					DirectMC.sendMessage(p, getString("healed"));
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