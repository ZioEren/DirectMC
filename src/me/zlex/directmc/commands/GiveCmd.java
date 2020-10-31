package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class GiveCmd extends Cmd{
	public GiveCmd(){
		super("give");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("usage", "&7Usage: &c'/give <player> <id/name> <amount>'&7.");
		addString("give", "&7You have been given to &a{PLAYER} &7a quantity of &e{AMOUNT} &7of &c{NAME}&7.");
		addString("give-to", "&7You have received an amount of &e{AMOUNT} &7of &c{NAME}&7 by &a{PLAYER}&7.");
	}
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "give")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						try{
							String asd = args[1];
							int id = 0;
							String name = "";
							if (isNumeric(asd)){
								id = Integer.parseInt(asd);							
							}else{
								name = asd;
							}
							int amount = Integer.parseInt(args[2]);
							ItemStack item;
							Material material;
							if (isNumeric(asd)){
								material = Material.getMaterial(id);
							}else{
								material = Material.getMaterial(name);
							}
							item = new ItemStack(material);
							item.setAmount(amount);
							p.getWorld().dropItem(p.getLocation(), item);
							String name1 = "";
							if(item.hasItemMeta()){
							    name1 = item.getItemMeta().getDisplayName();
							}
							else{
							    name1 = item.getType().toString();
							}
							DirectMC.sendMessage(player, getString("give-to").replace("{AMOUNT}", String.valueOf(amount)).replace("{NAME}", name1).replace("{PLAYER}", p.getName()));
							DirectMC.sendMessage(p, getString("give").replace("{PLAYER}", player.getName()).replace("{AMOUNT}", String.valueOf(amount)).replace("{NAME}", name1));
							return true;
						}catch(Exception e1){
							DirectMC.sendMessage(p, getString("usage"));
							return true;
						}
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
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    Double.parseDouble(str);
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
}