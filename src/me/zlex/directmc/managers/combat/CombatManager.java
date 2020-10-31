package me.zlex.directmc.managers.combat;
import java.util.ArrayList;
import org.bukkit.entity.Player;

import me.zlex.directmc.main.DirectMC;
public class CombatManager{
	private static ArrayList<Combat> combats;
	public CombatManager(){
		combats = new ArrayList<Combat>();
	}
	public ArrayList<Combat> getCombats(){
		return combats;
	}
	public void addCombat(Combat combat){
		combats.add(combat);
	}
	public void deleteCombat(Combat combat){
		combats.remove(combat);
	}
	public void deleteCombat(Player p){
		for (Combat c: combats){
			if (c.getFighter() == p || c.getVictim() == p){
				combats.remove(c);
			}
		}
	}
	public boolean isPlayerPresent(Player p){
		for (Combat c: combats){
			if (c.getFighter() == p || c.getVictim() == p){
				return true;
			}
		}
		return false;
	}
	public boolean isFighterPresent(Player p){
		for (Combat c: combats){
			if (c.getFighter() == p){
				return true;
			}
		}
		return false;
	}
	public boolean isVictimPresent(Player p){
		for (Combat c: combats){
			if (c.getVictim() == p){
				return true;
			}
		}
		return false;
	}
	public Combat getCombatOfPlayer(Player p){
		for (Combat c: combats){
			if (c.getFighter() == p || c.getVictim() == p){
				return c;
			}
		}
		return null;
	}
	public Combat getCombatOfFighter(Player p){
		for (Combat c: combats){
			if (c.getFighter() == p){
				return c;
			}
		}
		return null;
	}
	public Combat getCombatOfVictim(Player p){
		for (Combat c: combats){
			if (c.getVictim() == p){
				return c;
			}
		}
		return null;
	}
	public boolean getBoolean(String s){
		return DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("combat-log").getBoolean(s) && DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("combat-log").getBoolean("enabled");
	}
	public long getLong(String s){
		return DirectMC.getPlugin().getConfig().getConfigurationSection("functions").getConfigurationSection("combat-log").getLong(s);
	}
}