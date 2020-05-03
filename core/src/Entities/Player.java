package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.CCDLine;
import com.mygdx.game.GameMap;
import com.mygdx.game.Sensor;

public class Player extends Entity {

	Texture image;
	SpriteBatch spriteBatch;
	Sensor anglesensor;
	private static final int FRAME_COLS = 1, FRAME_ROWS = 1;

	protected boolean wasjusthit = false;
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
		anglesensor = new Sensor(this, world);
	}

	public void render(SpriteBatch batch) {
		//image = //insert animator that changes the sprite here
		Sprite render = new Sprite(image);
		render.rotate(this.angle);//In degrees
		render.setOrigin(0, 0);//This is relative to whatever you put in setBounds()
		render.setBounds(pos.x,pos.y,this.getWidth(), this.getHeight());//This is relative to the screen
		render.draw(batch);//Holy Fuckles it's Knuckles; the Sprite class is overly complicated
	}

	public void update(float deltatime, float gravity) {
		anglesensor.update();

		if (Gdx.input.isKeyPressed(Keys.SPACE) && grounded) {
			this.velocityY += 1.5* JUMP_VEL * getWeight();
		}
		float screenpixel = 2*Gdx.graphics.getPpcX()/Gdx.graphics.getWidth();
		if(grounded || this.velocityX == 0) wasjusthit=false;
		if(!wasjusthit)
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
					if(this.grounded) {
						this.velocityX-=0.25;
					}else this.velocityX-=screenpixel;
				}else if(this.velocityX < -1) {
					if(this.grounded) {
						this.velocityX+=0.25;
					}else this.velocityX+=screenpixel;
				}else if(this.grounded)this.velocityX=0;
			}
		//System.out.println(this.velocityX);
		super.update(deltatime, gravity);//Apply gravity
	}//ends update

	@Override
	public float getYvel() {
		return this.yvelocity;
	}
}//ends class
