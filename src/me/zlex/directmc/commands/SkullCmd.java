package me.zlex.directmc.commands;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class SkullCmd extends Cmd{
	public SkullCmd(){
		super("skull");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("skull", "&7You have received the skull of the player &a{PLAYER}&7.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "skull")){
				try{
					try{
						String name = args[0];
				        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				        SkullMeta skull = (SkullMeta) item.getItemMeta();
				        skull.setDisplayName(name);
				        skull.setOwner(name);
				        item.setItemMeta(skull);
				        p.getWorld().dropItem(p.getLocation(), item);
				        DirectMC.sendMessage(p, getString("skull").replace("{PLAYER}", name));
				        return true;
					}catch(Exception e1){
						String name = args[0];
				        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				        SkullMeta skull = (SkullMeta) item.getItemMeta();
				        skull.setDisplayName("Steve");
				        skull.setOwner("Steve");
				        item.setItemMeta(skull);
				        p.getWorld().dropItem(p.getLocation(), item);
				        DirectMC.sendMessage(p, getString("skull").replace("{PLAYER}", name));
						return true;
					}
				}catch(Exception e){
					try{
				        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				        SkullMeta skull = (SkullMeta) item.getItemMeta();
				        skull.setDisplayName(p.getName());
				        skull.setOwner(p.getName());
				        item.setItemMeta(skull);
				        p.getWorld().dropItem(p.getLocation(), item);
				        DirectMC.sendMessage(p, getString("skull").replace("{PLAYER}", p.getName()));
				        return true;
					}catch(Exception e1){
				        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				        SkullMeta skull = (SkullMeta) item.getItemMeta();
				        skull.setDisplayName("Steve");
				        skull.setOwner("Steve");
				        item.setItemMeta(skull);
				        p.getWorld().dropItem(p.getLocation(), item);
				        DirectMC.sendMessage(p, getString("skull").replace("{PLAYER}", p.getName()));
						return true;
					}
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