package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.utils.Box2DBuild;
import com.mygdx.game.CCDLine;
import com.mygdx.game.GameMap;
import com.mygdx.game.TileType;

public abstract class Entity {

	/**
	 * Responsible for the position of an entity.
	 * pos.X is believed to be the far left of the entity.
	 * pos.Y is believed to be the bottom of the entity.
	 */
	protected Vector2 pos;

	/**
	 * This is a CCDLine, or Constant Collision Detection Line.
	 * This line is used for determining if a player hits an object,
	 * even when it's moving so fast that it "Teleports" through/around it 
	 */
	protected CCDLine moveline;
	protected EntityType type;

	/**
	 * The map the object is located in
	 */
	protected GameMap map;

	/**
	 * The "Ground Velocity" of the entity. It's functionally identical to velocityX,
	 * but it follows sin() and cos() rules. Useful if you have to apply an angle to velocities as well.
	 */
	protected float velocityG = 0;
	protected float velocityY = 0;
	
	/**
	 * This is the standard X velocity. Use this if you dont care for sin and cos functions
	 */
	protected float velocityX = 0;
	protected final int LAYER = 1;
	protected int angle = 0;
	protected boolean grounded = false;

	public void create (EntitySnapshot snapshot, EntityType type, GameMap map) {
		this.pos = new Vector2(snapshot.getX(), snapshot.getY());
		this.type = type;
		this.map = map;
	}

	public void update (float deltaTime, float gravity) {

		//Drived from the (now removed) MoveX() method
		float newX = (float) Math.floor(pos.x + this.velocityX);
		if (!map.RectCollidesWithMap(newX, pos.y, getWidth(), getHeight(), LAYER)) {
			this.pos.x = newX;
		}else {
			//Substepping loop to prevent collisions with air
			for(int i =(int) this.velocityX; i > 0; i--) {
				newX = pos.x+i;
				if(!map.RectCollidesWithMap(newX, pos.y, getWidth(), getHeight(), LAYER)) {
					pos.x = newX;
					break;
				}
			}
			
			//Loop to make the player step over pixels instead of ramming into them and stopping
			boolean hitawall = true;
			int climbingheight = (int) (this.getHeight()+velocityG*Math.sin(angle)*MathUtils.degreesToRadians);
			for(int i = 0; i < climbingheight;i++) {
				if(velocityX < 0) {
					if(!map.RectCollidesWithMap(newX-1, pos.y+i, getWidth(), getHeight(), LAYER)) {
						pos.y += i;//move the player on top of the block
						if(Math.abs(velocityG) > 2) velocityG+=0.25;//Slow the player down to simulate gravity resisting them
						else velocityG = 0;//stop the player if they slow down too much
						hitawall = false;//Tell the game not to stop the player
						break;
					}
				}else if(velocityX > 0){
					//comments are the same thing in the opposite direction
					if(!map.RectCollidesWithMap(newX+1, pos.y+i, getWidth(), getHeight(), LAYER)) {
						pos.y += i;
						pos.x++;
						if(Math.abs(velocityG) > 1) velocityG-=0.25;
						else velocityG = 0;
						hitawall = false;
						break;
					}
				}
			}
			if(hitawall) {
				this.velocityG=0;
			}
		}
		//end of moveX()

		//This half is responsible for the movement in the Y
			this.velocityY += (gravity * deltaTime * this.getWeight());
		int newY = (int) (pos.y+(this.velocityY * deltaTime));
		if (map.RectCollidesWithMap(pos.x, newY, getWidth(), getHeight(), LAYER)) {
			if (velocityY < 0) {
				this.velocityY = 0;
				this.pos.y = (float) Math.floor(pos.y);
				grounded = true;
			}else if(velocityY > 0){
				this.velocityY = gravity;
			}
		} else {
			this.pos.y = newY;
			grounded = false;
		}
		this.moveline = new CCDLine(this.pos.x, newX, this.pos.y, this.pos.y+this.velocityY);

	}

	public abstract void render (SpriteBatch batch);
	public void render (TextureRegion batch) {

	};

	/*	protected float moveX (float amount) {
	}//*/

	/**
	 * This method true if either moveline (this.moveline or e.moveline) intersects the other. 
	 * Otherwise it checks the bounding boxes of both the entity and the player, compares them
	 * and returns true if they overlap
	 * @param e
	 * @return
	 */
	public boolean touches(Entity e) {
		float x2 = e.pos.x; 
		float y2 = e.pos.y; 
		float ewidth = e.getWidth();
		float eheight = e.getHeight();
		float x1 = this.pos.x;
		float y1 = this.pos.y;
		Boolean xtouches= false, ytouches = false;		

		if(x1 > x2) {
			if (x2+ewidth >= (x1)) { 
				xtouches = true;
			}else xtouches = false;
		}else if (x2 > x1) {
			if (x1+this.getWidth() >= x2){ 
				xtouches = true;
			}else xtouches = false;
		}else xtouches = true;//if ex==tx

		if(y1 > y2) {
			if (y2+eheight >= (y1)) { 
				ytouches = true;
			}else ytouches = false;
		}else if (y2 > y1) {
			if (y1+this.getHeight() >= y2){ 
				ytouches = true;
			}else ytouches = false;
		}else ytouches = true;//if ey==ty
		if(this.moveline!=null && this.moveline.isonline(e)) return true;
		else if(e.moveline!=null && e.moveline.isonline(this)) return true;
		return (xtouches && ytouches);
	}

	public EntitySnapshot getSaveSnapshot () {
		return new EntitySnapshot(type.getId(), pos.x, pos.y);
	}

	public Vector2 getPos() {
		return pos;
	}

	//Get/set the position
	public float getX () {
		return pos.x;
	}
	public void setX (int i) {
		this.pos.x = i;
	}

	public float getY () {
		return pos.y;
	}
	public void setY (int i) {
		this.pos.y = i;
	}

	//Get the velocity
	public float getYvel() {
		return this.velocityY;
	}
	public float getXvel() {
		return this.velocityX;
	}
	public float getGvel() {
		return this.velocityG;
	}

	public void setGvel(float gvel) {
		this.velocityG = gvel;
	}

	public EntityType getType() {
		return type;
	}

	public boolean isGrounded() {
		return grounded;
	}

	public int getWidth() {
		return type.getWidth();
	}

	public int getHeight() {
		return type.getHeight();
	}

	public float getWeight() {
		return type.getWeight();
	}
	public int getAngle() {
		return this.angle;
	}
	public void setAngle(int angle) {
		this.angle= angle;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Entity) {
			Entity e = (Entity) o;
			if(e.getType().equals(this.getType())) {
				if(e.getX() == this.getX() && e.getY() == this.getY()) {
					if(e.getYvel() == this.getYvel() && e.getXvel() == this.getXvel())
						return true;
				}
			}
		}
		return false;
	}

	public int getLayer() {
		// TODO Auto-generated method stub
		return this.LAYER;
	}

}