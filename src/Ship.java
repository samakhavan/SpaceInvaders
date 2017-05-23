
/**
 * A ship in the game.
 * 
 * Ships have a main method of attack.
 * Each ship can only have one bullet on screen at a time (checked by shotBullet boolean).
 * @author sakhavan
 *
 */

public abstract class Ship extends GameObject {
	// every ship has a bullet and a destroy score
	private Bullet bullet = null;
	private int destroyScore = 0;
	public static int animationSpeed = 20;
	
	// Constructor
	public Ship(int px, int py, int vx, int vy, int width, int height, int roomWidth,
				int roomHeight) {
		super(px, py, vx, vy, width, height, roomWidth, roomHeight);
	}
	
	/*** GETTERS *********************************************************************************/
	public Bullet getBullet() {
		return this.bullet;
	}
	
	public int getDestroyScore() {
		return this.destroyScore;
	}
	
	public int getAnimationSpeed() {
		return Ship.animationSpeed;
	}
	
	/*** SETTERS *********************************************************************************/
	public void setBullet(Bullet bullet) {
		this.bullet = bullet;
	}
	
	public void setDestroyScore(int score) {
		this.destroyScore = score;
	}
	
	public void setAnimationSpeed(int newSpeed) {
		Ship.animationSpeed = newSpeed;
	}
	
	/*** OTHER METHODS ***************************************************************************/
	// check if bullet exists
	public boolean bulletExists() {
		if (this.bullet != null) { return true; }
		return false;
	}
	
	public abstract void attack();
}
