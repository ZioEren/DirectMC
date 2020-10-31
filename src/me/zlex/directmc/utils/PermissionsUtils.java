package me.zlex.directmc.utils;
import org.bukkit.entity.Player;
public class PermissionsUtils{
	public static boolean hasPermission(Player p, String permission){
		return p.isOp() || p.hasPermission("*") || p.hasPermission("dmc.*") || p.hasPermission("dmc." + permission) || p.hasPermission("directmc.*") || p.hasPermission("directmc." + permission) || p.hasPermission(permission) || p.hasPermission("essentials." + permission) || p.hasPermission("essentials.*");
	}
}