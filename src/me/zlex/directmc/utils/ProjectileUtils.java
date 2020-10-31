package me.zlex.directmc.utils;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.WitherSkull;
import org.bukkit.util.Vector;
public class ProjectileUtils{
	public static void LaunchFireball(final LivingEntity sender){
        final Vector direction = sender.getEyeLocation().getDirection().multiply(2);
        final Fireball fb = (Fireball)sender.getWorld().spawn(sender.getEyeLocation().add(direction.getX(), direction.getY(), direction.getZ()), Fireball.class);
        fb.setShooter(sender);
    }   
    public static void LaunchSnowball(final LivingEntity sender){
        sender.launchProjectile(Snowball.class);
    }  
    public static void LaunchArrow(final LivingEntity sender){
        sender.launchProjectile(Arrow.class);
    }   
    public static void LaunchEgg(final LivingEntity sender){
        sender.launchProjectile(Egg.class);
    }   
    public static void LaunchEnderPearl(final LivingEntity sender){
        sender.launchProjectile(EnderPearl.class);
    }
    public static void LaunchWitherSkull(final LivingEntity sender){
        sender.launchProjectile(WitherSkull.class);
    }
}