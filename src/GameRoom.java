/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * GameRoom
 * 
 * This class holds the primary game logic for how different objects interact with one another.
 * Contains a timer that interacts with the different methods and repaints the GUI on every tick().
 */
@SuppressWarnings("serial")
public class GameRoom extends JPanel {
	// background image
	Image background = Toolkit.getDefaultToolkit().createImage("background.png");
	
    // the state of the game logic
    private PlayerShip playerShip; // the Player's ship
    private Ship[][] enemyArray = new Ship[5][11];
    private List<SpecialAlien> specialEnemies = new ArrayList<SpecialAlien>();
    
    private SpecialAlien special_alien_0;
    private SpecialAlien special_alien_1;
    private SpecialAlien special_alien_2;
    
    private int beginningSize;
    private int enemySize;

    public boolean playing = false; // whether the game is running 
    
    // Score and Lives counter
    public static int totalScore = 0;
    private int runningScore = 0;
    public static int livesRemaining = 3;
    private int currLives = 3;
    private int currAnimationSpeed = 0;
    
    private JLabel score;
    private JLabel lives;
    private Game game;
    
    // Game constants
    public static final int ROOM_WIDTH = 780;
    public static final int ROOM_HEIGHT = 670;
    public static int player_vx = 10;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;
    public static int counter = 0;

