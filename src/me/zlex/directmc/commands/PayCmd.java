package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class PayCmd extends Cmd{
	public PayCmd(){
		super("pay");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("no-money", "&7You do not have sufficient money to pay.");
		addString("no-yourself", "&7You can not pay yourself.");
		addString("usage", "&7Usage: &c'/pay <player> <money>'&7.");
		addString("pay", "&7You paid &c{SYMBOL}{MONEY} &7to &a{PLAYER}&7.");
		addString("pay-to", "&7You received &c{SYMBOL}{MONEY} &7from &a{PLAYER}&7.");
	}
	@SuppressWarnings("static-access")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "pay")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						try{
							double money = Double.parseDouble(args[1]);
							if (!DirectMC.getEcoManager().hasSufficientMoney(p, money)){
								DirectMC.sendMessage(p, getString("no-money"));
								return true;
							}else{
								if (p == player){
									DirectMC.sendMessage(p, getString("no-yourself"));
									return true;
								}else{
									DirectMC.getEcoManager().takeMoney(p, money);
									DirectMC.sendMessage(player, getString("pay-to").replace("{PLAYER}", p.getName()).replace("{SYMBOL}", DirectMC.getEcoManager().getVaultSymbol()).replace("{MONEY}", String.valueOf(money)));
									DirectMC.sendMessage(p, getString("pay").replace("{PLAYER}", player.getName()).replace("{SYMBOL}", DirectMC.getEcoManager().getVaultSymbol()).replace("{MONEY}", String.valueOf(money)));
									return true;
								}
							}
						}catch(Exception e){
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