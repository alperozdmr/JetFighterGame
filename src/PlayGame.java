
import java.awt.Color;
import java.awt.Font;
//import java.awt.Color;
//import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
//import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;











public class PlayGame extends JFrame implements Runnable, KeyListener {
	
	
	private Random randomNumber;
	
							
	
	private Background background1, background2, background3;	// Background
	public ArrayList<Bullet> BulletList;						// Bullets
	public ArrayList<Bullet> EnemyBulletList;					// Enemy Bullets
	public ArrayList<EnemyPlane> enemyList;						// Enemies
	public ArrayList<EnemyPlane> enemyBoss;						// Enemy boss
	public Font fontUI;		
	public GamePanel gp;
	private static Plane player;	
	
	private BufferedImage  planeImage, bg1, bg2, bg3;//
	private BufferedImage bulletImage,enemyImage,BossImage,enemyBullettImage;
	private Graphics g;		
	private int WIDTH, HEIGHT,setEnemy;	
	private Player user;
	
	
	int MoveDirection;
	boolean isSpacePressed;
	boolean isBossAlive = true;
	static int numberOfEnemy =0;
	
	
	private static final long serialVersionUID = 1L;

	public PlayGame(int setEnemy,int w, int h, Player p, GamePanel gamePanel) {
		this.setEnemy=setEnemy;
		WIDTH = w;				// Width
		HEIGHT = h;				// Height
		user = p;				// Player Profile
    	setLayout(null);		// Set Layout
    	addKeyListener(this);	// KeyListener
    	fontUI = new Font("Helvetica", Font.ROMAN_BASELINE, 18);

    	BulletList = new ArrayList<Bullet>();
    	EnemyBulletList = new ArrayList<Bullet>();
    	enemyList = new ArrayList<EnemyPlane>();
    	enemyBoss = new ArrayList<EnemyPlane>();
    	randomNumber = new Random();
    	this.gp = gamePanel;
    	objects();
	}
    	
	
	@Override
	public void run() {
		while(player.alive && isBossAlive){
			update();
			repaint();

			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				
			}
			
		}
		
