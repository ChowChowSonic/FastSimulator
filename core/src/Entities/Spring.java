package Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameMap;

public class Spring extends Entity{

	private GameMap world;
	Texture image;
	Player p;

	public Spring() {}
	public Spring(EntitySnapshot snapshot, GameMap world) {
		super.create(snapshot, EntityType.ENEMY, world);
		this.world = world;
		image = new Texture("badlogic.jpg");
		p = world.getPlayer();
	}
	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		batch.draw(image, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
	}
	
	public void update(float deltaTime,float gravity) {
		if(p.touches(this)|| this.touches(p)){
			p.setYvel(12*p.getWeight());
		}
		super.update(deltaTime, gravity);
	}

}
