package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class SpeedCmd extends Cmd{
	public SpeedCmd(){
		super("speed");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("usage", "&7Usage: &c'/speed <walk/fly> <0-10> <player>'&7.");
		addString("walk-speed", "&7You have been set your walk speed to &e{SPEED}&7.");
		addString("fly-speed", "&7You have been set your fly speed to &e{SPEED}&7.");
		addString("walk-speed-to", "&7You have been set the walk speed of &a{PLAYER}&7 to &e{SPEED}&7.");
		addString("fly-speed-to", "&7You have been set the fly speed of &a{PLAYER}&7 to &e{SPEED}&7.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "speed")){
				try{
					Player player = Bukkit.getPlayerExact(args[2]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						try{
							if (args[0].equalsIgnoreCase("walk")){
								float speed = Float.parseFloat(args[1]);
								speed = speed / 10;
								if (speed > 1){
									speed = 1;
								}
								if (speed < 0.1F){
									speed = 0.1F;
								}
								player.setWalkSpeed(speed);
								DirectMC.sendMessage(player, getString("walk-speed").replace("{SPEED}", String.valueOf(speed * 10)));
								DirectMC.sendMessage(p, getString("walk-speed-to").replace("{SPEED}", String.valueOf(speed * 10)).replace("{PLAYER}", player.getName()));
								return true;
							}else if (args[0].equalsIgnoreCase("fly")){
								float speed = Float.parseFloat(args[1]);
								speed = speed / 10;
								if (speed > 10){
									speed = 10;
								}
								if (speed < 0){
									speed = 0;
								}
								player.setFlySpeed(speed);
								DirectMC.sendMessage(player, getString("fly-speed").replace("{SPEED}", String.valueOf(speed * 10)));
								DirectMC.sendMessage(p, getString("fly-speed-to").replace("{SPEED}", String.valueOf(speed * 10)).replace("{PLAYER}", player.getName()));
								return true;
							}else{
								try{
									float speed = Float.parseFloat(args[0]);
									speed = speed / 10;
									if (speed > 1){
										speed = 1;
									}
									if (speed < 0.1F){
										speed = 0.1F;
									}
									if (player.isFlying()){
										player.setFlySpeed(speed);
										DirectMC.sendMessage(player, getString("fly-speed").replace("{SPEED}", String.valueOf(speed * 10)));
										DirectMC.sendMessage(p, getString("fly-speed-to").replace("{SPEED}", String.valueOf(speed * 10)).replace("{PLAYER}", player.getName()));
										return true;
									}else{
										player.setWalkSpeed(speed);
										DirectMC.sendMessage(player, getString("walk-speed").replace("{SPEED}", String.valueOf(speed * 10)));
										DirectMC.sendMessage(p, getString("walk-speed-to").replace("{SPEED}", String.valueOf(speed * 10)).replace("{PLAYER}", player.getName()));
										return true;
									}
								}catch(Exception e1){
									DirectMC.sendMessage(p, getString("usage"));
									return true;
								}
							}
						}catch(Exception e1){
							DirectMC.sendMessage(p, getString("usage"));
							return true;
						}
					}
				}catch(Exception e){
					try{
						if (args[0].equalsIgnoreCase("walk")){
							float speed = Float.parseFloat(args[1]);
							speed = speed / 10;
							if (speed > 1){
								speed = 1;
							}
							if (speed < 0.1F){
								speed = 0.1F;
							}
							p.setWalkSpeed(speed);
							DirectMC.sendMessage(p, getString("walk-speed").replace("{SPEED}", String.valueOf(speed * 10)));
							return true;
						}else if (args[0].equalsIgnoreCase("fly")){
							float speed = Float.parseFloat(args[1]);
							speed = speed / 10;
							if (speed > 10){
								speed = 10;
							}
							if (speed < 0.1F){
								speed = 0.1F;
							}
							p.setFlySpeed(speed);
							DirectMC.sendMessage(p, getString("fly-speed").replace("{SPEED}", String.valueOf(speed * 10)));
							return true;
						}else{
							try{
								float speed = Float.parseFloat(args[0]);
								speed = speed / 10;
								if (speed > 1){
									speed = 1;
								}
								if (speed < 0.1F){
									speed = 0.1F;
								}
								if (p.isFlying()){
									p.setFlySpeed(speed);
									DirectMC.sendMessage(p, getString("fly-speed").replace("{SPEED}", String.valueOf(speed * 10)));
									return true;
								}else{
									p.setWalkSpeed(speed);
									DirectMC.sendMessage(p, getString("walk-speed").replace("{SPEED}", String.valueOf(speed * 10)));
									return true;
								}
							}catch(Exception e1){
								DirectMC.sendMessage(p, getString("usage"));
								return true;
							}
						}
					}catch(Exception e1){
						DirectMC.sendMessage(p, getString("usage"));
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