package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.utils.Box2DBuild;
import com.mygdx.game.CCDLine;
import com.mygdx.game.GameMap;

public abstract class Entity {
	
	/**
	 * Responsible for the position of an entity.
	 * pos.X is believed to be the far left of the entity.
	 * pos.Y is believed to be the bottom of the entity.
	 */
	protected Vector2 pos;
	protected EntityType type;
	
	/**
	 * This is a CCDLine, or Constant Collision Detection Line.
	 * This line is used for determining if a player hits an object,
	 * even when it's moving so fast that it "Teleports" through/around it 
	 */
	protected CCDLine moveline;
	
	/**
	 * The real, true determinant of an entity's velocity.
	 * Change this value to anything != 0 to make it move.
	 * No further methods or hoops to jump through
	 * can be replaced by other variables in a subclass by
	 * overriding the getYvel() method [NEEDS VERIFICATION] 
	 */
	protected float velocityY = 0;
	
	/**
	 * The real, true determinant of an entity's velocity.
	 * Change this value to anything != 0 to make it move.
	 * No further methods or hoops to jump through.
	 * can be replaced by other variables in a subclass by
	 * overriding the getXvel() method [NEEDS VERIFICATION] 
	 */
	protected float velocityX = 0;
	
	/**
	 * The map the object is located in
	 */
	protected GameMap map;
	protected final int LAYER = 1;
	protected boolean grounded = false;
	
	public void create (EntitySnapshot snapshot, EntityType type, GameMap map) {
		this.pos = new Vector2(snapshot.getX(), snapshot.getY());
		this.type = type;
		this.map = map;
	}
	
	public void update (float deltaTime, float gravity) {
		//Was at one point in the (now removed) MoveX() method
		float newX = (float) Math.floor(pos.x + this.velocityX);
		if (!map.RectCollidesWithMap(newX, pos.y, getWidth(), getHeight(), LAYER)) {
			this.pos.x = newX;
	}else {
			for(int i =0; i < this.velocityX; i++) {
				newX = pos.x+i;
			if (!map.RectCollidesWithMap(newX, pos.y, getWidth(), getHeight(), LAYER)) {
				this.pos.x = newX;
				break;
			}
		}
	}
		this.moveline = new CCDLine(this.pos.x, newX, this.pos.y, this.pos.y+this.velocityY);
		//end of moveX()
		
		//This half is responsible for the movement in the Y
		float newY = pos.y;
		this.velocityY += gravity * deltaTime * getWeight();
		newY += this.velocityY * deltaTime;
		
		if (map.RectCollidesWithMap(pos.x, newY, getWidth(), getHeight(), LAYER)) {
			if (velocityY < 0) {
				this.pos.y = (float) Math.floor(pos.y);
				grounded = true;
				this.velocityY = 0;
			}
		} else {
			this.pos.y = newY;
			grounded = false;
		}
	}
	
	public abstract void render (SpriteBatch batch);
	public void render (TextureRegion batch) {
		
	};
	
	/*	protected float moveX (float amount) {
	}//*/
	
	/**
	 * This method true instantly if either moveline (this.moveline or e.moveline) intersects the other. 
	 * Otherwise it checks the bounding boxes of both the entity and the player, compares them
	 * and returns true if they overlap
	 * @param e
	 * @return
	 */
	public boolean touches(Entity e) {
		float ex = e.pos.x; 
		float ey = e.pos.y; 
		float ewidth = e.getWidth();
		float eheight = e.getHeight();
		float tx = this.pos.x;
		float ty = this.pos.y;
		Boolean xtouches= false, ytouches = false;		
		
		if(ex > tx) {
			if (ex+ewidth <= (tx)) { 
				xtouches = true;
			}else xtouches = false;
		}else if (ex < tx) {
			if (ex  >= (tx) +this.getWidth()){ 
				xtouches = true;
			}else xtouches = false;
		}else xtouches = true;//if ex==tx
		if(ey > ty) {
			if (ey+eheight <= (ty)) {
				ytouches = true;
			}
			else ytouches = false;
		}else if (ey < ty) {
			if (ey >= (ty) +this.getHeight()){
				ytouches = true;
			}
			else ytouches = false;
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
	public float getYvel() {
		return this.velocityY;
	}
	public void setYvel(float f) {
		this.velocityY = f;
	}
	
	public float getXvel() {
		return this.velocityX;
	}
	public void setXvel(float f) {
		this.velocityX = f;
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
	
}