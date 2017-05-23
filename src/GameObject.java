
import java.awt.Graphics;

/**
 * An object in the game.
 * 
 * Game Objects exist in the game room. They have a position, velocity, size, and bounds. Their
 * velocity controls how they move; their position should always be within their bounds.
 * @author sakhavan
 */
public abstract class GameObject {
	/*
	 * Current position of the enemy (in terms of graphics coordinates). Coordinates are given by
	 * upper left-hand corner of enemy. This position should always be within the bounds.
	 */
	private int px;
	private int py;
	
	// velocity (number of pixels to move every time move() is called)
	private int vx;
	private int vy;
	
	// size of object (in pixels)
	private int width;
	private int height;
	
	/*
	 * Upper bounds of area in which object can be positioned. Maximum permissible (x, y) position
	 * for the upper left-hand corner of the object.
	 */
	private int maxX;
	private int maxY;
	
	// Constructor
	public GameObject(int px, int py, int vx, int vy, int width, int height, int roomWidth,
						 int roomHeight) {
		this.px = px;
		this.py = py;
		this.vx = vx;
		this.vy = vy;
		this.width = width;
		this.height = height;
		
		// account for object width and height when calculating bounds
		this.maxX = roomWidth - width;
		this.maxY = roomHeight - height;
	}

    /*** GETTERS **********************************************************************************/
    public int getPx() {
        return this.px;
    }

    public int getPy() {
        return this.py;
    }
    
    public int getVx() {
        return this.vx;
    }
    
    public int getVy() {
        return this.vy;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getRoomWidth() {
    	return (this.maxX + this.width);
    }
    
    public int getRoomHeight() {
    	return (this.maxY + this.height);
    }
    
    /*** SETTERS **********************************************************************************/
    public void setPx(int px) {
        this.px = px;
        //clip();
    }

    public void setPy(int py) {
        this.py = py;
        //clip();
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }
    
    /*** UPDATES AND OTHER METHODS ****************************************************************/

    /**
     * Prevents the object from going outside of the bounds of the area designated for the object.
     * (i.e. Object cannot go outside of the active area the user defines for it).
     */ 
    private void clip() {
        this.px = Math.min(Math.max(this.px, 0), this.maxX);
        this.py = Math.min(Math.max(this.py, 0), this.maxY);
    }

    /**
     * Moves the object by its velocity.  Ensures that the object does not go outside its bounds by
     * clipping.
     */
    public void move() {
        this.px += this.vx;
        this.py += this.vy;

        clip();
    }
    
    /**
     * Reverses the object's x velocity, and shifts it down to the next line
     */
    public void shiftDown() {
    	// shift object down to next line
    	this.py += this.getHeight();
    	// invert vx
    	this.vx = -this.vx;
    }
    
    public boolean willHitSideWall() {
    	int nextXLeft = this.px + this.vx;
    	int nextXRight = this.px + this.vx + this.getWidth();
    	if (nextXLeft < 0 || nextXRight > this.getRoomWidth()) { return true; }
    	return false;
    }

    /**
     * Determine whether this game object is currently intersecting another object.
     * 
     * Intersection is determined by comparing bounding boxes. If the bounding boxes overlap, then
     * an intersection is considered to occur.
     * 
     * @param that The other object
     * @return Whether this object intersects the other object.
     */
    public boolean intersects(GameObject that) {
    	// a null object cannot be intersecting another object
    	if (that == null) { return false; }
    	
        return (this.px + this.width >= that.px
            && this.py + this.height >= that.py
            && that.px + that.width >= this.px 
            && that.py + that.height >= this.py);
    }
    
    public abstract void draw(Graphics g);
}
