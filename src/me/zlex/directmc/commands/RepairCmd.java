package me.zlex.directmc.commands;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class RepairCmd extends Cmd{
	public RepairCmd(){
		super("repair");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("usage", "&7Usage: &c'/repair <hand/all>'&7.");
		addString("no-hand-item", "&7There is no item in your hand.");
		addString("no-inventory-item", "&7There is no item in your inventory.");
		addString("hand-repaired", "&7Your item in hand was repaired.");
		addString("all-repaired", "&7All of your items in your inventory were repaired.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			try{
				if (args[0].equalsIgnoreCase("hand") && (PermissionsUtils.hasPermission(p, "repair.hand") || PermissionsUtils.hasPermission(p, "repair.*"))){
					if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR){
						DirectMC.sendMessage(p, getString("no-hand-item"));
						return true;
					}else{
						p.getItemInHand().setDurability((short) 0);
						DirectMC.sendMessage(p, getString("hand-repaired"));
						return true;
					}
				}else if (args[0].equalsIgnoreCase("all") && (PermissionsUtils.hasPermission(p, "repair.all") || PermissionsUtils.hasPermission(p, "repair.*"))){
					boolean isPresent = false;
					for (int i = 0; i < 36; i++){
						if (p.getInventory().getItem(i) != null && p.getInventory().getItem(i).getType() != Material.AIR){
							isPresent = true;
							p.getInventory().getItem(i).setDurability((short) 0);
						}
					}
					if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getType() != Material.AIR){
						isPresent = true;
						p.getInventory().getHelmet().setDurability((short) 0);
					}
					if (p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getType() != Material.AIR){
						isPresent = true;
						p.getInventory().getChestplate().setDurability((short) 0);
					}
					if (p.getInventory().getLeggings() != null && p.getInventory().getLeggings().getType() != Material.AIR){
						isPresent = true;
						p.getInventory().getLeggings().setDurability((short) 0);
					}
					if (p.getInventory().getBoots() != null && p.getInventory().getBoots().getType() != Material.AIR){
						isPresent = true;
						p.getInventory().getBoots().setDurability((short) 0);
					}
					if (!isPresent){
						DirectMC.sendMessage(p, getString("no-inventory-item"));
					}else{
						DirectMC.sendMessage(p, getString("all-repaired"));
					}
					return true;
				}else{
					if (!PermissionsUtils.hasPermission(p, "repair.hand") || !PermissionsUtils.hasPermission(p, "repair.*") || !PermissionsUtils.hasPermission(p, "repair.all")){
						DirectMC.sendMessage(p, getString("no-permission"));
						return true;
					}else{
						DirectMC.sendMessage(p, getString("usage"));
						return true;
					}
				}
			}catch(Exception e){
				DirectMC.sendMessage(p, getString("usage"));
				return true;
			}
		}else{
			sender.sendMessage(getString("no-player"));
			return true;
		}
	}
}