		DataM dM = new DataM();
		dM.updateScore(user.getName(), player.getScore()+"");
		//JOptionPane.showConfirmDialog(null,"Your score is: " + player.getScore(), "GAME OVER", JOptionPane.CLOSED_OPTION);
    	//this.setVisible(false);
		///  update scor
		
			
	}
	
	public void objects() {
		Background();
		Planes();
		Bullets();
		
	}
	
	public void Background() {
		String bg1Path = "./ımage/bg1.png"; 
		String bg2Path = "./ımage/bg2.png"; 
		String bg3Path = "./ımage/bg3.png"; 
        try {
			bg1 = ImageIO.read(new File(bg1Path)); 
			bg2 = ImageIO.read(new File(bg2Path)); 
			bg3 = ImageIO.read(new File(bg3Path)); 
		}
        catch (IOException e) {
			e.printStackTrace();
		}
        
    	background1 = new Background(0, 0, WIDTH, HEIGHT, bg1);
    	background2 = new Background(0, background1.getHeight(), WIDTH, HEIGHT, bg2);
    	background3 = new Background(0, -background1.getHeight(), WIDTH, HEIGHT, bg3);
    	
    }
	public void Planes() {
		
		String planeImagePath = "./ımage/plane.png";
        try {
			planeImage = ImageIO.read(new File(planeImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
        int planeWidth = (WIDTH*17)/100; // Plane image width will be %20 of the screen width
        int planeHeight = (planeImage.getHeight() * planeWidth) / planeImage.getWidth(); // Set plane image height as ration of width
    	player = new Plane(WIDTH/2 - planeWidth/2, HEIGHT - planeHeight - HEIGHT/50, planeWidth, planeHeight, planeImage); // Create player object
    	player.setScreenWidth(WIDTH); 
		
	}
	public void Bullets() {
		String bulletImagePath = "./ımage/mybullet.png";
        try {
			bulletImage = ImageIO.read(new File(bulletImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
        String enemyBulletImagePath = "./ımage/enemybullet.png";
        try {
			enemyBullettImage = ImageIO.read(new File(enemyBulletImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
		checkCollisions();
		for(int ed=0; ed<enemyList.size(); ed++){
			if(enemyList.get(ed).alive==false){
				enemyList.remove(ed);
				player.increaseScore(20);
				
			}
		}
		for(int ed=0; ed<enemyBoss.size(); ed++){
			if(enemyBoss.get(ed).alive==false){
				enemyBoss.remove(ed);
				player.increaseScore(100);
				isBossAlive = false;
				
			}
		}
		///////////////////////////////// DYNAMİC BACKGROUND
		if(background1.getY()>=HEIGHT){
			background1.setY(background2.getY()-background1.getHeight());
		}
		background1.setY(background1.getY() + background1.speed);
		
		if(background2.getY()>=HEIGHT){
			background2.setY(background3.getY()-background2.getHeight());
		}
		background2.setY(background2.getY() + background2.speed);
		
		if(background3.getY()>=HEIGHT){
			background3.setY(background1.getY()-background3.getHeight());
		}
		background3.setY(background3.getY() + background3.speed);
		
		/////////////////////////////////   MOVE PLAYER AND BULLETS
		if(MoveDirection == 1){
			player.moveLeft();
		}
		if(MoveDirection == -1){
			player.moveRight();
		}
		if(isSpacePressed){
			if(player.rocketNumber > 0) {
				shootBullet(player.getX(), player.getY()); // Create Rocket
			}
		}
		
		 Iterator<Bullet> iterator = BulletList.iterator();
	        while (iterator.hasNext()) {
	            Bullet bullet = iterator.next();
	            bullet.move();
	            // Remove bullets that go off the top of the panel
	            if (bullet.getY()< 0) {
	                iterator.remove();
	            }
	        }
	    //////////////////////////////////////    ENEMY HANDLİNG
	        
	        for(int me=0; me<enemyList.size(); me++){
				int moveState = randomNumber.nextInt(100);
				if(enemyList.get(me).moveCount==0){
					if(moveState<50){
						enemyList.get(me).setMoveState(1);
						enemyList.get(me).setMoveCount(randomNumber.nextInt(100));
					}
					else{
						enemyList.get(me).setMoveState(0);
						enemyList.get(me).setMoveCount(randomNumber.nextInt(100));
					}
				}
				else{
					enemyList.get(me).moveLR();				
				}
			}
	        
			Iterator<EnemyPlane> Eiterator =  enemyList.iterator();
			while(Eiterator.hasNext()){
				// Move enemy to down
				EnemyPlane enemy = Eiterator.next();
				enemy.moveDown();
				if(enemy.getY()>HEIGHT){ 	// If out of the screen
					Eiterator.remove();		// Remove enemy
					
				}
			}

			for(int EBULL=0; EBULL<enemyList.size(); EBULL++){
				if(enemyList.get(EBULL).getAmmo()>0){
					if(randomNumber.nextInt(200)<25){
						enemyList.get(EBULL).decreaseAmmo();
						enemyShoot(enemyList.get(EBULL).getX()+enemyList.get(EBULL).getWidth()/2, enemyList.get(EBULL).getY()+ enemyList.get(EBULL).getHeight());
					}
				}
			}
			// Move Enemy Rockets
			Iterator<Bullet> Ebullİter = EnemyBulletList.iterator();
			while(Ebullİter.hasNext()) {
				Bullet eBull = Ebullİter.next();
				eBull.move();
				if(eBull.getY()>HEIGHT) {
					Ebullİter.remove();
				}
			}
	     
			 if(numberOfEnemy<setEnemy && randomNumber.nextInt(200)== 50 ){
				
				createEnemy();
				numberOfEnemy++;
			 }
			 ////////// Enemy boss
			 if(numberOfEnemy == setEnemy && enemyList.size()==0 ) {
				 createBoss();
				 numberOfEnemy++;
			 }

			 Iterator<EnemyPlane> Biterator =  enemyBoss.iterator();
				while(Biterator.hasNext()){
					// Move enemy to down
					EnemyPlane enemyB = Biterator.next();
					enemyB.moveDown();
					if(enemyB.getY()>HEIGHT/7){ 	
						enemyB.setY(HEIGHT/7);
						
					}
				} 
				for(int eRoc=0; eRoc<enemyBoss.size(); eRoc++){
					if(enemyBoss.get(eRoc).getAmmo()>0){
						if(randomNumber.nextInt(150)<25){
							enemyBoss.get(eRoc).decreaseAmmo();
							enemyBossShoot(enemyBoss.get(eRoc).getX()+enemyBoss.get(eRoc).getWidth()/2, enemyBoss.get(eRoc).getY()+ enemyBoss.get(eRoc).getHeight());
						}
					}
				}
			///////////////////////
			 
	}
	private void checkCollisions() {
		
		
		///////////////// Check Enemy Collisions
		Iterator<EnemyPlane> enemyCounter =  enemyList.iterator();
		while(enemyCounter.hasNext()) {
			EnemyPlane enemyList = enemyCounter.next();
			Rectangle Enemyplane = new Rectangle(enemyList.getX(),enemyList.getY(), enemyList.getWidth(), enemyList.getHeight());
			if (Enemyplane.intersects(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
				player.decreaseHealth(30);
				///// decrease health
				enemyCounter.remove();
			}
		}
		////////////////////// checks enemy and bullets collisions
		Iterator<EnemyPlane> en = enemyList.iterator();
		while(en.hasNext()) {
			EnemyPlane enemyList = en.next();
			Rectangle Enemyplane = new Rectangle(enemyList.getX(),enemyList.getY(), enemyList.getWidth(), enemyList.getHeight());
			Iterator<Bullet> bullet = BulletList.iterator();
			while(bullet.hasNext()) {
				Bullet rc = bullet.next();
				if(Enemyplane.intersects(rc.getX(),rc.getY(),rc.getWidth(),rc.getHeight())){
					enemyList.decreaseHealth(rc.getDamage());
					bullet.remove();
				}
			}
		}
		Iterator<EnemyPlane> boss = enemyBoss.iterator();
		while(boss.hasNext()) {
			EnemyPlane enemyList = boss.next();
			Rectangle Enemyplane = new Rectangle(enemyList.getX(),enemyList.getY(), enemyList.getWidth(), enemyList.getHeight());
			Iterator<Bullet> bullet = BulletList.iterator();
			while(bullet.hasNext()) {
				Bullet rc = bullet.next();
				if(Enemyplane.intersects(rc.getX(),rc.getY(),rc.getWidth(),rc.getHeight())){
					enemyList.decreaseHealth(rc.getDamage());
					bullet.remove();
				}
			}
		}
		/////////////////////////// check enemy bullet and player collision
		Iterator<Bullet> eBullet = EnemyBulletList.iterator();
		while(eBullet.hasNext()) {
			Bullet ebull= eBullet.next();
			Rectangle plane = new Rectangle(player.getX(),player.getY(),player.getWidth(),player.getHeight());
			if(plane.intersects(ebull.getX(), ebull.getY(), ebull.getWidth(), ebull.getHeight())) {
				player.decreaseHealth(ebull.getDamage());
				eBullet.remove();
			}
		}
		
		
	}
	public Image drawScene(){
		BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	    g = bufferedImage.createGraphics();
	    

	    // DYNAMİC BACKGROUNDS
	    g.drawImage(background1.getSourceImage(), background1.getX(), background1.getY(), background1.getWidth(), background1.getHeight(), this);
	    g.drawImage(background2.getSourceImage(), background2.getX(), background2.getY(), background2.getWidth(), background2.getHeight(), this);
	    g.drawImage(background3.getSourceImage(), background3.getX(), background3.getY(), background3.getWidth(), background3.getHeight(), this);
	    
	    // PLAYER PLANE 
	    g.drawImage(player.getSourceImage(), player.getX(), player.getY(), player.getWidth(), player.getHeight(), this);
	    // PLAYER BULLET
	    for(int i=0; i<BulletList.size(); i++){
	    	g.drawImage(BulletList.get(i).getSourceImage(), BulletList.get(i).getX(), BulletList.get(i).getY(), BulletList.get(i).getWidth(), BulletList.get(i).getHeight(), this);
	    }
	    // ENEMY PLANE 
	    for(int en=0; en<enemyList.size(); en++){
	    	g.drawImage(enemyList.get(en).getSourceImage(), enemyList.get(en).getX(), enemyList.get(en).getY(), enemyList.get(en).getWidth(), enemyList.get(en).getHeight(), this);
	    	
	    }
	    // ENEMY BOSS
	    for(int en=0; en<enemyBoss.size(); en++){
	    	g.drawImage(enemyBoss.get(en).getSourceImage(), enemyBoss.get(en).getX(), enemyBoss.get(en).getY(), enemyBoss.get(en).getWidth(), enemyBoss.get(en).getHeight(), this);
	    }
	    // ENEMY BULLEET
	    for(int i=0; i<EnemyBulletList.size(); i++){
	    	g.drawImage(EnemyBulletList.get(i).getSourceImage(), EnemyBulletList.get(i).getX(), EnemyBulletList.get(i).getY(), EnemyBulletList.get(i).getWidth(), EnemyBulletList.get(i).getHeight(), this);
	    }
	    
	    
	    g.setFont(fontUI);
	    g.drawString("Score:" + player.getScore() + " | " + "Rocket: " + player.getRocketNumber(), WIDTH/50, HEIGHT/10);// Score
	    g.drawString("Health "+player.getHealth(), 15, 100);// Score
		g.setColor(Color.RED);
	    g.fillRect(15,105, WIDTH/5*player.getHealth()/100, HEIGHT/80);
	    
	    if (!player.alive) {
        	g.clearRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString("GAME OVER", getWidth() / 2 - 100, getHeight() / 4);
            g.drawString("YOU LOSE", getWidth() / 2 - 85, getHeight() / 3);
            g.drawString("SCOR IS  \n"+player.getScore(), getWidth() / 2 - 100, getHeight() / 2-60);
         }
	    if(!isBossAlive) {
	    	g.clearRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString("GAME OVER", getWidth() / 2 - 100, getHeight() / 4);
            g.drawString("YOU WIN", getWidth() / 2 - 85, getHeight() / 3);
            g.drawString("SCOR IS  \n"+player.getScore(), getWidth() / 2 - 100, getHeight() / 2-60);
        }

	    return bufferedImage;
	}
	
	////// PAİNT ///////
	@Override
	public void paint(Graphics g){
        g.drawImage(drawScene(), 0, 0, this); 
	}
	
	/// SHOOT BULLET ///
	public void shootBullet(int x, int y){
		int bulletWidth = player.getWidth()/5;
        int bulletHeight = bulletImage.getHeight() * bulletWidth / bulletImage.getWidth(); 
		if(player.getRocketNumber()>0) {
			player.decreaseRocket(); 
			BulletList.add(new Bullet(x + player.getWidth()/3, y + player.getHeight()/10, HEIGHT, 30, 0, bulletWidth,bulletHeight, bulletImage));
			
		}
		
	}
	///////////////////////// ENEMY AND BOSS SHOOT///////////////
	public void enemyShoot(int x, int y){
		int rocketWidth = player.getWidth()/5;
        int rocketHeight = enemyBullettImage.getHeight() * rocketWidth / enemyBullettImage.getWidth();
        // 1 is rocket damage
        EnemyBulletList.add(new Bullet(x, y-5, HEIGHT, 1, 1, rocketWidth, rocketHeight, enemyBullettImage));
	}
	///////////////////////////////
	public void enemyBossShoot(int x ,int y) {
		
		int rocketWidth = player.getWidth()/5;
        int rocketHeight = enemyBullettImage.getHeight() * rocketWidth / enemyBullettImage.getWidth();
        
        int rand = randomNumber.nextInt(200);
        if(rand<50) {
        	//////// LEFT WİNG
	        EnemyBulletList.add(new Bullet(x-150,y,HEIGHT,5,1,rocketWidth,rocketHeight,enemyBullettImage));
	        EnemyBulletList.add(new Bullet(x-100,y,HEIGHT,5,1,rocketWidth,rocketHeight,enemyBullettImage));
	        //////// RİGHT WİNG
	        EnemyBulletList.add(new Bullet(x+100,y,HEIGHT,5,1,rocketWidth,rocketHeight,enemyBullettImage));
	        EnemyBulletList.add(new Bullet(x+150,y,HEIGHT,5,1,rocketWidth,rocketHeight,enemyBullettImage));
        } 
        else if(50 <rand && rand < 100) {
        	//////// LEFT WİNG
   	        EnemyBulletList.add(new Bullet(x-120,y,HEIGHT,5,1,rocketWidth,rocketHeight,enemyBullettImage));
   	        EnemyBulletList.add(new Bullet(x-70,y,HEIGHT,5,1,rocketWidth,rocketHeight,enemyBullettImage));
   	        //////// RİGHT WİNG
   	        EnemyBulletList.add(new Bullet(x+70,y,HEIGHT,5,1,rocketWidth,rocketHeight,enemyBullettImage));
   	        EnemyBulletList.add(new Bullet(x+120,y,HEIGHT,5,1,rocketWidth,rocketHeight,enemyBullettImage));
        }
        else {
        	
        }
        
	}
	////////////////////////////////////////////
	////////// create enemy
	public void createEnemy(){

		String planeImagePath = "./ımage/enemyplane2.png" ;
				
        try {
			enemyImage = ImageIO.read(new File(planeImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		int enemyWidth = (WIDTH*15)/100;
        int enemyHeight = enemyImage.getHeight() * enemyWidth / enemyImage.getWidth();
        EnemyPlane enemyObject;
		if(enemyList.size()==0){
			enemyObject = new EnemyPlane(randomNumber.nextInt(WIDTH-enemyWidth), -enemyHeight, enemyWidth, enemyHeight, enemyImage, WIDTH,180,50);
			
		}	
		else{
			if(enemyList.get(enemyList.size()-1).getY()>0){
				enemyObject = new EnemyPlane(randomNumber.nextInt(WIDTH-enemyWidth), -enemyHeight, enemyWidth, enemyHeight, enemyImage, WIDTH,180,50);				
			}
			else{
				enemyObject = new EnemyPlane(randomNumber.nextInt(WIDTH-enemyWidth), enemyList.get(enemyList.size()-1).getY()-randomNumber.nextInt(enemyHeight)-enemyHeight*3/2, enemyWidth, enemyHeight, enemyImage, WIDTH,180,50);	
			}
		}
		enemyList.add(enemyObject);
	}
	///////// create boss
	public void createBoss() {
		String Bosspath= "./ımage/enemyboss.png";
		try {
			BossImage = ImageIO.read(new File(Bosspath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int bossWidth = (WIDTH*70)/100;
        int bossHeight = BossImage.getHeight() * bossWidth / BossImage.getWidth();
        EnemyPlane enemyObject;
		if(enemyBoss.size()==0){
			enemyObject = new EnemyPlane(80, -bossHeight, bossWidth, bossHeight, BossImage, WIDTH,800,200);
			enemyBoss.add(enemyObject);
		}	
	}
	
	
	
	
	
	///////////////////
	//// KEY EVENT ////
	///////////////////
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			MoveDirection = 1;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			MoveDirection = -1;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			isSpacePressed = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			MoveDirection = 2;
		}		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			MoveDirection = 2;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			isSpacePressed = false;
		}
	}

	
@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
