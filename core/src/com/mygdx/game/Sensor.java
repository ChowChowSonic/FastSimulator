package com.mygdx.game;

import Entities.Entity;

public class Sensor {
	Entity root;
	GameMap world;

	public Sensor(Entity e, GameMap m) {
		this.root = e;
		world = m;
	}

	public void updateangle(){
		float x = root.getXvel(), y = root.getY()+1, xvel = root.getXvel();
		int layer = root.getLayer();
		float dx = root.getWidth();
		TileType tile = world.getTileTypeByLocation(layer, x+dx, y);

		root.setAngle(50);
	}//ends method 

}
