package me.zlex.directmc.managers.economy;
import org.bukkit.entity.Player;
import me.zlex.directmc.database.Database;
import me.zlex.directmc.main.DirectMC;
public class EcoManager{
	public EcoManager(){
		for (Database database: DirectMC.getDatabaseManager().getDatabases()){
			if (database.getName().equalsIgnoreCase("economy")){
				try{
					database.reload();
				}catch(Exception e){
					database.setData("");
					database.save();
					database.reload();
				}
				break;
			}
		}
	}
	public static Database getDatabase(){
		for (Database database: DirectMC.getDatabaseManager().getDatabases()){
			if (database.getName().equalsIgnoreCase("economy")){
				return database;
			}
		}
		return null;
	}
	public static void reload(){
		for (Database database: DirectMC.getDatabaseManager().getDatabases()){
			if (database.getName().equalsIgnoreCase("economy")){
				database.reload();
				break;
			}
		}
	}
	public static void save(){
		for (Database database: DirectMC.getDatabaseManager().getDatabases()){
			if (database.getName().equalsIgnoreCase("economy")){
				database.save();
				break;
			}
		}
	}
	public static boolean isInEconomy(Player p){
		for (Database database: DirectMC.getDatabaseManager().getDatabases()){
			if (database.getName().equalsIgnoreCase("economy")){
				if (database.getData() == "" || database.getData() == null)
					return false;
				if (p == null)
					return false;
				for (String s: database.getLines()){
					if (!(s == "") && !(s == null)){
						if (s.toLowerCase().startsWith(p.getName().toLowerCase() + " ")){
							return true;
						}
					}
				}
				break;
			}
		}
		return false;
	}
	public static String getVaultSymbol(){
		return DirectMC.getPlugin().getConfig().getConfigurationSection("economy").getString("vault-symbol");
	}
	public static boolean isEnabled(){
		return DirectMC.getPlugin().getConfig().getConfigurationSection("economy").getBoolean("enabled");
	}
	public static double getMinMoney(){
		return DirectMC.getPlugin().getConfig().getConfigurationSection("economy").getDouble("min-money");
	}
	public static double getMaxMoney(){
		return DirectMC.getPlugin().getConfig().getConfigurationSection("economy").getDouble("max-money");
	}
	public static double getStartMoney(){
		return DirectMC.getPlugin().getConfig().getConfigurationSection("economy").getDouble("start-money");
	}
	public static double getMoney(Player p){
		for (Database database: DirectMC.getDatabaseManager().getDatabases()){
			if (database.getName().equalsIgnoreCase("economy")){
				database.reload();
				try{
					for (String s: database.getLines()){
						if (!(s == "") && !(s == null)){
							if (s.toLowerCase().startsWith(p.getName().toLowerCase() + " ")){
								String[] lol = s.split("\\s+");
								Double money = Double.parseDouble(lol[1]);
								return money;
							}
						}
					}
				}catch(Exception e){
					return getStartMoney();
				}
				break;
			}
		}
		return getStartMoney();
	}
	public static void startEconomy(Player p, double money){
		for (Database database: DirectMC.getDatabaseManager().getDatabases()){
			if (database.getName().equalsIgnoreCase("economy")){
				String asd = p.getName().toLowerCase() + " " + String.valueOf(money);
				if (database.getData() == ""){
					database.setData(asd);
				}else{
					database.setData(database.getData() + "\n" + asd);
				}
				database.save();
				database.reload();
				break;
			}
		}
	}
	public static void addMoney(Player p, double money){
		for (Database database: DirectMC.getDatabaseManager().getDatabases()){
			if (database.getName().equalsIgnoreCase("economy")){
				String asd = "";
				for (String s: database.getLines()){
					if (s.startsWith(p.getName().toLowerCase() + " ")){
						asd = s;
						break;
					}
				}
				String[] lol = asd.split("\\s+");
				double newMoney;
				double theMoney = Double.parseDouble(lol[1]);
				newMoney = theMoney + money;
				String finished = lol[0] + " " + String.valueOf(newMoney);
				database.setData(database.getData().replace(asd, finished));
				database.save();
				database.reload();
				break;
			}
		}
	}
	public static void takeMoney(Player p, double money){
		for (Database database: DirectMC.getDatabaseManager().getDatabases()){
			if (database.getName().equalsIgnoreCase("economy")){
				String asd = "";
				for (String s: database.getLines()){
					if (s.startsWith(p.getName().toLowerCase() + " ")){
						asd = s;
						break;
					}
				}
				String[] lol = asd.split("\\s+");
				double newMoney;
				double theMoney = Double.parseDouble(lol[1]);
				newMoney = theMoney - money;
				String finished = lol[0] + " " + String.valueOf(newMoney);
				database.setData(database.getData().replace(asd, finished));
				database.save();
				database.reload();
				break;
			}
		}
	}
	public static void fixMoney(Player p, double minMoney, double maxMoney){
		for (Database database: DirectMC.getDatabaseManager().getDatabases()){
			if (database.getName().equalsIgnoreCase("economy")){
				String asd = "";
				for (String s: database.getLines()){
					if (s.startsWith(p.getName().toLowerCase() + " ")){
						asd = s;
						break;
					}
				}
				String[] lol = asd.split("\\s+");
				double theMoney = Double.parseDouble(lol[1]);
				if (theMoney < minMoney){
					theMoney = minMoney;
				}
				if (theMoney > maxMoney){
					theMoney = maxMoney;
				}
				String finished = lol[0] + " " + String.valueOf(theMoney);
				database.setData(database.getData().replace(asd, finished));
				database.save();
				database.reload();
				break;
			}
		}
	}
	
	public static void setMoney(Player p, double money){
		for (Database database: DirectMC.getDatabaseManager().getDatabases()){
			if (database.getName().equalsIgnoreCase("economy")){
				String asd = "";
				for (String s: database.getLines()){
					if (s.startsWith(p.getName().toLowerCase() + " ")){
						asd = s;
						break;
					}
				}
				String[] lol = asd.split("\\s+");
				String finished = lol[0] + " " + String.valueOf(money);
				database.setData(database.getData().replace(asd, finished));
				database.save();
				database.reload();
				break;
			}
		}
	}
	public static boolean hasSufficientMoney(Player p, double money){
		if (getMoney(p) >= money)
			return true;
		return false;
	}
}