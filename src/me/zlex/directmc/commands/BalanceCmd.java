package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.managers.economy.EcoManager;
import me.zlex.directmc.utils.PermissionsUtils;
public class BalanceCmd extends Cmd{
	public BalanceCmd(){
		super("balance");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("no-economy", "&7The economy is not enabled.");
		addString("balance", "&7Balance: &c{SYMBOL}{MONEY}&7.");
		addString("balance-of", "&a{PLAYER}&7's balance: &c{SYMBOL}{MONEY}&7.");
	}
	@SuppressWarnings("static-access")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "balance") || PermissionsUtils.hasPermission(p, "money")){
				if (!DirectMC.getPlugin().getConfig().getConfigurationSection("economy").getBoolean("enabled")){
					DirectMC.sendMessage(p, getString("no-economy"));
					return true;
				}else{
					try{
						Player player = Bukkit.getPlayerExact(args[0]);
						if (player == null){
							DirectMC.sendMessage(p, getString("no-online"));
							return true;
						}else{
							DirectMC.getEcoManager().fixMoney(p, EcoManager.getMinMoney(), EcoManager.getMaxMoney());
							DirectMC.getEcoManager().fixMoney(player, EcoManager.getMinMoney(), EcoManager.getMaxMoney());
							DirectMC.sendMessage(p, getString("balance-of").replace("{SYMBOL}", DirectMC.getEcoManager().getVaultSymbol()).replace("{MONEY}", String.valueOf(DirectMC.getEcoManager().getMoney(player))).replace("{PLAYER}", player.getName()));
							return true;
						}
					}catch(Exception e){
						DirectMC.getEcoManager().fixMoney(p, EcoManager.getMinMoney(), EcoManager.getMaxMoney());
						DirectMC.sendMessage(p, getString("balance").replace("{SYMBOL}", DirectMC.getEcoManager().getVaultSymbol()).replace("{MONEY}", String.valueOf(DirectMC.getEcoManager().getMoney(p))));
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