
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The third type of alien in the game.
 * @author sakhavan
 */
public class Alien3 extends Ship {
    public static final String IMG_FILE_1 = "alien3_1.png";
    public static final String IMG_FILE_2 = "alien3_2.png";
	public static int width = 44;
	public static int height = 32;
	public static final int INIT_VX = 10;
	public static final int INIT_VY = 0;
	
	public static BufferedImage img1;
	public static BufferedImage img2;

	// constructor
	public Alien3(int px, int py, int roomWidth, int roomHeight) {
		super(px, py, INIT_VX, INIT_VY, width, height, roomWidth, roomHeight);
		
		try {
			if (img1 == null) {
				img1 = ImageIO.read(new File(IMG_FILE_1));
			}
			if (img2 == null) {
				img2 = ImageIO.read(new File(IMG_FILE_2));
			}
		} catch (IOException e) {
			System.out.println("Alien3 Internal Error: " + e.getMessage());
		}
		
		// set the score to give when destroyed
		this.setDestroyScore(40);
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
	// Alien3 has a semi-random attack--it will shoot its bullet left or right randomly
	public void attack() {
		// can only shoot one bullet at a time
		if (!this.bulletExists()) {
			this.setBullet(new Bullet(this.getPx() + (this.getWidth() / 2),
									  this.getPy() + this.getHeight(), -1, this.getRoomWidth(),
									  this.getRoomHeight(), Color.RED));
			// make bullet move left or right randomly
			if (Math.random() < 0.5) {
				this.getBullet().setVx(-5);
			}
			else {
				this.getBullet().setVx(5);
			}
		}
	}
}