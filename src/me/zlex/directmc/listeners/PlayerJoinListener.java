package me.zlex.directmc.listeners;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.managers.economy.EcoManager;
import me.zlex.directmc.utils.LagUtils;
public class PlayerJoinListener implements Listener{
	@SuppressWarnings({ "static-access" })
	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if (DirectMC.getPlugin().getConfig().getConfigurationSection("general").getBoolean("force-spawn-on-join")){
			String poses = DirectMC.getPlugin().getConfig().getConfigurationSection("general").getString("spawn-location");
			String[] arg = poses.split("\\s+");
			double x = Double.parseDouble(arg[0]);
			double y = Double.parseDouble(arg[1]);
			double z = Double.parseDouble(arg[2]);
			float yaw = Float.parseFloat(arg[3]);
			float pitch = Float.parseFloat(arg[4]);
			World world = Bukkit.getWorld(arg[5]);
			p.teleport(new Location(world, x, y, z, yaw, pitch));
		}
		for (Player player: DirectMC.getVanishes()){
			p.hidePlayer(player);
		}
		if (DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("auto-pickup").getBoolean("enabled") && DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("auto-pickup").getBoolean("toggled-on-join")){
			if (!DirectMC.hasAutoPickup(p)){
				DirectMC.getAutoPickups().add(p);
			}
		}
		if (DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("custom-messages").getBoolean("enabled")){
			e.setJoinMessage(DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("custom-messages").getString("player-join").replace("&", "§").replace("{PLAYER}", p.getName()));
		}
		if (DirectMC.getEcoManager().isEnabled()){
			if (!DirectMC.getEcoManager().isInEconomy(p)){
				DirectMC.getEcoManager().startEconomy(p, DirectMC.getEcoManager().getStartMoney());
			}else{
				DirectMC.getEcoManager().fixMoney(p, EcoManager.getMinMoney(), EcoManager.getMaxMoney());
			}
		}
		if (DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("ping-fix").getBoolean("enabled")){
	        Bukkit.getServer().getScheduler().runTaskLater(DirectMC.getPlugin(), (Runnable)new Runnable(){
	            @Override
	            public void run(){
	            	try{
	                    final int ping = LagUtils.getPing2(p);
	                    if (ping > 1000 || ping < 0){
	                        LagUtils.setPing(p, 50);
	                    }
	            	}catch(Exception e){}
	            }
	        }, 60L);
		}
	}
}