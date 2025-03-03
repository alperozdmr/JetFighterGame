

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class GamePanel extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L; 
	private JMenuBar menuBar; 
	private static JLabel gameHeader,Image; 
	private static PlayGame PG;
	private static JButton register,login,help;
	private static JPasswordField passwordField,passwordFieldL;
	private static JTextField nameField,nameFieldL;
	private static JLabel password,name,message,nameL,passwordL;
	private static JPanel registerPanel , loginPanel ,scorPanel;
	private JTextArea Scorarea;
	private static DataM data;
	private static JFrame frame,frameL,frameS;
	static ImageIcon image;
	private static Player currentPlayer;
	public static final int WIDTH = 500;	// Game Screen Width
	public static final int HEIGHT = 700;
	public static Thread gameThread;
	static int setEnemy;
	
	
	public GamePanel() {
		
		
		
		 Font headerFont = new Font("SansSerif", Font.BOLD, 20);	
		 gameHeader = new JLabel("JET FIGHTER");		  	 			// Name label
		 gameHeader.setFont(headerFont);								// Set Font Type
		 gameHeader.setHorizontalAlignment(SwingConstants.CENTER);		// Text Alignment
		 gameHeader.setSize(300,60);				 	 				// Label Location
		 gameHeader.setLocation(150, 30);								// Label position
		 gameHeader.setForeground(Color.BLACK);	 	 					// Label Text Color
		 add(gameHeader);	
		 
		
		 try {
				ImageIcon image = new ImageIcon(getClass().getResource("bgq.jpg"));
				Image = new JLabel(image);
				Image.setBounds(0, 20, 600, 500);
				add(Image);
			}
			catch(Exception e) {
				System.out.println("cannot found");
		}
		 
		 JMenu filemenu = new JMenu ("File");
		 
		 JMenuItem Register = new JMenuItem ("Register");
		 filemenu.add(Register);
		 
		 JMenuItem Login = new JMenuItem ("Login");
		 filemenu.add(Login);
		 
		 JMenuItem PlayGame = new JMenuItem ("Play Game");
		 filemenu.add(PlayGame);
		 
		 JMenuItem scor = new JMenuItem ("Scor Table");
		 filemenu.add(scor);
		 
		 JMenuItem exitItem = new JMenuItem ("Exit");
	     filemenu.add (exitItem);
	     
	     JMenu helpmenu = new JMenu ("Help");
	     
	     JMenuItem about = new JMenuItem ("About");
	     helpmenu.add(about);
	     
	     menuBar = new JMenuBar();
		 menuBar.add(filemenu);
		 menuBar.add(helpmenu);
		 
		 setPreferredSize (new Dimension (1000, 400));
	     setLayout (null);
	     add (menuBar);
	     menuBar.setBounds (0, 0, 635, 25);
	     
	     ////////////////////////
	     //// REGİSTER PANEL ////
	     ////////////////////////
	     Register.addActionListener(new java.awt.event.ActionListener() {
	    	 
				public void actionPerformed(ActionEvent e) {
						
						registerPanel();
					 	
				}
	        });
	     ///////////////////////
	     ///// LOGİN PANEL//////
	     //////////////////////
	     Login.addActionListener(new java.awt.event.ActionListener() {
	        	
	        	public void actionPerformed(java.awt.event.ActionEvent evt) {
	        		
	               loginPanel();
	        		
	            }
	        });
	     
	     ////////////////////////
	     ////// PLAY GAME ///////
	     ////////////////////////
	     PlayGame.addActionListener(new java.awt.event.ActionListener() {
	        	
	        	public void actionPerformed(java.awt.event.ActionEvent evt) {
	        		
	        		loginPanel();	               
	            }
	        });
	     /////////////////////////
	     /////// SCOR ////////////
	     /////////////////////////
	     scor.addActionListener(new java.awt.event.ActionListener() {
	        	
	    	 
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showScor();
	              
	            }
	        });
	     ///////////////////
	     ////// EXIT //////
	     //////////////////
	     exitItem.addActionListener(new java.awt.event.ActionListener() {
	        	
	        	public void actionPerformed(java.awt.event.ActionEvent evt) {
	               System.exit(0);
	            }
	        });
	     
	     ///// ABAOUT //////
	     about.addActionListener(new java.awt.event.ActionListener() {
	        	
	        	public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	
	            	JOptionPane.showMessageDialog(null, "ALPER ÖZDEMİR 20210702058", "About", JOptionPane.INFORMATION_MESSAGE);
	            }
	        });
	    
	    
	}
	

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == register) {
			registerFunc();
		}
		if(e.getSource() == login) {
			login();
		}
	
		if(e.getSource() == help) {
			int result = JOptionPane.showConfirmDialog(null, "You have to register. Do you want create a new user?", "Help", JOptionPane.YES_NO_OPTION);
	    	if(result == 0) {
	    		registerPanel();
	    	}
	    	
		}
	}
	
	///////////////////////////////////////	
	///////// REGİSTER FUNCTİON ///////////
	///////////////////////////////////////
	@SuppressWarnings("deprecation")
	public void registerFunc() {
		String Username = nameField.getText();
		String pass = passwordField.getText();
		data = new DataM();
		
		if(Username!=null && pass!=null){
			if(Username.length()!=0 && pass.length()!=0 || Username.contains(" " ) || pass.contains(" ")){
            	if(data.registerUser(Username, pass)){
            		message.setForeground(Color.green);
            		message.setText("Registered succesfuly");
            	}
            	else{
            		message.setForeground(Color.RED);
            		message.setText("User already exist");
            	}
        	}
			
        }
        else{
        	message.setForeground(Color.RED);
        	message.setText("Error: Fill all fields!");
        }
		
	}

	///////////////////////////////////////	
	/////////    LOGİN FUNCTİON ///////////
	///////////////////////////////////////                          / DAHA BİTMEDİ
	@SuppressWarnings("deprecation")
	public void login() {
		String Username = nameFieldL.getText();
		String pass = passwordFieldL.getText();
		data = new DataM();
		currentPlayer=data.loginUser(Username, pass);
		int userexist =0;
		if(currentPlayer!=null && currentPlayer.getName().equals(Username)) {
			message.setForeground(Color.green);
    		message.setText("Login succesfuly");
    		frameL.setVisible(false);
    		userexist++;
    		
		}
		else {
			message.setForeground(Color.RED);
    		message.setText("User does not exist");
		}
		
		if(userexist !=0) {
			
		   diffuculty();
			
    		PG = new PlayGame(setEnemy,WIDTH, HEIGHT, currentPlayer, this);
    		PG.setVisible(true);
    		PG.setTitle("jet fighter");
    		PG.setLocationRelativeTo(null);
    		PG.setSize(WIDTH, HEIGHT);
    		PG.setResizable(false);
    		PG.setLocationRelativeTo(null);
    		PG.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    		gameThread = new Thread(PG);
    		gameThread.start(); 
    		PG.addWindowListener(new java.awt.event.WindowAdapter() {
    			@Override
    		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
    		        
    				int result = JOptionPane.showConfirmDialog(PG, "Do you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
    		    	if(result == 0) {
    		    		
    		        	PG.dispose();
    		    	}
		
    		    }
    		});
    	}
    }
    		    
	////////////////////////
	//// LOGİN PANEL //////
	///////////////////////
	public void loginPanel() {
		loginPanel = new JPanel();
	 	loginPanel.setLayout(null);
	 	frameL = new JFrame("Login Pane");
	    frameL.getContentPane().add(loginPanel);
	    
	    nameL = new JLabel("Name:");
        nameL.setBounds(30, 5, 100, 40);			
		nameL.setForeground(Color.black);	
		loginPanel.add(nameL);
        
        nameFieldL=new JTextField();									
        nameFieldL.setBounds(100, 15, 100, 20);							
		nameFieldL.setBackground(Color.white);							
		nameFieldL.setForeground(Color.black);							
		loginPanel.add(nameFieldL);									
       
		passwordL = new JLabel("Password:");
		passwordL.setBounds(30, 45, 100, 40);							
		passwordL.setForeground(Color.black);	
		loginPanel.add(passwordL);
	        
		passwordFieldL=new JPasswordField();						
		passwordFieldL.setBounds(100, 55, 100, 20);					
	    passwordFieldL.setBackground(Color.white);						
	    passwordFieldL.setForeground(Color.black);						
	    loginPanel.add(passwordFieldL); 							
	    
	    login = new JButton("Login");
	    login.setBounds(30, 95, 75, 20);
	    login.setBackground(Color.white);						
	    login.setForeground(Color.black);
	    login.addActionListener(new GamePanel());
	    loginPanel.add(login);
	    
	    help = new JButton("Help");
	    help.setBounds(110, 95, 75, 20);
	    help.setBackground(Color.white);						
	    help.setForeground(Color.black);
	    help.addActionListener(new GamePanel());
	    loginPanel.add(help);
        
        message = new JLabel(" ");
        message.setBounds(30, 115, 300, 20);
        loginPanel.add(message);
        
        loginPanel.setBackground(Color.DARK_GRAY);
        frameL.setLocationRelativeTo(null);
	    frameL.setSize(350,200);
	    frameL.setVisible(true);
	}
	///////////////////////////
	///// REGİSTER PANEL //////
	///////////////////////////
	public void registerPanel() {
		registerPanel = new JPanel();
	 	registerPanel.setLayout(null);
	 	frame = new JFrame("Register Pane");
	    frame.getContentPane().add(registerPanel);
	    
	    name = new JLabel("Name:");
        name.setBounds(30, 5, 100, 40);			
		name.setForeground(Color.black);	
		registerPanel.add(name);
        
        nameField=new JTextField();									
        nameField.setBounds(100, 15, 100, 20);							
		nameField.setBackground(Color.white);							
		nameField.setForeground(Color.black);							
		registerPanel.add(nameField);									
       
		password = new JLabel("Password:");
		password.setBounds(30, 45, 100, 40);							
		password.setForeground(Color.black);
		registerPanel.add(password);
	        
		passwordField=new JPasswordField();						
		passwordField.setBounds(100, 55, 100, 20);					
	    passwordField.setBackground(Color.white);						
	    passwordField.setForeground(Color.black);						
	    registerPanel.add(passwordField); 							
	    
	    register = new JButton("Register");
	    register.setBounds(30, 95, 100, 20);
	    register.setBackground(Color.white);						
	    register.setForeground(Color.black);
	    register.addActionListener(new GamePanel());
        registerPanel.add(register);
        
        message = new JLabel(" ");
        message.setBounds(30, 115, 300, 20);
        registerPanel.add(message);
        
        registerPanel.setBackground(Color.DARK_GRAY);
        frame.setLocationRelativeTo(null);
	    frame.setSize(350,200);
	    frame.setVisible(true);
	}
	
	public void showScor() {
		
		scorPanel = new JPanel();
	 	scorPanel.setLayout(null);
		frameS = new JFrame("Scor Pane");
	    frameS.getContentPane().add(scorPanel);
	    
	    data = new DataM();
	    String users = data.getScore();
	    
	    Scorarea = new JTextArea();
	    Font scorefont = new Font("SansSerif", Font.BOLD, 20);
	    Scorarea.setFont(scorefont);
	    Scorarea.setEditable(false);
	    Scorarea.setText(users);
	    Scorarea.setBounds(40, 40, 250, 270);
	    scorPanel.add(Scorarea);
	    
	    scorPanel.setBackground(Color.DARK_GRAY);
	    frameS.setLocationRelativeTo(null);
	    frameS.setSize(350,400);
	    frameS.setVisible(true);
	    
	}
	
	public void diffuculty() {
		
		String difficultyselection = "1.EASY\n" + "2.MEDIUM \n"+"3.HARD\n";
    	String difficulty = JOptionPane.showInputDialog(difficultyselection);
    	int select = Integer.parseInt(difficulty);
    	
    	switch (select) {
    	
    	case 1:
    		setEnemy= 5;
    		break;
    	case 2:
    		setEnemy= 10;
    		break;
    	case 3:
    		setEnemy= 15;
    		break;
    		
    	
    	}
    		
	}

	

	
	 
    
}
