package me.zlex.directmc.managers.home;
import java.util.ArrayList;
import org.bukkit.entity.Player;
public class HomeManager{
	private static ArrayList<Home> homesList;
	public HomeManager(){
		homesList = new ArrayList<Home>();
	}
	public ArrayList<Home> getHomes(){
		return homesList;
	}
	public void addHome(Home home){
		homesList.add(home);
	}
	public void deleteHome(Home home){
		homesList.add(home);
	}
	public boolean isHomePresent(Home home){
		return homesList.contains(home);
	}
	public void addHome(Player player, String name){
		homesList.add(new Home(player, name, player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
	}
	public void addHome(Player player){
		homesList.add(new Home(player, "", player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
	}
	public ArrayList<Home> getHomesByPlayer(Player player){
		ArrayList<Home> homes = new ArrayList<Home>();
		for (Home home: homesList){
			if (home.getPlayer().equals(player)){
				homes.add(home);
			}
		}
		return homes;
	}
	public Home getHomeByPlayer(Player player){
		for (Home home: homesList){
			if (home.getPlayer().equals(player)){
				return home;
			}
		}
		return null;
	}
	public void teleportToHome(Player player, Home home){
		player.getLocation().setX(home.getX());
		player.getLocation().setY(home.getY());
		player.getLocation().setZ(home.getZ());
		player.getLocation().setWorld(home.getWorld());
		player.getLocation().setYaw(home.getYaw());
		player.getLocation().setPitch(home.getPitch());
	}
}