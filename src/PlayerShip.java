
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The ship that the user will control in the game. Starts centered at the bottom of the game room.
 * Displayed as a space ship.
 * @author sakhavan
 */
public class PlayerShip extends Ship {
    public static final String IMG_FILE = "player_ship.png";
	public static int width = 72;
	public static int height = 72;
	public static final int INIT_POS_X = 390 - (width / 2);
	public static final int INIT_POS_Y = 590;
	public static final int INIT_VX = 0;
	public static final int INIT_VY = 0;
	public static int lives = 3;
	
	public static BufferedImage img;

	// constructor
	public PlayerShip(int roomWidth, int roomHeight) {
		super(INIT_POS_X, INIT_POS_Y, INIT_VX, INIT_VY, width, height, roomWidth, roomHeight);
		
		try {
			if (img == null) {
				img = ImageIO.read(new File(IMG_FILE));
			}
		} catch (IOException e) {
			System.out.println("PlayerShip Internal Error: " + e.getMessage());
		}
	}
	
	// gets the current lives of the player
	public int getLives() {
		return lives;
	}
	
	// sets the current lives of the player
	public void setLives(int new_lives) {
		lives = new_lives;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
	}

	@Override
	public void attack() {
		// can only shoot one bullet at a time
		if (!this.bulletExists()) {
			this.setBullet(new Bullet(this.getPx() + (this.getWidth() / 2), this.getPy(), 1,
									  this.getRoomWidth(), this.getRoomHeight(), Color.WHITE));
		}
	}
}
