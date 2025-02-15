

public class Player {

	private String Username;	// User Name
	private String pass;	// User Password
	private int score;		// User Score

	
	
	public Player(String Username, String pass,int score){
		super();
		this.Username = Username;
		this.pass = pass;
		this.score = score;
	}



	public String getName(){
		return Username;
	}
	public void setName(String name){
		this.Username = name;
	}
	public String getPass(){
		return pass;
	}
	public void setPass(String pass){
		this.pass = pass;
	}
	public int getScore(){
		return score;
	}
	public void setScore(int score){
		this.score = score;
	}
	@Override
	public String toString() {
		return "Player ['name=" + Username + ", pass=" + pass + ", score=" + score + "]";
	}

}