    public GameRoom(Game currGame, JLabel score, JLabel lives) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
                counter++;
            }
        });
        // starts the timer
        timer.start();

        // Enable keyboard focus on the room area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the player to move as long as an arrow key is pressed, by
        // changing the playerShip's velocity accordingly (the tick method below actually moves
        // the ship)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    playerShip.setVx(-player_vx);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    playerShip.setVx(player_vx);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                	playerShip.attack();
                }
            }

            public void keyReleased(KeyEvent e) {
                playerShip.setVx(0);
            }
        });
        this.score = score;
        this.lives = lives;
        this.game = currGame;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
    	score.setText("Score: " + runningScore);
    	lives.setText("Lives: " + currLives);
    	counter = 0;
    	Ship.animationSpeed = 20;
    	
        playerShip = new PlayerShip(ROOM_WIDTH, ROOM_HEIGHT);
        livesRemaining = 3;
        totalScore = 0;
        
    	// create Alien1s
    	for (int i = 0; i < 2; i++) {
    		for (int j = 0; j < 11; j++) {
    			enemyArray[i][j] = new Alien1(108 + 54 * j, 237 - 37 * i, ROOM_WIDTH, ROOM_HEIGHT);
    		}
    	}
    	// create Alien2s
    	for (int i = 0; i < 2; i++) {
    		for (int j = 0; j < 11; j++) {
    			enemyArray[i+2][j] = new Alien2(108 + 54 * j, 163 - 37 * i, ROOM_WIDTH, ROOM_HEIGHT);
    		}
    	}
    	// create Alien3s
    	for (int j = 0; j < 11; j++) {
    		enemyArray[4][j] = new Alien3(108 + 54 * j, 89, ROOM_WIDTH, ROOM_HEIGHT);
    	}
    	
    	// create special aliens
    	specialEnemies.clear();
    	special_alien_0 = new SpecialAlien(-100, 50, ROOM_WIDTH, ROOM_HEIGHT);
    	special_alien_1 = new SpecialAlien(-100, 50, ROOM_WIDTH, ROOM_HEIGHT);
    	special_alien_2 = new SpecialAlien(-100, 50, ROOM_WIDTH, ROOM_HEIGHT);
    	specialEnemies.add(special_alien_0);
    	specialEnemies.add(special_alien_1);
    	specialEnemies.add(special_alien_2);
        
        // set beginning size to number of enemies
        beginningSize = 55;
        enemySize = beginningSize;

        playing = true;

        // make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    void tick() {
        if (playing) {
            // advance the ship in its current direction.
            playerShip.move();
            // if a bullet exists, move it
            if (playerShip.bulletExists()) {
            	playerShip.getBullet().move();
            	// if player bullet goes off screen, reset it
            	if (playerShip.getBullet().getPy() <= 0) {
            		playerShip.setBullet(null);
            	}
            }
            
        	for (int i = 0; i < enemyArray.length; i++) {
        		for (int j = 0; j < enemyArray[0].length; j++) {
        			// don't check current element if it doesn't exist
        			if (enemyArray[i][j] == null) { continue; }
        			Ship currEnemy = enemyArray[i][j];
        			// if bullet hits an enemy, kill it by removing it from enemies list
        			if (currEnemy.intersects(playerShip.getBullet())) {
	        			// increment total score accordingly
	        			totalScore += currEnemy.getDestroyScore();
	        			score.setText("Score: " + totalScore);
	        			enemyArray[i][j] = null;
	        			enemySize--;
	        			playerShip.setBullet(null);
        			}
        		
	        		// if enemy bullet exists, move it
	        		if (currEnemy.bulletExists()) {
	        			currEnemy.getBullet().move();
	        			// if enemy bullet goes off screen, reset it
	        			if ((currEnemy.getBullet().getPy() + currEnemy.getBullet().getHeight()) >=
	        				ROOM_HEIGHT) {
	        				currEnemy.setBullet(null);
	        			}
	        			// if enemy bullet intersects player, remove one life
	        			if (playerShip.intersects(currEnemy.getBullet())) {
	        				livesRemaining--;
	        				lives.setText("Lives: " + livesRemaining);
	        				currEnemy.setBullet(null);
	        			}
	        		}
        		
	        		// if enemy has hit the bottom barrier, lose a life
	        		if (currEnemy.getPy() >= 553) {
	        			livesRemaining--;
	        			enemyArray[i][j] = null;
	        			enemySize--;
	        			lives.setText("Lives: " + livesRemaining);
	        		}
        		}
        	}
        	
        	// speed up when there are less enemies
        	if (enemySize < (beginningSize / 2)) {
        		beginningSize = enemySize;
        		Ship.animationSpeed = Math.max(Ship.animationSpeed / 2, 1);
        	}
        	
            
            // only update the alien positions every animationSpeed ticks
            if (counter % Ship.animationSpeed == 0) {
            	// we iterate through enemies twice such that they will all be shifted down if
            	// necessary BEFORE moving to keep them in line
	            for (int i = 0; i < enemyArray.length; i++) {
	            	for (int j = 0; j < enemyArray[0].length; j++) {
	            		// don't check current element if it doesn't exist
	            		if (enemyArray[i][j] == null) { continue; }
		            	Ship currEnemy = enemyArray[i][j];
		            	if (currEnemy.willHitSideWall()) {
		                    for (int k = 0; k < enemyArray.length; k++) {
		                    	for (int l = 0; l < enemyArray[0].length; l++) {
		                    		// only shift ship if it isn't null
		                    		if (enemyArray[k][l] != null) {
		                    			// only shift down enemies that aren't charging
		                    			if (enemyArray[k][l].getVy() == 0) {
		                    				enemyArray[k][l].shiftDown();
		                    			}
		                    		}
		                    	}
		                    }
		                }
		            	// each enemy only has a 2% chance of attacking each time it moves
		            	if (Math.random() < 0.02) {
		            		currEnemy.attack();
		            	}
	            	}
	            }
	            // move enemies
	            for (int i = 0; i < enemyArray.length; i++) {
	            	for (int j = 0; j < enemyArray[0].length; j++) {
	            		if (enemyArray[i][j] == null) { continue; }
	            		Ship currEnemy = enemyArray[i][j];
	            		currEnemy.move();
	            	}
	            }
	            
	            // randomly send out special enemy
	            if (Math.random() < 0.05 && specialEnemies.size() > 0) {
	            	SpecialAlien currEnemy = specialEnemies.get(specialEnemies.size() - 1);
	            	// only change Vx if it isn't already moving
	            	if (currEnemy.getVx() == 0) {
	            		currEnemy.setVx(10);
	            	}
	            }
            }
            
            // remove special enemies if they are out of bounds; otherwise, move them
            for (int i = 0; i < specialEnemies.size(); i++) {
            	SpecialAlien currEnemy = specialEnemies.get(i);
            	if (currEnemy.getPx() > ROOM_WIDTH) {
            		specialEnemies.remove(i);
            	}
            	currEnemy.move();
    			// if bullet hits a special enemy, kill it by removing it from list
    			if (currEnemy.intersects(playerShip.getBullet())) {
        			// increment total score accordingly
        			totalScore += currEnemy.getDestroyScore();
        			score.setText("Score: " + totalScore);
        			specialEnemies.remove(i);
        			playerShip.setBullet(null);
    			}
            }
            
            // check if player is out of lives, and stop play if so
            if (livesRemaining <= 0) {
            	playing = false;
            	game.switchToGameOverScreen();
            }
            
            // check if player has destroyed all enemies, and reset board with updated score/speed
            if (enemySize == 0) {
            	runningScore = totalScore;
            	currAnimationSpeed++;
            	currLives = livesRemaining;
            	reset();
            	Ship.animationSpeed = Ship.animationSpeed / currAnimationSpeed;
            	totalScore = runningScore;
            	livesRemaining = currLives;
            }
            
            // update the display
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw background
        g.drawImage(background, 0, 0, ROOM_WIDTH, ROOM_HEIGHT, null);
        // draw playerShip and bullet (if it exists)
        playerShip.draw(g);
        if (playerShip.bulletExists()) {
        	playerShip.getBullet().draw(g);
        }
        // draw enemies and bullets (if they exist)
        for (int i = 0; i < enemyArray.length; i++) {
        	for (int j = 0; j < enemyArray[0].length; j++) {
        		if (enemyArray[i][j] == null) { continue; }
	        	Ship currEnemy = enemyArray[i][j];
	        	currEnemy.draw(g);
	        	if (currEnemy.bulletExists()) {
	        		currEnemy.getBullet().draw(g);
	        	}
        	}
        }
        // draw special enemies
        for (int i = 0; i < specialEnemies.size(); i++) {
        	specialEnemies.get(i).draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(ROOM_WIDTH, ROOM_HEIGHT);
    }
}