package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Animator;
import com.mygdx.game.CCDLine;
import com.mygdx.game.GameMap;

public class Player extends Entity {

	Animator render;
	Texture image;
	private static int accel = 0;
	private float yvelocity = this.velocityY;
	private static final int SPEEDCAP = 20;
	public static final int JUMP_VEL = 5;

	public Player() {}

	public Player(EntitySnapshot snapshot, GameMap world) {
		super.create(snapshot, EntityType.PLAYER, world);
		image = new Texture("SUPER SPONGEBOB.png");
		this.moveline = new CCDLine(this.getX(), this.getX()+this.velocityX, this.getY(), this.getY()+this.velocityY);
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
	}

	public void update(float deltatime, float gravity) {
		if (Gdx.input.isKeyPressed(Keys.SPACE) && grounded)
			this.velocityY += JUMP_VEL * getWeight();
		else if (Gdx.input.isKeyPressed(Keys.SPACE) && !grounded && this.velocityY > 0)
			this.velocityY += JUMP_VEL * getWeight() * deltatime;
		if (Gdx.input.isKeyPressed(Keys.D)) {
			if(accel >= SPEEDCAP) {
		accel ++;
		}else if(accel < SPEEDCAP){accel = SPEEDCAP;}
		}else if (Gdx.input.isKeyPressed(Keys.A)) {
			if(accel <= -SPEEDCAP) {
				accel --;
				}else if(accel > -SPEEDCAP){accel = -SPEEDCAP;}
		}else{
			if(this.velocityX>0) this.velocityX-=1;
			else if(this.velocityX<0)this.velocityX+=1;
			accel=0;
		}
		this.velocityX += accel*deltatime;
		/*if (Gdx.input.isKeyPressed(Keys.D)) {
					if((accel < SPEEDCAP)) {
						if(this.velocityX==0) accel+=5;
						this.velocityX = (int) moveX((accel+=2) * deltatime);
					}else this.velocityX = (int) moveX((accel) * deltatime);
		
				}else if (Gdx.input.isKeyPressed(Keys.A)) {
					if((accel > -SPEEDCAP)) {
						if(accel==0) accel-=5;
						this.velocityX = (int) moveX(-(accel+=2) * deltatime);
					}else this.velocityX = (int) moveX(-(accel) * deltatime);
				}else{
					this.velocityX=0;
					accel=(int) this.velocityX;
				}//*/
		System.out.println(this.velocityX);
		super.update(deltatime, gravity);//Apply gravity
	}//ends update

	@Override
	public float getYvel() {
		return this.yvelocity;
	}
}//ends class
