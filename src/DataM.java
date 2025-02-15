

import java.io.*;
import java.util.ArrayList;

public class DataM {
	
	private static FileWriter writer ;
	private static BufferedWriter bufferedWriter;
	private static FileReader reader;
	private static BufferedReader bufferedReader;
	private static String line;
	//static ArrayList<Player> player = new ArrayList<>();
	private static Player player;
	private static final String FILE = "C:\\Users\\Hp\\Desktop\\VS code\\JetFighter\\JetFighterGame\\PlayerScor.csv";
	
	
	public boolean registerUser(String name, String pass){
		
		if(IsUserExist(name)) {
			
			return false;
		}
		else {

	        try {
	            writer = new FileWriter(FILE, true);
	            bufferedWriter = new BufferedWriter(writer);

	            bufferedWriter.write(name + "," + pass +","+"0");
	            bufferedWriter.newLine();

	            bufferedWriter.close();

	        } catch (IOException e) {
	           System.out.println("PlayerScor bulunamadı");
	        }
			
			return true;
		}
		
	}
	
	public boolean IsUserExist(String name){
		
		try {
			reader = new FileReader(FILE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    bufferedReader = new BufferedReader(reader);
	    
		try {
			while((line = bufferedReader.readLine()) != null){
				String[] sArr = line.split(",");
				if(sArr[0].equals(name)){
					return true;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	public Player loginUser(String name , String pass) {
		
		
		try {
			reader = new FileReader(FILE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    bufferedReader = new BufferedReader(reader);
	    
		try {
			while((line = bufferedReader.readLine()) != null){
				String[] sArr = line.split(",");
				if(sArr[0].equals(name) && sArr[1].equals(pass)){
					int scor = Integer.parseInt(sArr[2]);
					player = new Player(name, pass, scor);
					//return true;
					
				}
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return player;
	} 
	
	
	public void updateScore(String name, String score){
		
		try {
			reader = new FileReader(FILE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    bufferedReader = new BufferedReader(reader);
		try {
			String write = "";
			ArrayList<String> playerList = new ArrayList<String>();
			while((line = bufferedReader.readLine()) != null){
				String[] sArr = line.split(",");
				if(sArr[0].equals(name)){
					if(Integer.parseInt(sArr[2])< Integer.parseInt(score)){
						// If current score higher than old score update with new record
						write = sArr[0] + "," + sArr[1] + "," + score;						
					}
					else{
						// Keep old score
						write = sArr[0] + "," + sArr[1] + "," + sArr[2];
					}
				}
				else{
					write = line;
				}
				playerList.add(write);
			}
			bufferedReader.close();
			writer = new FileWriter(FILE);
			bufferedWriter = new BufferedWriter(writer);
			for(int i=0; i<playerList.size(); i++){
				bufferedWriter.write(playerList.get(i) + "\n");
			}			
			bufferedWriter.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	public String getScore() {
       
		try {
			reader = new FileReader(FILE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		 bufferedReader = new BufferedReader(reader);
		 String info =" ";
        try { 
            String line;
           
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 3) {
                    String name = fields[0];
                    String scor = fields[2];
                    info += "Name: " + name + ", Scor: " + scor + "\n" ;
                    
                }
            }
           
            bufferedReader.close();
             
        } catch (IOException e) {
            e.printStackTrace();
        }
		return info;
        
    }
	
	/////// update score ////////


}
