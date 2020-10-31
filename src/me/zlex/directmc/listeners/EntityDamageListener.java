package me.zlex.directmc.listeners;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.managers.combat.Combat;
public class EntityDamageListener implements Listener{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e){
		if (e.getEntity() instanceof Player){
			if (DirectMC.hasGodmode((Player) e.getEntity())){
				e.setCancelled(true);
			}
		}else if (e.getDamager() instanceof Player){
			if (DirectMC.hasOnepunch((Player) e.getDamager())){
				LivingEntity en = (LivingEntity) e.getEntity();
				e.setDamage(en.getMaxHealth());
			}
		}
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player){
			if (DirectMC.getCombatManager().getBoolean("enabled")){
				if (!DirectMC.getCombatManager().isPlayerPresent((Player) e.getDamager())){
					Bukkit.getScheduler().runTaskLater(DirectMC.getPlugin(), new Runnable(){
						@Override
						public void run(){
							try{
								if (DirectMC.getCombatManager().isPlayerPresent((Player) e.getDamager())){
									DirectMC.getCombatManager().deleteCombat((Player) e.getDamager()); 
									DirectMC.sendMessage((Player) e.getDamager(), DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("combat-log").getString("no-combat").replace("&", "§"));
								}
								if (DirectMC.getCombatManager().getBoolean("disable-fly")){
									((Player) e.getDamager()).setAllowFlight(false);
								}
								DirectMC.sendMessage((Player) e.getDamager(), DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("combat-log").getString("now-combat").replace("&", "§"));
							}catch(Exception e){}
						}
					}, DirectMC.getCombatManager().getLong("remove-time"));
				}
				if (!DirectMC.getCombatManager().isPlayerPresent((Player) e.getEntity())){
					Bukkit.getScheduler().runTaskLater(DirectMC.getPlugin(), new Runnable(){
						@Override
						public void run(){
							try{
								if (DirectMC.getCombatManager().isPlayerPresent((Player) e.getEntity())){
									DirectMC.getCombatManager().deleteCombat((Player) e.getEntity()); 
									DirectMC.sendMessage((Player) e.getEntity(), DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("combat-log").getString("no-combat").replace("&", "§"));
								}
								if (DirectMC.getCombatManager().getBoolean("disable-fly")){
									((Player) e.getEntity()).setAllowFlight(false);
								}
								DirectMC.sendMessage((Player) e.getEntity(), DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("combat-log").getString("now-combat").replace("&", "§"));
							}catch(Exception e){}
						}
					}, DirectMC.getCombatManager().getLong("remove-time"));
				}
				if (DirectMC.getCombatManager().isPlayerPresent((Player) e.getDamager()) && DirectMC.getCombatManager().isPlayerPresent((Player) e.getEntity())){}else{
					DirectMC.getCombatManager().addCombat(new Combat((Player) e.getDamager(), (Player) e.getEntity()));
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDamage(EntityDamageEvent e){
		if (e.getEntity() instanceof Player){
			if (DirectMC.hasGodmode((Player) e.getEntity())){
				e.setCancelled(true);
			}
		}
	}
}