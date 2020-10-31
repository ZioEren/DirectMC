package me.zlex.directmc.managers.combat;
import org.bukkit.entity.Player;
public class Combat{
	private Player fighter;
	private Player victim;
	public Combat(Player fighter, Player victim){
		this.fighter = fighter;
		this.victim = victim;
	}
	public Player getFighter(){
		return fighter;
	}
	public void setFighter(Player fighter){
		this.fighter = fighter;
	}
	public Player getVictim(){
		return victim;
	}
	public void setVictim(Player victim){
		this.victim = victim;
	}
}