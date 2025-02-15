
import java.awt.*;

import javax.swing.*;



public class JetGameMain {
	
	
	 public static void main(String[] args) {
		 
		 SwingUtilities.invokeLater(() ->{
	    	
		 	GamePanel gameFrame = new GamePanel();
		 	gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 	gameFrame.setTitle("Jet Fighter");
		 	gameFrame.setMinimumSize(new Dimension(600, 500));
		 	gameFrame.setLocationRelativeTo(null); 		// Center the frame
		 	gameFrame.setResizable(false);
		 	gameFrame.setVisible(true); 
		 });
	    	
	    }
}

