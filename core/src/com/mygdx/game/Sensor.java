package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;

public class Sensor {
	float x, y;
	int width, height;
	GameMap world;

	public Sensor(float x, float y, int entitywidth, int entityheight, GameMap m) {
		this.x = x;
		this.y = y;
		width = entitywidth;
		height = entityheight;
		world = m;
	}

	public float updateangle(){
		float endpos = x+width;
		double dx= width+1f;
		double dy = 0f;
		//check the tiles beneath the entity's root pos, X, which is the far left side
		for (int i =0; i < width; i++) {
			TileType tile = world.getTileTypeByLocation(1, x, y-i);
			if(tile != null && tile.isCollidable()) {
				dy+= i;
				//System.out.println(i);
				break;
			}
		}
		
		//check tiles beneath the entity's "front" (far right) which is x + width
		for (int i = width; i > -width; i--) {
			TileType tile = world.getTileTypeByLocation(1, x+width+1, y+i);
			if(tile != null && tile.isCollidable()) {
				dy+= i;
				//System.out.println(i);
				break;
			}
		}
		float angle = (float) (Math.atan(((float)dy)/dx)*MathUtils.radiansToDegrees);
		System.out.println(dy +" " + dx);
		
		return angle;
	}//ends method 

}
