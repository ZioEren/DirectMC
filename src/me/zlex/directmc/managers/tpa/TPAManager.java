package me.zlex.directmc.managers.tpa;
import java.util.ArrayList;
import org.bukkit.entity.Player;
public class TPAManager{
	private static ArrayList<TPARequest> tpaRequests;
	public TPAManager(){
		tpaRequests = new ArrayList<TPARequest>();
	}
	public ArrayList<TPARequest> getTPARequests(){
		return tpaRequests;
	}
	public void addTPARequest(TPARequest request){
		tpaRequests.add(request);
	}
	public void deleteTPARequest(TPARequest request){
		tpaRequests.remove(request);
	}
	public void deleteTPARequest(Player p){
		for (TPARequest request: tpaRequests){
			if (request.getSender() == p || request.getReceiver() == p){
				tpaRequests.remove(request);
				return;
			}
		}
	}
	public boolean isPlayerPresent(Player p){
		for (TPARequest request: tpaRequests){
			if (request.getSender() == p || request.getReceiver() == p){
				return true;
			}
		}
		return false;
	}
	public boolean isSenderPresent(Player p){
		for (TPARequest request: tpaRequests){
			if (request.getSender() == p){
				return true;
			}
		}
		return false;
	}
	public boolean isReceiverPresent(Player p){
		for (TPARequest request: tpaRequests){
			if (request.getReceiver() == p){
				return true;
			}
		}
		return false;
	}
	public TPARequest getTPARequestOfPlayer(Player p){
		for (TPARequest request: tpaRequests){
			if (request.getSender() == p || request.getReceiver() == p){
				return request;
			}
		}
		return null;
	}
	public TPARequest getTPARequestOfSender(Player p){
		for (TPARequest request: tpaRequests){
			if (request.getSender() == p){
				return request;
			}
		}
		return null;
	}
	public TPARequest getTPARequestOfReceiver(Player p){
		for (TPARequest request: tpaRequests){
			if (request.getReceiver() == p){
				return request;
			}
		}
		return null;
	}
	public TPAType getTPATypeOfPlayer(Player p){
		for (TPARequest request: tpaRequests){
			if (request.getSender() == p || request.getReceiver() == p){
				return request.getType();
			}
		}
		return null;
	}
	public TPAType getTPATypeOfSender(Player p){
		for (TPARequest request: tpaRequests){
			if (request.getSender() == p){
				return request.getType();
			}
		}
		return null;
	}
	public TPAType getTPATypeOfReceiver(Player p){
		for (TPARequest request: tpaRequests){
			if (request.getReceiver() == p){
				return request.getType();
			}
		}
		return null;
	}
}