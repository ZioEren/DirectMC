package me.zlex.directmc.database;
import java.util.ArrayList;
public class DatabaseManager{
	private ArrayList<Database> databases;
	public DatabaseManager(){
		databases = new ArrayList<Database>();
	}
	public void addDatabase(Database database){
		databases.add(database);
	}
	public void removeDatabase(Database database){
		databases.remove(database);
	}
	public ArrayList<Database> getDatabases(){
		return databases;
	}
	public Database getDatabaseByName(String name){
		for (Database database: databases){
			if (name.toLowerCase() == database.getName().toLowerCase()){
				return database;
			}
		}
		return null;
	}
}