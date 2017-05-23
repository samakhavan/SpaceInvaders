
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The second type of alien in the game.
 * @author sakhavan
 */
public class Alien2 extends Ship {
    public static final String IMG_FILE_1 = "alien2_1.png";
    public static final String IMG_FILE_2 = "alien2_2.png";
	public static int width = 44;
	public static int height = 32;
	public static final int INIT_VX = 10;
	public static final int INIT_VY = 0;
	
	public static BufferedImage img1;
	public static BufferedImage img2;

	// constructor
	public Alien2(int px, int py, int roomWidth, int roomHeight) {
		super(px, py, INIT_VX, INIT_VY, width, height, roomWidth, roomHeight);
		
		try {
			if (img1 == null) {
				img1 = ImageIO.read(new File(IMG_FILE_1));
			}
			if (img2 == null) {
				img2 = ImageIO.read(new File(IMG_FILE_2));
			}
		} catch (IOException e) {
			System.out.println("Alien2 Internal Error: " + e.getMessage());
		}
		
		// set the score to give when destroyed
		this.setDestroyScore(20);
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
	// Alien2 has a kamikaze attack--it will randomly stop moving sideways and charge the player
	public void attack() {
		this.setVx(0);
		this.setVy(20);
	}
}