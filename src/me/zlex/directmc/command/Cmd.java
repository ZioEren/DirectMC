package me.zlex.directmc.command;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.command.CommandExecutor;
import me.zlex.directmc.main.DirectMC;
import me.zlex.directmc.utils.ChatFormat;
public abstract class Cmd implements CommandExecutor{
	private String name;
	private Map<String, String> strings;
	private Map<String, Integer> integers;
	private Map<String, Double> doubles;
	private Map<String, Boolean> booleans;
	public Cmd(String n){
		this.name = n.toLowerCase();
		this.strings = new HashMap<String, String>();
		this.integers = new HashMap<String, Integer>();
		this.doubles = new HashMap<String, Double>();
		this.booleans = new HashMap<String, Boolean>();
	}
	public HashMap<String, String> getStrings(){
		return (HashMap<String, String>) strings;
	}
	public void clearStrings(){
		strings.clear();
		strings = null;
	}
	public void addString(String title, String attribute){
		strings.put(title, attribute);
	}
	public HashMap<String, Integer> getIntegers(){
		return (HashMap<String, Integer>) integers;
	}
	public void clearIntegers(){
		integers.clear();
		integers = null;
	}
	public void addInteger(String title, int attribute){
		integers.put(title, attribute);
	}
	public HashMap<String, Double> getDoubles(){
		return (HashMap<String, Double>) doubles;
	}
	public void clearDoubles(){
		doubles.clear();
		doubles = null;
	}
	public void addDouble(String title, double attribute){
		doubles.put(title, attribute);
	}
	public HashMap<String, Boolean> getBooleans(){
		return (HashMap<String, Boolean>) booleans;
	}
	public void clearBooleans(){
		booleans.clear();
		booleans = null;
	}
	public void addBoolean(String title, boolean attribute){
		booleans.put(title, attribute);
	}
	public void setName(String n){
		this.name = n.toLowerCase();
	}
	public String getName(){
		return this.name.toLowerCase();
	}
	public String getString(String s){
		return ChatFormat.get(DirectMC.getPlugin().getConfig().getConfigurationSection("commands").getConfigurationSection(getName()).getString(s));
	}
	public int getInteger(String s){
		return DirectMC.getPlugin().getConfig().getConfigurationSection("commands").getConfigurationSection(getName()).getInt(s);
	}
	public boolean getBoolean(String s){
		return DirectMC.getPlugin().getConfig().getConfigurationSection("commands").getConfigurationSection(getName()).getBoolean(s);
	}
	public double getDouble(String s){
		return DirectMC.getPlugin().getConfig().getConfigurationSection("commands").getConfigurationSection(getName()).getDouble(s);
	}
}