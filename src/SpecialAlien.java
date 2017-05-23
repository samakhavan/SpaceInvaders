
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The fourth type of alien in the game.
 * @author sakhavan
 */
public class SpecialAlien extends Ship {
    public static final String IMG_FILE_1 = "alien_saucer.png";
	public static int width = 66;
	public static int height = 28;
	public static final int INIT_VX = 0;
	public static final int INIT_VY = 0;
	
	public static BufferedImage img1;

	// constructor
	public SpecialAlien(int px, int py, int roomWidth, int roomHeight) {
		super(px, py, INIT_VX, INIT_VY, width, height, roomWidth, roomHeight);
		
		try {
			if (img1 == null) {
				img1 = ImageIO.read(new File(IMG_FILE_1));
			}
		} catch (IOException e) {
			System.out.println("Alien3 Internal Error: " + e.getMessage());
		}
		
		// set the score to give when destroyed
		this.setDestroyScore(150);
	}
	
	@Override
    public void move() {
        this.setPx(this.getPx() + this.getVx());
        this.setPy(this.getPy() + this.getVy());
    }

	@Override
	public void draw(Graphics g) {
		g.drawImage(img1, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
	}

	@Override
	// SpecialAlien doesn't attack, it just gives bonus points!
	public void attack() { return; }
}