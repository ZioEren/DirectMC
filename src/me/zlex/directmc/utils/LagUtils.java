package me.zlex.directmc.utils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import me.zlex.directmc.main.DirectMC;
public class LagUtils implements Listener{
	public DirectMC directmc;
	private double tps;
	public LagUtils(DirectMC directmc){
		this.directmc = directmc;;
		new BukkitRunnable(){
			long sec;
			long currentSec;
			int ticks;
			public void run(){
				this.sec = (System.currentTimeMillis() / 1000L);
				if(this.currentSec == this.sec){
					this.ticks += 1;
				}else{
					this.currentSec = this.sec;
					LagUtils.this.tps = (LagUtils.this.tps == 0.0D ? this.ticks : (LagUtils.this.tps + this.ticks) / 2.0D);
					this.ticks = 0;
				}
			}
		}.runTaskTimerAsynchronously(directmc, 1L, 1L);
		this.directmc.addListener(this);
	}
	public double getTPS(){
		return this.tps + 1.0D > 20.0D ? 20.0D : this.tps + 1.0D;
	}
	public double getLag(){
		return Math.round((1.0D - tps / 20.0D) * 100.0D);
	}
	public double getFreeRam(){
		return Math.round(Runtime.getRuntime().freeMemory() / 1000000);
	}
	public double getMaxRam(){
		return Math.round(Runtime.getRuntime().maxMemory() / 1000000);
	}
	public static Object getNmsPlayer(Player p) throws Exception{
		Method getHandle = p.getClass().getMethod("getHandle");
		return getHandle.invoke(p);
	}
	public static Object getFieldValue(Object instance, String fieldName) throws Exception{
		Field field = instance.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(instance);
	}
	public static int getPing(Player who){
		try{
			String bukkitversion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
			Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + bukkitversion + ".entity.CraftPlayer");
			Object handle = craftPlayer.getMethod("getHandle").invoke(who);
			Integer ping = (Integer) handle.getClass().getDeclaredField("ping").get(handle);
			return ping.intValue();
		}catch(Exception e){
			return 404;
		}
	}
    public static int getPing2(final Player player){
        int ping = 100;
        try{
            final Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().substring(23) + ".entity.CraftPlayer");
            final Object handle = craftPlayerClass.getMethod("getHandle", (Class<?>[])new Class[0]).invoke(craftPlayerClass.cast(player), new Object[0]);
            ping = handle.getClass().getDeclaredField("ping").getInt(handle);
        }
        catch(Exception ex){}
        return ping;
    }
    public static void setPing(final Player player, final int ping){
        try{
            final Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().substring(23) + ".entity.CraftPlayer");
            final Object handle = craftPlayerClass.getMethod("getHandle", (Class<?>[])new Class[0]).invoke(craftPlayerClass.cast(player), new Object[0]);
            handle.getClass().getDeclaredField("ping").set(handle, 50);
        }
        catch(Exception ex){}
    }
}