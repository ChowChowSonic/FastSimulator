package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.CCDLine;
import com.mygdx.game.GameMap;

public class Player extends Entity {

	Texture image;
	SpriteBatch spriteBatch;
	private static final int FRAME_COLS = 1, FRAME_ROWS = 1;
	
	private float stateTime=0;
	private float yvelocity = this.velocityY;
	/**
	 * The horizontal speed cap for the player: They
	 * can not move faster than this value in the X directon
	 */
	private static final int SPEEDCAP = 20;
	public static final int JUMP_VEL = 5;

	public Player() {}

	public Player(EntitySnapshot snapshot, GameMap world) {
		super.create(snapshot, EntityType.PLAYER, world);
		image = new Texture("SUPER SPONGEBOB.png");
		this.moveline = new CCDLine(this.getX(), this.getX()+this.velocityX, this.getY(), this.getY()+this.velocityY);
	}

	public void render(SpriteBatch batch) {
		if(this.velocityX >= 0){
			batch.draw(image, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		}else batch.draw(image, this.getX()+this.getWidth(), this.getY(), -this.getWidth(), this.getHeight());;
	}

	public void update(float deltatime, float gravity) {
		if (Gdx.input.isKeyPressed(Keys.SPACE) && grounded)
			this.velocityY += JUMP_VEL * getWeight();
		else if (Gdx.input.isKeyPressed(Keys.SPACE) && !grounded && this.velocityY > 0)
			this.velocityY += JUMP_VEL * getWeight() * deltatime;

		float screenpixel = 3*Gdx.graphics.getPpcX()/Gdx.graphics.getWidth();
		if (Gdx.input.isKeyPressed(Keys.D)) {
			if((int)this.velocityX < SPEEDCAP) {
				this.velocityX+= screenpixel;
				if(this.velocityX < 0) {
					this.velocityX++;
				}
			}else if(!(this.velocityX <= SPEEDCAP)){
				this.velocityX = SPEEDCAP;
			}
		}else if (Gdx.input.isKeyPressed(Keys.A)) {
			if((int)this.velocityX > -SPEEDCAP) {
				this.velocityX-=screenpixel;
				if(this.velocityX > 0) {
					this.velocityX--;
				}
			}else if(!(this.velocityX >= -SPEEDCAP)){
				this.velocityX = -SPEEDCAP-0.01f;
			}
		}else {
			if(this.velocityX > 1) {
				this.velocityX-=0.5;
			}else if(this.velocityX < -1) {
				this.velocityX+=0.5;
			}else this.velocityX=0;
		}
		//System.out.println(this.velocityX);
		super.update(deltatime, gravity);//Apply gravity
	}//ends update

	@Override
	public float getYvel() {
		return this.yvelocity;
	}
}//ends class
