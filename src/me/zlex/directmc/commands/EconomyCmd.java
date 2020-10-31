package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class EconomyCmd extends Cmd{
	public EconomyCmd(){
		super("economy");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("usage", "&7Usage: &c'/eco <give/set/take> <player> <money>'&7.");
		addString("eco-give", "&7You gave &c{SYMBOL}{MONEY} &7money to &a{PLAYER}&7.");
		addString("eco-give-to", "&7You received &c{SYMBOL}{MONEY} &7money from &a{PLAYER}&7.");
		addString("eco-set", "&7You set &c{SYMBOL}{MONEY} &7money to &a{PLAYER}&7.");
		addString("eco-set-to", "&7You now have &c{SYMBOL}{MONEY} &7money from &a{PLAYER}&7.");
		addString("eco-take", "&7You took &c{SYMBOL}{MONEY} &7money to &a{PLAYER}&7.");
		addString("eco-take-to", "&7You lost &c{SYMBOL}{MONEY} &7money from &a{PLAYER}&7.");
	}
	@SuppressWarnings("static-access")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "economy") || PermissionsUtils.hasPermission(p, "eco")){
				try{
					Player player = Bukkit.getPlayerExact(args[1]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						try{
							double money = Double.parseDouble(args[2]);
							if (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("add")){
								DirectMC.getEcoManager().addMoney(player, money);
								DirectMC.getEcoManager().fixMoney(player, DirectMC.getEcoManager().getMinMoney(), DirectMC.getEcoManager().getMaxMoney());
								DirectMC.sendMessage(player, getString("eco-give-to").replace("{SYMBOL}", DirectMC.getEcoManager().getVaultSymbol()).replace("{MONEY}", String.valueOf(money)).replace("{PLAYER}", p.getName()));
								DirectMC.sendMessage(p, getString("eco-give").replace("{SYMBOL}", DirectMC.getEcoManager().getVaultSymbol()).replace("{MONEY}", String.valueOf(money)).replace("{PLAYER}", player.getName()));
								return true;
							}else if (args[0].equalsIgnoreCase("set")){
								DirectMC.getEcoManager().setMoney(player, money);
								DirectMC.getEcoManager().fixMoney(player, DirectMC.getEcoManager().getMinMoney(), DirectMC.getEcoManager().getMaxMoney());
								DirectMC.sendMessage(player, getString("eco-set-to").replace("{SYMBOL}", DirectMC.getEcoManager().getVaultSymbol()).replace("{MONEY}", String.valueOf(money)).replace("{PLAYER}", p.getName()));
								DirectMC.sendMessage(p, getString("eco-set").replace("{SYMBOL}", DirectMC.getEcoManager().getVaultSymbol()).replace("{MONEY}", String.valueOf(money)).replace("{PLAYER}", player.getName()));
								return true;
							}else if (args[0].equalsIgnoreCase("take")){
								DirectMC.getEcoManager().takeMoney(player, money);
								DirectMC.getEcoManager().fixMoney(player, DirectMC.getEcoManager().getMinMoney(), DirectMC.getEcoManager().getMaxMoney());
								DirectMC.sendMessage(player, getString("eco-take-to").replace("{SYMBOL}", DirectMC.getEcoManager().getVaultSymbol()).replace("{MONEY}", String.valueOf(money)).replace("{PLAYER}", p.getName()));
								DirectMC.sendMessage(p, getString("eco-take").replace("{SYMBOL}", DirectMC.getEcoManager().getVaultSymbol()).replace("{MONEY}", String.valueOf(money)).replace("{PLAYER}", player.getName()));
								return true;
							}else{	
								DirectMC.sendMessage(p, getString("usage"));
								return true;
							}
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
}