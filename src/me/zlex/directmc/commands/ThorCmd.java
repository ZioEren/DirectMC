package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class ThorCmd extends Cmd{
	public ThorCmd(){
		super("thor");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("thor", "&7You have been striked a lightning.");
		addString("thor-to", "&7You have been striked a lightning to &a{PLAYER}&7.");
		addString("thor-to-to", "&7You have been striked by &a{PLAYER}&7.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "thor")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						player.getWorld().strikeLightning(player.getLocation());
						DirectMC.sendMessage(player, getString("thor-to-to").replace("{PLAYER}", p.getName()));
						DirectMC.sendMessage(p, getString("thor-to").replace("{PLAYER}", player.getName()));
						return true;
					}
				}catch(Exception e){
				    BlockIterator iter = new BlockIterator(p, 200);
				    Block lastBlock = iter.next();
				    while (iter.hasNext()) {
				        lastBlock = iter.next();
				        if (lastBlock.getType() == Material.AIR){
				            continue;
				        }
				        break;
				    }
					p.getWorld().strikeLightning(lastBlock.getLocation());
					DirectMC.sendMessage(p, getString("thor"));
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