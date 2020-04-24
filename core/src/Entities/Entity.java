package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	 * pos.X is believed to be the far right of the entity. [NEEDS VERIFICATION]
	 */
	protected Vector2 pos;
	protected EntityType type;
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
	protected GameMap map;
	protected boolean grounded = false;
	
	public void create (EntitySnapshot snapshot, EntityType type, GameMap map) {
		this.pos = new Vector2(snapshot.getX(), snapshot.getY());
		this.type = type;
		this.map = map;
	}
	
	public void update (float deltaTime, float gravity) {
		float newX = (float) Math.floor(pos.x + this.velocityX);
		if (!map.RectCollidesWithMap(newX, pos.y, getWidth(), getHeight())) {
			this.moveline = new CCDLine(this.pos.x, newX, this.pos.y, this.pos.y+this.velocityY);
			this.pos.x = newX;
	}
		else {
			this.velocityX = 0;
		}
		float newY = pos.y;
		
		this.velocityY += gravity * deltaTime * getWeight();
		newY += this.velocityY * deltaTime;
		
		if (map.RectCollidesWithMap(pos.x, newY, getWidth(), getHeight())) {
			if (velocityY < 0) {
				this.pos.y = (float) Math.floor(pos.y);
				grounded = true;
			}
			this.velocityY = 0;
		} else {
			this.pos.y = newY;
			grounded = false;
		}
	}
	
	public abstract void render (SpriteBatch batch);
	
	/*	protected float moveX (float amount) {
	}//*/
	
	/**
	 * This method makes me sad
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
		int i = 0;//so I can remove the loops with minimal effort
		
	//	for(int i = 0; i<= this.getXvel(); i++) {
		if(ex > tx) {
			if (ex+ewidth <= (tx+i) -this.getWidth()) { 
				xtouches = true;
			//	break;
			}else xtouches = false;
		}else if (ex < tx) {
			if (ex-ewidth >= (tx+i) +this.getWidth()){ 
				xtouches = true;
			//	break;
			}else xtouches = false;
		}else xtouches = true;
	//}
		//for(int i = 0; i<= this.getYvel(); i++) {
		if(ey > ty) {
			if (ey+eheight <= (ty+i) -this.getHeight()) {
				ytouches = true;
			//	break;
			}
			else ytouches = false;
		}else if (ey < ty) {
			if (ey-eheight >= (ty+i) +this.getHeight()){
				ytouches = true;
			//	break;
			}
			else ytouches = false;
		}else ytouches = true;
		//}
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
	
}