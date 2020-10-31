package me.zlex.directmc.managers.tpa;
import org.bukkit.entity.Player;
public class TPARequest{
	private Player sender;
	private Player receiver;
	private TPAType type;
	public TPARequest(Player sender, Player receiver, TPAType type){
		this.sender = sender;
		this.receiver = receiver;
		this.type = type;
	}
	public void setSender(Player sender){
		this.sender = sender;
	}
	public Player getSender(){
		return this.sender;
	}
	public void setReceiver(Player receiver){
		this.receiver = receiver;
	}
	public Player getReceiver(){
		return this.receiver;
	}
	public void setType(TPAType type){
		this.type = type;
	}
	public TPAType getType(){
		return this.type;
	}
}