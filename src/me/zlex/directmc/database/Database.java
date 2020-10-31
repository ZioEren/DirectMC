package me.zlex.directmc.database;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import me.zlex.directmc.main.DirectMC;
public class Database{
	private String name;
	private String data;
	public Database(String n){
		this.name = n;
	}
	public void setName(String n){
		this.name = n;
	}
	public String getName(){
		return this.name;
	}
	public void reload(){
		File file = new File(DirectMC.getPlugin().getDataFolder(), this.getName().toLowerCase() + ".yml");
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		}catch(FileNotFoundException e){}
		try{
		    try{
				String everything = IOUtils.toString(inputStream);
				this.data = everything;
			}catch (IOException e){}
		}finally{
		    try{
				inputStream.close();
			} catch (IOException e){}
		}
	}
	public void setData(String data){
		this.data = data;
	}
	public String getData(){
		return this.data;
	}
	public String[] getLines(){
		return this.data.split("\n");
	}
	public ArrayList<String> getArrayLines(){
		ArrayList<String> arrayLines = new ArrayList<String>();
		String[] lines = getLines();
		for (int i = 0; i < lines.length; i++){
			arrayLines.add(lines[i]);
		}
		return arrayLines;
	}
	public List<String> getListLines(){
		List<String> arrayLines = new ArrayList<String>();
		String[] lines = getLines();
		for (int i = 0; i < lines.length; i++){
			arrayLines.add(lines[i]);
		}
		return arrayLines;
	}
	public String getLine(int i){
		String[] lines = getLines();
		String line = "";
		for (int k = 0; k < lines.length; k++){
			if (k == i){
				line = lines[k];
				break;
			}
		}
		return line;
	}
	public boolean isUsable(){
		return !(this.data == "") && !(this.data == null);
	}
	public void save(){
        BufferedWriter output = null;
        try{
    		File file = new File(DirectMC.getPlugin().getDataFolder(), this.getName().toLowerCase() + ".yml");
            output = new BufferedWriter(new FileWriter(file));
            output.write(getData());
        }catch (IOException e){
            e.printStackTrace();
        }finally{
          if(output != null){
            try{
				output.close();
			}catch (IOException e){
				e.printStackTrace();
			}
          }
        }
	}
}