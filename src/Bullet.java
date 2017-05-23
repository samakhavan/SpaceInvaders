import java.awt.Color;
import java.awt.Graphics;

/**
 * The standard bullet that the user and enemies shoot.
 * @author sakhavan
 *
 */

public class Bullet extends GameObject {
	public static final int INIT_VX = 0;
	public static int INIT_VY = -15;
	public static final int WIDTH = 5;
	public static final int HEIGHT = 20;
	
	private Color color;
	
	// constructor
	public Bullet(int shotX, int shotY, int direction, int roomWidth, int roomHeight, Color color) {
		super(shotX, shotY, INIT_VX, direction * INIT_VY, WIDTH, HEIGHT, roomWidth, roomHeight);
		
		this.color = color;
	}
	
    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }
	
}
