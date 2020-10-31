package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.managers.tpa.TPARequest;
import me.zlex.directmc.managers.tpa.TPAType;
import me.zlex.directmc.utils.PermissionsUtils;
public class TPACmd extends Cmd{
	public TPACmd(){
		super("tpa");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("no-yourself", "&7You can not send a teleport request to yourself.");
		addString("already-sent", "&7You have already sent a teleport request.");
		addString("already-received", "&7You have currently a received teleport request.");
		addString("already-sent-to", "&7This player has already sent a teleport request.");
		addString("already-received-to", "&7This player has already received a teleport request.");
		addString("tpa-received", "&7Hey, the player &a{PLAYER} &7has been sent a teleport request to you. You have only &e{TIME} &7seconds to accept. Use &c'/tpaccept' &7to accept the request or &c'/tpdeny'&7 to refuse the request.");
		addString("usage", "&7Usage: &c'/tpa <player>'&7.");
		addInteger("duration-seconds", 120);
		addString("tpa", "&7A teleport request has been sent to &a{PLAYER}&7. He has &e{TIME} &7seconds to accept the request.");
		addString("tpa-finished", "&7The teleport request duration time has been finished.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "tpa")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						if (DirectMC.getTPAManager().isSenderPresent(player)){
							DirectMC.sendMessage(p, getString("already-sent-to"));
							return true;
						}else if (DirectMC.getTPAManager().isReceiverPresent(player)){
							DirectMC.sendMessage(p, getString("already-received-to"));
							return true;
						}else if (DirectMC.getTPAManager().isSenderPresent(p)){
							DirectMC.sendMessage(p, getString("already-sent"));
							return true;
						}else if (DirectMC.getTPAManager().isReceiverPresent(p)){
							DirectMC.sendMessage(p, getString("already-received"));
							return true;
						}else if (player == p){
							DirectMC.sendMessage(p, getString("no-yourself"));
							return true;
						}else{
							int time = getInteger("duration-seconds");
							DirectMC.getTPAManager().addTPARequest(new TPARequest(p, player, TPAType.TO));
		        			Bukkit.getScheduler().runTaskLater(DirectMC.getPlugin(), new Runnable(){
		        				@Override
		        				public void run(){
		        					try{
		        						DirectMC.getTPAManager().deleteTPARequest(p);
			        					DirectMC.sendMessage(p, getString("tpa-finished"));
			        					DirectMC.sendMessage(player, getString("tpa-finished"));
		        					}catch(Exception e){}
		        				}
		        			}, 20L * time);
							DirectMC.sendMessage(p, getString("tpa").replace("{PLAYER}", player.getName()).replace("{TIME}", String.valueOf(time)));
							DirectMC.sendMessage(player, getString("tpa-received").replace("{PLAYER}", p.getName()).replace("{TIME}", String.valueOf(time)));
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