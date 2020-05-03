package com.mygdx.game;

import Entities.Entity;

public class Sensor {
	Entity root;
	GameMap world;

	public Sensor(Entity e, GameMap m) {
		this.root = e;
		world = m;
	}

	public int updateangle(){
		float x = root.getXvel(), y = root.getY()+1, xvel = root.getXvel();
		int layer = root.getLayer();
		float dx = root.getWidth();
		TileType tile = world.getTileTypeByLocation(layer, x-dx, y);

		if(tile !=null && tile.isCollidable())
		return 10;
		else return 50;
	}//ends method 

}
