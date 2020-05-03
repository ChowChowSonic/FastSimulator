package com.mygdx.game;

import Entities.Entity;

public class Sensor {
	Entity root;
	GameMap world;

	public Sensor(Entity e, GameMap m) {
		this.root = e;
		world = m;
	}

	public void update(){
		float x = root.getXvel(), y = root.getY(), xvel = root.getXvel();
		int layer = root.getLayer(), signum = 1;
		TileType tile = null;
		float dx = 0;

		/*//Make sure that we're colliding on the correct side here
				if(xvel > 0) {
					x+= root.getWidth();
				}
				//Horizontal check to see if a collidable tile is approaching
				for(int i = 0; i <= Math.abs(xvel); i++) {
						tile = world.getTileTypeByLocation(layer, x+(i*Math.signum(xvel)), y);
						
					if(tile != null && tile.isCollidable()) {
						dx=i*Math.signum(xvel);
						break;
					}else if(!world.getTileTypeByLocation(layer, x+(i*Math.signum(xvel)), y-1).isCollidable()) {
					//If the game sees a downhill slope
						signum = -1;
					}
				}
				if(dx == 0) {
					dx=0.00001f;
				}
				//Vertical check to see how far up the tiles go, if we find a tile we can hit
				if(tile != null && tile.isCollidable()) {
					for(int i = 0; i < Math.ceil(32); i++) {
						tile = world.getTileTypeByLocation(layer, x, y+(i*signum));
						if(tile != null && !tile.isCollidable()) {
							root.setAngle(root.getAngle()+(int)Math.ceil((180*Math.atan((i*signum)/dx))/Math.PI));
							break;
						}
					}
				}//*/


	}//ends method 

}
