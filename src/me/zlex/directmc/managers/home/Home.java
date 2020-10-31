package me.zlex.directmc.managers.home;
import org.bukkit.World;
import org.bukkit.entity.Player;
public class Home{
	private Player player;
	private String name;
	private World world;
	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;
	public Home(Player player, String name, World world, double x, double y, double z, float yaw, float pitch){
		this.player = player;
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	public Player getPlayer(){
		return player;
	}
	public void setPlayer(Player player){
		this.player = player;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public World getWorld(){
		return world;
	}
	public void setWorld(World world){
		this.world = world;
	}
	public double getX(){
		return x;
	}
	public void setX(double x){
		this.x = x;
	}
	public double getY(){
		return y;
	}
	public void setY(double y){
		this.y = y;
	}
	public double getZ(){
		return z;
	}
	public void setZ(double z){
		this.z = z;
	}
	public float getYaw(){
		return yaw;
	}
	public void setYaw(float yaw){
		this.yaw = yaw;
	}
	public float getPitch(){
		return pitch;
	}
	public void setPitch(float pitch){
		this.pitch = pitch;
	}
}