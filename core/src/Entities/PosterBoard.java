package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameMap;

public class PosterBoard extends Entity{
	private final Texture IMAGE = new Texture("badlogic.jpg");
	private final int x = 250, y = 320;
	private Player p;
	
	public PosterBoard(GameMap world) {
		EntitySnapshot snapshot = new EntitySnapshot("posterboard", (float)x, (float)y);
		p = world.getPlayer();
		super.create(snapshot, EntityType.POSTERBOARD, world);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		batch.draw(IMAGE, pos.x, pos.y, getWidth(), getHeight());
	}
	
	public void update(float deltaTime, float gravity) {
		if(p.touches(this) && Gdx.input.isKeyJustPressed(Keys.X)) {
			//Bring up a menu...
		}
		//ensure the board can't be moved by any means
		if(this.getX() != x) {
			this.setX(x);
			this.velocityX = 0;
		}
		if(this.getY() != y) {
			this.setY(y);
			this.velocityY = 0;
		}
		//Reminder: Don't use super.update() unless you want gravity
	}

}
