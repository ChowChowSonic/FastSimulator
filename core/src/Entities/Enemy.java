package Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.CCDLine;
import com.mygdx.game.GameMap;
import com.mygdx.game.TileType;
import Entities.Player;

public class Enemy extends Entity{

	Texture image;
	Player p;
	GameMap world;
	private final int defaultspeed = 100;
	private boolean goingback = false;
	private TileType blocktowatch, cliff, blockade;

	public Enemy() {}
	public Enemy(EntitySnapshot snapshot, GameMap world) {
		super.create(snapshot, EntityType.ENEMY, world);
		this.world = world;
		image = new Texture("badlogic.jpg");
		p = world.getEntitybyType(new Player());
	}

	public void render(SpriteBatch batch){
		batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
		//batch.draw(image, pos.x-TileType.TILE_SIZE, pos.y, getWidth(), getHeight());//Debug stuff
	}

	public void update(float deltatime, float gravity) {
	//	CCDLine line= p.moveline;
		if(this.touches(p)) {
			p.setYvel(10*p.getWeight());
		}
		
		if (goingback == false) {
			cliff = world.getTileTypeByLocation(1, this.pos.x+TileType.TILE_SIZE+5, this.pos.y - TileType.TILE_SIZE);
			blockade = world.getTileTypeByLocation(1, pos.x+TileType.TILE_SIZE+this.getWidth(), pos.y);
			if(blockade != null && blockade.isCollidable()) {
				goingback=!goingback;
				blocktowatch=blockade;
			}else blocktowatch=cliff;
		}
		else if(goingback == true){
			cliff = world.getTileTypeByLocation(1, this.pos.x - TileType.TILE_SIZE, this.pos.y - TileType.TILE_SIZE);
			blockade = world.getTileTypeByLocation(1, this.pos.x - TileType.TILE_SIZE, this.pos.y);
			if(blockade != null && blockade.isCollidable()) {
				blocktowatch=blockade;
				goingback=!goingback;
			}else blocktowatch=cliff;
		}

		if(blocktowatch != null && blocktowatch.isCollidable()) {
			if(goingback == false){
				this.velocityX = defaultspeed*deltatime;
			}
			else {
				this.velocityX = (-defaultspeed*deltatime);
			}
		}else {
			goingback=!goingback;
		}
		super.update(deltatime, gravity);
	}
}
