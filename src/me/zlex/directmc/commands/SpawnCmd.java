package me.zlex.directmc.commands;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.zlex.directmc.command.Cmd;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.PermissionsUtils;
public class SpawnCmd extends Cmd{
	public SpawnCmd(){
		super("spawn");
		addString("no-player", "&7Only players can execute this command.");
		addString("no-permission", "&7You do not have permission to run this command.");
		addString("no-online", "&7This player is not online.");
		addString("spawn", "&7You have been teleported to spawn.");
		addString("spawn-to", "&7The player &a{PLAYER} &7has been teleported to spawn.");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (PermissionsUtils.hasPermission(p, "spawn")){
				try{
					Player player = Bukkit.getPlayerExact(args[0]);
					if (player == null){
						DirectMC.sendMessage(p, getString("no-online"));
						return true;
					}else{
						String poses = DirectMC.getPlugin().getConfig().getConfigurationSection("general").getString("spawn-location");
						String[] arg = poses.split("\\s+");
						double x = Double.parseDouble(arg[0]);
						double y = Double.parseDouble(arg[1]);
						double z = Double.parseDouble(arg[2]);
						float yaw = Float.parseFloat(arg[3]);
						float pitch = Float.parseFloat(arg[4]);
						World world = Bukkit.getWorld(arg[5]);
						player.teleport(new Location(world, x, y, z, yaw, pitch));
						DirectMC.sendMessage(p, getString("spawn-to").replace("{PLAYER}", player.getName()));
						DirectMC.sendMessage(player, getString("spawn"));
						return true;
					}
				}catch(Exception e){
					String poses = DirectMC.getPlugin().getConfig().getConfigurationSection("general").getString("spawn-location");
					String[] arg = poses.split("\\s+");
					double x = Double.parseDouble(arg[0]);
					double y = Double.parseDouble(arg[1]);
					double z = Double.parseDouble(arg[2]);
					float yaw = Float.parseFloat(arg[3]);
					float pitch = Float.parseFloat(arg[4]);
					World world = Bukkit.getWorld(arg[5]);
					if (world == null){
						for (World w: Bukkit.getWorlds()){
							world = w;
							break;
						}
					}
					p.teleport(new Location(world, x, y, z, yaw, pitch));
					DirectMC.sendMessage(p, getString("spawn"));
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