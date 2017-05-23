
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The first type of alien in the game.
 * @author sakhavan
 */
public class Alien1 extends Ship {
    public static final String IMG_FILE_1 = "alien1_1.png";
    public static final String IMG_FILE_2 = "alien1_2.png";
	public static int width = 44;
	public static int height = 32;
	public static final int INIT_VX = 10;
	public static final int INIT_VY = 0;
	
	public static BufferedImage img1;
	public static BufferedImage img2;

	// constructor
	public Alien1(int px, int py, int roomWidth, int roomHeight) {
		super(px, py, INIT_VX, INIT_VY, width, height, roomWidth, roomHeight);
		
		try {
			if (img1 == null) {
				img1 = ImageIO.read(new File(IMG_FILE_1));
			}
			if (img2 == null) {
				img2 = ImageIO.read(new File(IMG_FILE_2));
			}
		} catch (IOException e) {
			System.out.println("Alien1 Internal Error: " + e.getMessage());
		}
		
		// set the score to give when destroyed
		this.setDestroyScore(10);
	}

	@Override
	public void draw(Graphics g) {
		if (GameRoom.counter % (2 * Ship.animationSpeed) < (Ship.animationSpeed)) {
			g.drawImage(img1, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
		}
		else {
			g.drawImage(img2, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
		}
	}

	@Override
	// Alien1 has a simple attack--it only shoots directly downward (like the player)
	public void attack() {
		// can only shoot one bullet at a time
		if (!this.bulletExists()) {
			this.setBullet(new Bullet(this.getPx() + (this.getWidth() / 2),
									  this.getPy() + this.getHeight(), -1, this.getRoomWidth(),
									  this.getRoomHeight(), Color.RED));
		}
	}
}